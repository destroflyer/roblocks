/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application;

import com.destroflyer.jme3.cubes.Vector3Int;
import com.destroflyer.roblocks.game.*;
import com.destroflyer.roblocks.application.engine.gui.ScreenController_Level;
import com.destroflyer.roblocks.application.engine.gui.ScreenController_SimpleProgram;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public class LevelAppState extends MapAppState{

    public LevelAppState(Map map){
        super(map);
    }
    private long nextProgramExecutionSpeedHideTimestamp = -1;
    private boolean isLevelCompleted = false;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        stateManager.detach(stateManager.getState(StartMenuAppState.class));
        stateManager.detach(stateManager.getState(LevelSelectionAppState.class));
        addInputListener("program_execution_speed_decrease", new KeyTrigger(KeyInput.KEY_LEFT));
        addInputListener("program_execution_speed_increase", new KeyTrigger(KeyInput.KEY_RIGHT));
        mainApplication.getNiftyAppState().openLevel();
    }

    @Override
    public void cleanup(){
        super.cleanup();
        ProgramExecution.SPEED = 1;
    }

    @Override
    public void onAction(String actionName, boolean value, float lastTimePerFrame){
        super.onAction(actionName, value, lastTimePerFrame);
        if(!isLevelCompleted){
            if(actionName.equals("program_execution_speed_decrease") && value){
                changeProgramExecutionSpeed(false);
            }
            else if(actionName.equals("program_execution_speed_increase") && value){
                changeProgramExecutionSpeed(true);
            }
        }
    }
    
    private void changeProgramExecutionSpeed(boolean increaseOrDecrease){
        float newSpeed = (ProgramExecution.SPEED + (0.5f * (increaseOrDecrease?1:-1)));
        if(newSpeed < 0.5f){
            newSpeed = 0.5f;
        }
        else if(newSpeed > 10){
            newSpeed = 10;
        }
        ProgramExecution.SPEED = newSpeed;
        mainApplication.getNiftyAppState().getScreenController(ScreenController_SimpleProgram.class).setExecutionSpeedDisplayVisible(true);
        nextProgramExecutionSpeedHideTimestamp = (System.currentTimeMillis() + 1000);
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        if(!isLevelCompleted){
            if(nextProgramExecutionSpeedHideTimestamp != -1){
                if(System.currentTimeMillis() >= nextProgramExecutionSpeedHideTimestamp){
                    mainApplication.getNiftyAppState().getScreenController(ScreenController_SimpleProgram.class).setExecutionSpeedDisplayVisible(false);
                    nextProgramExecutionSpeedHideTimestamp = -1;
                }
            }
        }
    }

    public void onLevelCompleted(){
        isLevelCompleted = true;
        mainApplication.getNiftyAppState().showLevelCompletedScreen();
        Robot robot = map.getRobots()[0];
        Vector3Int gridLocation = robot.getFacedGridLocation();
        Vector3f camLocation = new Vector3f(gridLocation.getX(), gridLocation.getY(), gridLocation.getZ()).add(0.5f, 3, 0.5f);
        Vector3Int gridDirection = robot.getDirectionVector();
        Vector3f camDirection = new Vector3f(-1 * gridDirection.getX(), 0, -1 * gridDirection.getZ());
        camLocation.subtractLocal(camDirection.mult(4));
        mainApplication.getCamera().setLocation(camLocation.multLocal(Map.TILE_SIZE));
        mainApplication.getCamera().lookAt(robot.getWorldTranslation(), Vector3f.UNIT_Y);
        robot.getModelObject().playAnimation("dance");
    }

    @Override
    public void setIsExecutingProgram(boolean isExecutingProgram){
        super.setIsExecutingProgram(isExecutingProgram);
        mainApplication.getNiftyAppState().getScreenController(ScreenController_Level.class).setStartOrStopImage(!isExecutingProgram);
    }
}
