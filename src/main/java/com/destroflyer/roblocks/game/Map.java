/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game;

import java.util.ArrayList;
import com.jme3.scene.Node;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.destroflyer.jme3.cubes.BlockTerrainControl;
import com.destroflyer.jme3.cubes.Vector3Int;
import com.destroflyer.roblocks.application.*;
import com.destroflyer.roblocks.application.engine.*;
import com.destroflyer.roblocks.blocks.*;

/**
 *
 * @author Carl
 */
public abstract class Map extends Node{
    
    public Map(){
        super("map");
        blockTerrainNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        objectsNode.attachChild(blockTerrainNode);
        attachChild(objectsNode);
        blockTerrain = BlockAssets.createNewBlockTerrain(new Vector3Int());
        addControl(programControl);
    }
    public static final float TILE_SIZE = 3;
    private MapInformation mapInformation;
    private Node objectsNode = new Node("mapObjectsNode");
    protected CameraState cameraState;
    private ProgramControl programControl = new ProgramControl();
    private ArrayList<MapObject> objects = new ArrayList<>();
    private ArrayList<RobotSpawn> robotSpawns = new ArrayList<>();
    protected Robot[] robots;
    protected Class<? extends Robot> robotClass = Robot.class;
    private Node blockTerrainNode = new Node();
    protected BlockTerrainControl blockTerrain;
    protected String musicPath = "sounds/music/clouds_castle.ogg";
    private LevelEditorAppState levelEditorAppState;

    public void prepareCamera(Camera camera){
        if(cameraState != null){
            camera.setLocation(cameraState.getLocation());
            camera.lookAtDirection(cameraState.getDirection(), Vector3f.UNIT_Y);
        }
    }
    
    public void prepareForProgram(){
        respawnRobots();
        if(blockTerrain != null){
            blockTerrainNode.removeControl(blockTerrain);
        }
        if((levelEditorAppState != null) && levelEditorAppState.isInitialized()){
            blockTerrain.setBlocksFromTerrain(levelEditorAppState.getBlockTerrain());
        }
        else{
            prepareBlockTerrainForProgram();
        }
        blockTerrainNode.addControl(blockTerrain);
    }
    
    public void prepareBlockTerrainForProgram(){
        
    }
    
    private void respawnRobots(){
        if(robots != null){
            for(Robot robot : robots){
                removeObject(robot);
            }
        }
        robots = new Robot[robotSpawns.size()];
        for(int i=0;i<robots.length;i++){
            Robot robot = Util.createObjectByClass(robotClass);
            RobotSpawn robotSpawn = robotSpawns.get(i);
            robot.setGridLocation(robotSpawn.getLocation());
            robot.setGridDirection(robotSpawn.getDirection());
            addObject(robot);
            robots[i] = robot;
        }
    }

    public void afterProgramStep(){
        for(Robot robot : robots){
            robot.afterProgramStep();
        }
    }
    
    public abstract boolean isObjectiveAccomplished();

    protected void addObject(MapObject mapObject){
        mapObject.setMap(this);
        objects.add(mapObject);
        objectsNode.attachChild(mapObject);
    }

    protected void removeObject(MapObject mapObject){
        mapObject.setMap(null);
        objects.remove(mapObject);
        objectsNode.detachChild(mapObject);
    }
    
    public boolean isGridBlocked(Vector3Int location){
        return (!isGridFree(location));
    }
    
    public boolean isGridFree(Vector3Int location){
        for(MapObject mapObject : objects){
            if(mapObject.getGridLocation().equals(location)){
                return false;
            }
        }
        return (blockTerrain.getBlock(location) == null);
    }

    public MapInformation getMapInformation(){
        return mapInformation;
    }

    public void setMapInformation(MapInformation mapInformation){
        this.mapInformation = mapInformation;
    }

    public CameraState getCameraState(){
        return cameraState;
    }

    public void setCameraState(CameraState cameraState){
        this.cameraState = cameraState;
    }

    public void clearRobotSpawns(){
        robotSpawns.clear();
    }

    public void addRobotSpawn(RobotSpawn robotSpawn){
        robotSpawns.add(robotSpawn);
    }

    public RobotSpawn getRobotSpawn(Vector3Int robotSpawnLocation){
        for(int i=0;i<robotSpawns.size();i++){
            RobotSpawn robotSpawn = robotSpawns.get(i);
            if(robotSpawn.getLocation().equals(robotSpawnLocation)){
                return robotSpawn;
            }
        }
        return null;
    }

    public void removeRobotSpawn(RobotSpawn robotSpawn){
        robotSpawns.remove(robotSpawn);
    }

    public ArrayList<RobotSpawn> getRobotSpawns(){
        return robotSpawns;
    }

    public Robot[] getRobots(){
        return robots;
    }

    public BlockTerrainControl getBlockTerrain(){
        return blockTerrain;
    }

    public String getMusicPath(){
        return musicPath;
    }

    public void setMusicPath(String musicPath){
        this.musicPath = musicPath;
    }

    public void setLevelEditorAppState(LevelEditorAppState levelEditorAppState){
        this.levelEditorAppState = levelEditorAppState;
    }
}
