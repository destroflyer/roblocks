/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application;

import com.destroflyer.roblocks.game.*;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;

/**
 *
 * @author Carl
 */
public class ProgramControl extends AbstractControl{

    public ProgramControl(){
        
    }
    private Map map;
    private ProgramExecution programExecution;

    @Override
    public void setSpatial(Spatial spatial){
        super.setSpatial(spatial);
        map = (Map) spatial;
    }

    public void execute(ProgramExecution programExecution){
        this.programExecution = programExecution;
        programExecution.start(map);
    }

    @Override
    protected void controlUpdate(float lastTimePerFrame){
        if(programExecution != null){
            programExecution.onNextFrameCalculation(lastTimePerFrame);
        }
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort){
        
    }

    @Override
    public Control cloneForSpatial(Spatial spatial){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stopExecution(){
        if(programExecution != null){
            programExecution.stop();
            programExecution = null;
        }
    }
}
