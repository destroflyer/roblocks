/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game;

import com.destroflyer.roblocks.game.program_steps.SubprogramStep;

/**
 *
 * @author Carl
 */
public class SimpleProgram extends Program{

    public SimpleProgram(ProgramStep[] steps){
        this.steps = steps;
    }
    private ProgramStep[] steps;
    private int currentStepIndex = -1;
    private ProgramStep currentStep;

    @Override
    public ProgramStep getNextStep(){
        currentStep = getCurrentStep(true);
        return currentStep;
    }

    private ProgramStep getCurrentStep(boolean incrementStepIndex){
        boolean goToNextStep = incrementStepIndex;
        if(isSubprogramStep()){
            boolean isFirstStepOfSubProgram = (!incrementStepIndex);
            SubprogramStep subprogramStep = (SubprogramStep) steps[currentStepIndex];
            SimpleProgram subProgram = subprogramStep.getProgram();
            if(isFirstStepOfSubProgram){
                subProgram.resetExecution();
            }
            ProgramStep nextSubProgramStep;
            do{
                nextSubProgramStep = subProgram.getNextStep();
            }while((nextSubProgramStep == null) && (!subProgram.isFinished()));
            if(nextSubProgramStep != null){
                return nextSubProgramStep;
            }
            else if(isFirstStepOfSubProgram){
                goToNextStep = true;
            }
        }
        if(goToNextStep){
            currentStepIndex++;
            if(isFinished()){
                return null;
            }
            return getCurrentStep(false);
        }
        return steps[currentStepIndex];
    }
    
    private boolean isSubprogramStep(){
        if(currentStepIndex == -1){
            return false;
        }
        return (steps[currentStepIndex] instanceof SubprogramStep);
    }
    
    @Override
    public boolean isStarted(){
        return (currentStepIndex != -1);
    }
    
    @Override
    public boolean isFinished(){
        return (currentStepIndex >= steps.length);
    }
    
    @Override
    public void resetExecution(){
        currentStepIndex = -1;
    }

    public ProgramStep[] getSteps(){
        return steps;
    }

    public void setSteps(ProgramStep[] steps){
        this.steps = steps;
    }

    @Override
    public ProgramStep getCurrentStep(){
        return currentStep;
    }
}
