/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application;

import com.destroflyer.roblocks.game.*;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.Trigger;

/**
 *
 * @author Carl
 */
public class MapAppState extends AbstractAppState implements ActionListener{

    public MapAppState(Map map){
        this.map = map;
    }
    protected MainApplication mainApplication;
    protected Map map;
    protected boolean isExecutingProgram = false;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication = (MainApplication) application;
        mainApplication.getRootNode().attachChild(map);
        playBackgroundMusic(map.getMusicPath());
        if(map.getMusicPath() != null){
            mainApplication.getAudioManager().playBackgroundMusic(map.getMusicPath());
        }
        else{
            mainApplication.getAudioManager().stopBackgroundMusic();
        }
        map.prepareCamera(mainApplication.getCamera());
        map.prepareForProgram();
    }
    
    protected void playBackgroundMusic(String musicPath){
        if(musicPath != null){
            mainApplication.getAudioManager().playBackgroundMusic(musicPath);
        }
        else{
            mainApplication.getAudioManager().stopBackgroundMusic();
        }
    }
    
    protected void addInputListener(String actionName, Trigger... triggers){
        mainApplication.getInputManager().addMapping(actionName, triggers);
        mainApplication.getInputManager().addListener(this, actionName);
    }

    protected void removeInputListener(String actionName){
        if(mainApplication.getInputManager().hasMapping(actionName)){
            mainApplication.getInputManager().deleteMapping(actionName);
        }
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getRootNode().detachChild(map);
        mainApplication.getInputManager().removeListener(this);
    }

    public void onAction(String actionName, boolean value, float lastTimePerFrame){
        
    }
    
    public void startProgram(final ProgramExecution programExecution){
        mainApplication.enqueueTask(new Runnable(){

            public void run(){
                map.getControl(ProgramControl.class).execute(programExecution);
                setIsExecutingProgram(true);
            }
        });
    }

    public void stopProgram(){
        mainApplication.enqueueTask(new Runnable(){

            public void run(){
                map.getControl(ProgramControl.class).stopExecution();
                setIsExecutingProgram(false);
            }
        });
    }

    public boolean isExecutingProgram(){
        return isExecutingProgram;
    }

    public void setIsExecutingProgram(boolean isExecutingProgram){
        this.isExecutingProgram = isExecutingProgram;
    }

    public Map getMap(){
        return map;
    }
}
