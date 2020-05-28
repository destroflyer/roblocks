/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game;

/**
 *
 * @author Carl
 */
public class ProgramExecution{

    public ProgramExecution(){
        
    }

    public ProgramExecution(Program program){
        this.program = program;
    }
    public static float SPEED = 1;
    private boolean isExecuting;
    private Program program;
    private Map map;
    private ProgramStep currentStep;
    private ProgramStepPart[] currentStepParts;
    private int currentStepPartIndex;
    private float currentStepPartDuration;
    
    public void start(Map map){
        this.map = map;
        map.prepareForProgram();
        program.resetExecution();
        executeNextStep();
        isExecuting = (currentStep != null);
    }

    public void stop(){
        map.prepareForProgram();
    }

    public void onNextFrameCalculation(float lastTimePerFrame){
        if(isExecuting){
            currentStepPartDuration += (SPEED * lastTimePerFrame);
            if(currentStepPartDuration > currentStepParts[currentStepPartIndex].getDuration()){
                currentStepPartIndex++;
                currentStepPartDuration = 0;
                if(currentStepPartIndex < currentStepParts.length){
                    executeCurrentStepPart();
                }
                else{
                    executeNextStep();
                }
            }
        }
    }
    
    private void executeNextStep(){
        map.afterProgramStep();
        if(map.isObjectiveAccomplished()){
            onMapObjectiveAccomplished();
        }
        else{
            try{
                do{
                    currentStep = program.getNextStep();
                }while((currentStep == null) && (!program.isFinished()));
            }catch(StackOverflowError ex){
                currentStep = null;
            }
            if(currentStep != null){
                currentStepParts = currentStep.generateParts();
                currentStepPartIndex = 0;
                currentStepPartDuration = 0;
                executeCurrentStepPart();
            }
            else{
                stopExecuting();
            }
        }
    }
    
    protected void onMapObjectiveAccomplished(){
        stopExecuting();
    }
    
    private void stopExecuting(){
        isExecuting = false;
    }
    
    private void executeCurrentStepPart(){
        for(Robot robot : map.getRobots()){
            currentStepParts[currentStepPartIndex].execute(robot);
        }
    }

    public Program getProgram(){
        return program;
    }
}
