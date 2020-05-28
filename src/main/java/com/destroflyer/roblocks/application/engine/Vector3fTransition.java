/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine;

import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public class Vector3fTransition{

    public Vector3fTransition(Vector3f vector3f){
        this.vector3f = vector3f;
    }
    protected Vector3f vector3f;
    protected Vector3f targetVector3f;
    private Vector3f tempVector3f = new Vector3f();
    private float lastTargetDistance;
    private Vector3f direction = new Vector3f();
    private float minimumTargetDistance = 0;
    protected boolean stopAtInvalidVector3f = false;
    private boolean hasChangedSinceLastFrame = false;
    private boolean isTargetJustReached = false;
    
    public void start(Vector3f targetVector3f, float speed){
        setTargetVector3f(targetVector3f);
        direction = targetVector3f.subtract(vector3f).normalizeLocal().multLocal(speed);
    }
    
    public void startTimed(Vector3f targetVector3f, float durationInSeconds){
        setTargetVector3f(targetVector3f);
        direction = targetVector3f.subtract(vector3f).divideLocal(durationInSeconds);
    }
    
    public void setTargetVector3f(Vector3f targetVector3f){
        boolean hasTargetVectorChanged = (!targetVector3f.equals(this.targetVector3f));
        this.targetVector3f = targetVector3f.clone();
        if(hasTargetVectorChanged){
            lastTargetDistance = calculateTargetDistance();
        }
    }
    
    public void finish(){
        if(targetVector3f != null){
            vector3f.set(targetVector3f);
        }
    }
    
    public void onNextFrameCalculation(float timePerFrame){
        hasChangedSinceLastFrame = false;
        boolean wasTargetJustReached = isTargetJustReached;
        isTargetJustReached = false;
        if(targetVector3f != null){
            if(isTargetReached()){
                if(minimumTargetDistance == 0){
                    vector3f.set(targetVector3f);
                }
                stop();
                onTargetReached();
                hasChangedSinceLastFrame = (!wasTargetJustReached);
            }
            else{
                tempVector3f.set(vector3f);
                vector3f.addLocal(direction.mult(timePerFrame));
                if(!isValidVector3f()){
                    vector3f.set(tempVector3f);
                    if(stopAtInvalidVector3f){
                        stop();
                    }
                }
                hasChangedSinceLastFrame = true;
            }
        }
    }
    
    public boolean isValidVector3f(){
        return true;
    }
    
    private boolean isTargetReached(){
        float movementTargetDistance = calculateTargetDistance();
        if((movementTargetDistance == 0) || (movementTargetDistance > lastTargetDistance)){
            return true;
        }
        lastTargetDistance = movementTargetDistance;
        return false;
    }
    
    private float calculateTargetDistance(){
        float distance = (vector3f.distance(targetVector3f) - minimumTargetDistance);
        if(distance < 0){
            distance = 0;
        }
        return distance;
    }
    
    public void stop(){
        targetVector3f = null;
        direction.set(Vector3f.ZERO);
        isTargetJustReached = false;
    }
    
    protected void onTargetReached(){
        isTargetJustReached = true;
    }
    
    public boolean isRunning(){
        return (!direction.equals(Vector3f.ZERO));
    }

    public boolean isStopAtInvalidVector3f(){
        return stopAtInvalidVector3f;
    }

    public void setStopAtInvalidVector3f(boolean stopAtInvalidVector3f){
        this.stopAtInvalidVector3f = stopAtInvalidVector3f;
    }

    public boolean hasChangedSinceLastFrame(){
        return hasChangedSinceLastFrame;
    }

    public boolean isTargetJustReached(){
        return isTargetJustReached;
    }

    public Vector3f getVector3f(){
        return vector3f;
    }

    public Vector3f getDirection(){
        return direction;
    }

    public float getMinimumTargetDistance(){
        return minimumTargetDistance;
    }

    public void setMinimumTargetDistance(float minimumTargetDistance){
        this.minimumTargetDistance = minimumTargetDistance;
    }
}
