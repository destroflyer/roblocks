/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game;

import com.destroflyer.jme3.cubes.Block;
import com.destroflyer.jme3.cubes.Vector3Int;
import com.destroflyer.roblocks.application.JumpControl;
import com.destroflyer.roblocks.application.MovementControl;
import com.destroflyer.roblocks.application.engine.*;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.renderer.queue.RenderQueue;

/**
 *
 * @author Carl
 */
public class MapObject extends Node{

    public MapObject(String modelName){
        setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        modelObject = new ModelObject("models/" + modelName + "/skin.xml");
        attachChild(modelObject);
        addControl(new MovementControl());
        setGridDirection(Direction.SOUTH);
    }
    public static Vector3Int[] DIRECTION_VECTORS = new Vector3Int[]{
        new Vector3Int(0, 0, 0),
        new Vector3Int(0, 0, -1),
        new Vector3Int(1, 0, 0),
        new Vector3Int(0, 0, 1),
        new Vector3Int(-1, 0, 0),
    };
    protected Map map;
    private Vector3Int gridLocation = new Vector3Int();
    private int direction;
    private Vector3fTransition movement;
    private Vector3fTransition turning;
    protected ModelObject modelObject;
    
    public void setGridLocation(Vector3Int gridLocation){
        this.gridLocation.set(gridLocation);
        setLocalTranslation(calculateLocalTranslation());
        movement = new Vector3fTransition(getLocalTranslation().clone());
    }
    
    public void setGridDirection(int direction){
        this.direction = direction;
        turning = new Vector3fTransition(getVector3f(getDirectionVector()));
        JMonkeyUtil.setLocalRotation(this, turning.getVector3f());
    }
    
    public boolean move(){
        Vector3Int location = getFacedGridLocation();
        if(map.isGridFree(location)
        && map.isGridBlocked(location.subtract(0, 1, 0))){
            gridLocation.set(location);
            movement.start(calculateLocalTranslation(), (ProgramExecution.SPEED * Map.TILE_SIZE));
            return true;
        }
        return false;
    }
    
    public Vector3Int getFacedGridLocation(){
        return gridLocation.add(getDirectionVector());
    }
    
    public Vector3Int getDirectionVector(){
        return DIRECTION_VECTORS[direction];
    }
    
    public void turn(boolean rightOrLeft){
        direction = Direction.turn(direction, rightOrLeft);
        turning.startTimed(getVector3f(getDirectionVector()), (0.5f / ProgramExecution.SPEED));
    }
    
    public boolean jump(){
        Vector3Int jumpLocation = null;
        Vector3Int location = getFacedGridLocation();
        if(map.isGridBlocked(location)){
            if(map.isGridFree(gridLocation.add(0, 1, 0))){
                location.addLocal(0, 1, 0);
                if(map.isGridFree(location)){
                    jumpLocation = location;
                }
            }
        }
        else{
            location.subtractLocal(0, 1, 0);
            if(map.isGridFree(location)){
                do{
                    location.subtractLocal(0, 1, 0);
                }while(map.isGridFree(location) && (location.getY() >= 0));
                if(location.getY() >= 0){
                    location.addLocal(0, 1, 0);
                    jumpLocation = location;
                }
            }
        }
        addControl(new JumpControl(Map.TILE_SIZE));
        if(jumpLocation != null){
            float linearDistance = (float) (Math.sqrt(1 + Math.pow(jumpLocation.getY() - gridLocation.getY(), 2)) * Map.TILE_SIZE);
            gridLocation.set(jumpLocation);
            movement.start(calculateLocalTranslation(), (ProgramExecution.SPEED * linearDistance));
            return true;
        }
        return false;
    }

    public void updateTransform(float lastTimePerFrame){
        movement.onNextFrameCalculation(lastTimePerFrame);
        if(movement.hasChangedSinceLastFrame()){
            setLocalTranslation(movement.getVector3f());
        }
        turning.onNextFrameCalculation(lastTimePerFrame);
        if(turning.hasChangedSinceLastFrame()){
            JMonkeyUtil.setLocalRotation(this, turning.getVector3f());
        }
    }

    public boolean buildBlock(Block block){
        Vector3Int location = getFacedGridLocation();
        if (map.isGridFree(location) && map.isGridBlocked(location.subtract(0, 1, 0))) {
            map.getBlockTerrain().setBlock(location, block);
        }
        return false;
    }

    public boolean dig(){
        Vector3Int digLocation = gridLocation.subtract(0, 1, 0);
        Vector3Int location = digLocation.clone();
        do{
            location.subtractLocal(0, 1, 0);
        }while(map.isGridFree(location) && (location.getY() >= 0));
        if(location.getY() >= 0){
            location.addLocal(0, 1, 0);
            float fallHeight = ((gridLocation.getY() - location.getY()) * Map.TILE_SIZE);
            gridLocation.set(location);
            movement.start(calculateLocalTranslation(), (ProgramExecution.SPEED * fallHeight));
            map.getBlockTerrain().removeBlock(digLocation);
            return true;
        }
        return false;
    }

    public void afterProgramStep(){
        removeControl(JumpControl.class);
        movement.finish();
        setLocalTranslation(calculateLocalTranslation());
        modelObject.stopAndRewindAnimation();
    }
    
    private Vector3f calculateLocalTranslation(){
        return getVector3f(gridLocation).add(0.5f, 0, 0.5f).mult(Map.TILE_SIZE);
    }

    public Map getMap(){
        return map;
    }

    public void setMap(Map map){
        this.map = map;
    }

    public boolean isMoving(){
        return movement.isRunning();
    }

    public Vector3Int getGridLocation(){
        return gridLocation;
    }

    public ModelObject getModelObject(){
        return modelObject;
    }
    
    public int getID(){
        return Util.getUniqueObjectID(this);
    }
    
    public static Vector3f getVector3f(Vector3Int vector3Int){
        return new Vector3f(vector3Int.getX(), vector3Int.getY(), vector3Int.getZ());
    }
}
