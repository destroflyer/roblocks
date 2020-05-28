/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.destroflyer.roblocks.game.*;

/**
 *
 * @author Carl
 */
public class JumpControl extends AbstractControl{

    public JumpControl(float jumpHeight){
        this.jumpHeight = jumpHeight;
    }
    private float jumpHeight;
    private float timeSinceJumpStart;
    private float lastJumpHeight;

    @Override
    protected void controlUpdate(float lastTimePerFrame){
        timeSinceJumpStart += (ProgramExecution.SPEED * lastTimePerFrame);
        float currentJumpHeight = getCurrentJumpHeight();
        float additionalY = currentJumpHeight;
        if(spatial instanceof MapObject){
            MapObject mapObject = (MapObject) spatial;
            if(!mapObject.isMoving()){
                additionalY -= lastJumpHeight;
            }
        }
        spatial.setLocalTranslation(spatial.getLocalTranslation().add(0, additionalY, 0));
        lastJumpHeight = currentJumpHeight;
    }
    
    private float getCurrentJumpHeight(){
        float factor = (4 * jumpHeight);
        return ((-1 * factor * timeSinceJumpStart * timeSinceJumpStart) + (factor * timeSinceJumpStart));
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort){
        
    }

    @Override
    public Control cloneForSpatial(Spatial spatial){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
