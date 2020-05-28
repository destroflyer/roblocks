/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

import de.lessvoid.nifty.render.NiftyImage;
import com.destroflyer.roblocks.application.*;
import com.destroflyer.roblocks.game.*;

/**
 *
 * @author Carl
 */
public abstract class ScreenController_Level extends GameScreenController{

    public ScreenController_Level(){
        
    }
    private NiftyImage startOrStopImage_Start;
    private NiftyImage startOrStopImage_Stop;
    
    @Override
    protected void initialize(){
        startOrStopImage_Start = createImage("interface/images/buttons/ok.png");
        startOrStopImage_Stop = createImage("interface/images/buttons/error.png");
        getSlider("programExecutionSpeed").setEnabled(false);
    }

    @Override
    public void onStartup(){
        super.onStartup();
        setStartOrStopImage(true);
        setExecutionSpeedDisplayVisible(false);
    }
    
    public void stopProgramIfRunning(){
        MapAppState mapAppState = MainApplication.INSTANCE.getStateManager().getState(MapAppState.class);
        if(mapAppState.isExecutingProgram()){
            mapAppState.stopProgram();
        }
    }
    
    public void startOrStopProgram(){
        MapAppState mapAppState = MainApplication.INSTANCE.getStateManager().getState(MapAppState.class);
        boolean startProgram = (!mapAppState.isExecutingProgram());
        if(startProgram){
            Program program = getProgramToExecute();
            ProgramExecution programExecution = new ProgramExecution(program){

                @Override
                protected void onMapObjectiveAccomplished(){
                    super.onMapObjectiveAccomplished();
                    application.getStateManager().getState(LevelAppState.class).onLevelCompleted();
                }
            };
            mapAppState.startProgram(programExecution);
        }
        else{
            mapAppState.stopProgram();
        }
    }
    
    protected abstract Program getProgramToExecute();
    
    public void setStartOrStopImage(boolean startOrStop){
        getImageRenderer("startOrStopProgram").setImage(startOrStop?startOrStopImage_Start:startOrStopImage_Stop);
    }
    
    public void back(){
        application.openLevelSelection();
    }
    
    public void setExecutionSpeedDisplayVisible(boolean isVisible){
        getSlider("programExecutionSpeed").setValue(ProgramExecution.SPEED);
        getElementByID("programExecutionSpeed").setVisible(isVisible);
    }
}
