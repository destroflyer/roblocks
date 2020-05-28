/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class ModelObject extends Node{

    public ModelObject(String skinFilePath){
        ModelSkin skin = new ModelSkin(skinFilePath);
        modelSpatial = skin.loadSpatial();
        attachChild(modelSpatial);
        AnimControl animationControl = modelSpatial.getControl(AnimControl.class);
        if(animationControl != null){
            animationChannel = animationControl.createChannel();
        }
    }
    protected Spatial modelSpatial;
    private AnimChannel animationChannel;
    
    public void playAnimation(String animationName){
        playAnimation(animationName, 1);
    }
    
    public void playAnimation(final String animationName, final float speed){
        if(!animationName.equals(animationChannel.getAnimationName())){
            animationChannel.setAnim(animationName);
            animationChannel.setSpeed(speed);
        }
    }
    
    public void stopAndRewindAnimation(){
        animationChannel.reset(true);
    }
}
