/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 *
 * @author Carl
 */
public class CameraState{

    public CameraState(Vector3f location, Vector3f direction){
        this.location = location;
        this.direction = direction;
    }
    private Vector3f location;
    private Vector3f direction;

    public Vector3f getLocation(){
        return location;
    }

    public Vector3f getDirection(){
        return direction;
    }
    
    public void set(Camera camera){
        location = camera.getLocation();
        direction = camera.getDirection();
    }
}
