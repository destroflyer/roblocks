/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game;

import com.destroflyer.jme3.cubes.Vector3Int;

/**
 *
 * @author Carl
 */
public class RobotSpawn{

    public RobotSpawn(Vector3Int location, int direction){
        this.location = location;
        this.direction = direction;
    }
    private Vector3Int location;
    private int direction;

    public void setLocation(Vector3Int location){
        this.location = location;
    }

    public Vector3Int getLocation(){
        return location;
    }

    public void setDirection(int direction){
        this.direction = direction;
    }

    public int getDirection(){
        return direction;
    }
}
