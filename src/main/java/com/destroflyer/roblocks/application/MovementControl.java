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
import com.destroflyer.roblocks.game.MapObject;

/**
 *
 * @author Carl
 */
public class MovementControl extends AbstractControl{

    @Override
    protected void controlUpdate(float lastTimePerFrame){
        MapObject mapObject = (MapObject) spatial;
        mapObject.updateTransform(lastTimePerFrame);
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort){
        
    }

    @Override
    public Control cloneForSpatial(Spatial spatial){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
