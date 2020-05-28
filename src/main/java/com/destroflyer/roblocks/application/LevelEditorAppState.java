/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application;

import com.destroflyer.jme3.cubes.Block;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.destroflyer.jme3.cubes.BlockNavigator;
import com.destroflyer.jme3.cubes.BlockTerrainControl;
import com.destroflyer.jme3.cubes.Vector3Int;
import com.destroflyer.roblocks.application.engine.gui.ScreenController_LevelEditor;
import com.destroflyer.roblocks.blocks.*;
import com.destroflyer.roblocks.game.*;

/**
 *
 * @author Carl
 */
public class LevelEditorAppState extends LevelAppState{

    public LevelEditorAppState(Map map){
        super(map);
        map.setLevelEditorAppState(this);
        map.prepareForProgram();
        blockTerrain = map.getBlockTerrain().clone();
    }
    private enum BlockModificationType{
        NONE,
        SET,
        REMOVE
    }
    private boolean isGUIInitialized = false;
    private BlockTerrainControl blockTerrain;
    private Block[] blocks = new Block[]{
        BlockAssets.BLOCK_GRASS,
        BlockAssets.BLOCK_BOX,
        BlockAssets.BLOCK_STONE_TILE,
        BlockAssets.BLOCK_WOOD,
        BlockAssets.BLOCK_SNOW_EARTH,
        BlockAssets.BLOCK_PINE_BARK,
        BlockAssets.BLOCK_LEAVES,
        BlockAssets.BLOCK_SAND,
        BlockAssets.BLOCK_CACTUS,
        BlockAssets.BLOCK_GOLD
    };
    private String[] blockNames = new String[]{
        "Grass",
        "Box",
        "Stone Tile",
        "Wood",
        "Snow Earth",
        "Pine Bark",
        "Leaves",
        "Sand",
        "Cactus",
        "Gold"
    };
    private int selectedBlockClassIndex = 0;
    private String[] musicPaths = new String[]{
        null,
        "sounds/music/clouds_castle.ogg",
        "sounds/music/air_ducts.ogg",
        "sounds/music/sand_and_water.ogg",
        "sounds/music/starry_island.ogg",
        "sounds/music/aquatic_circus.ogg",
    };
    private int selectedMusicIndex;
    private BlockModificationType blockModificationType = BlockModificationType.NONE;
    private Vector3Int lastBlockModificationLocation;
    private boolean isMouseWheelPressed;
    private RobotSpawn currentModifyingRobotSpawn;
    private boolean shouldSelectedRobotSpawnBeDeleted;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        removeInputListener("FLYCAM_Rise");
        removeInputListener("FLYCAM_Lower");
        addInputListener("set_start_location", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
        addInputListener("set_block", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        addInputListener("remove_block", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        addInputListener("block_selection_previous", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        addInputListener("block_selection_next", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        addInputListener("camera_free", new KeyTrigger(KeyInput.KEY_Z));
        mainApplication.getNiftyAppState().openLevelEditor();
        if(map.getMusicPath() != null){
            for(int i=0;i<musicPaths.length;i++){
                if(map.getMusicPath().equals(musicPaths[i])){
                    selectedMusicIndex = i;
                    break;
                }
            }
        }
    }

    @Override
    public void cleanup(){
        super.cleanup();
        mainApplication.getNiftyAppState().closeLevelEditor();
    }

    @Override
    public void onAction(String actionName, boolean value, float lastTimePerFrame){
        super.onAction(actionName, value, lastTimePerFrame);
        ScreenController_LevelEditor screenController_LevelEditor = mainApplication.getNiftyAppState().getScreenController(ScreenController_LevelEditor.class);
        if(screenController_LevelEditor.getNifty() != null){
            screenController_LevelEditor.looseFocus();
        }
        if(actionName.equals("set_start_location")){
            isMouseWheelPressed = value;
            if(isMouseWheelPressed){
                Vector3Int blockLocation = getCurrentPointedBlockLocation(true);
                if(blockLocation != null){
                    currentModifyingRobotSpawn = map.getRobotSpawn(blockLocation);
                    shouldSelectedRobotSpawnBeDeleted = true;
                    if(currentModifyingRobotSpawn == null){
                        currentModifyingRobotSpawn = new RobotSpawn(blockLocation, Direction.SOUTH);
                        shouldSelectedRobotSpawnBeDeleted = false;
                        map.addRobotSpawn(currentModifyingRobotSpawn);
                        map.prepareForProgram();
                        lastBlockModificationLocation = blockLocation;
                    }
                }
            }
            else{
                if(shouldSelectedRobotSpawnBeDeleted){
                    map.removeRobotSpawn(currentModifyingRobotSpawn);
                    map.prepareForProgram();
                    lastBlockModificationLocation = currentModifyingRobotSpawn.getLocation();
                }
                currentModifyingRobotSpawn = null;
            }
        }
        else if(actionName.equals("set_block")){
            if(value){
                Vector3Int blockLocation = getCurrentPointedBlockLocation(true);
                if(blockLocation != null){
                    stopProgram();
                    blockTerrain.setBlock(blockLocation, blocks[selectedBlockClassIndex]);
                    map.prepareForProgram();
                    blockModificationType = BlockModificationType.SET;
                    lastBlockModificationLocation = blockLocation;
                }
            }
            else{
                blockModificationType = BlockModificationType.NONE;
                lastBlockModificationLocation = null;
            }
        }
        else if(actionName.equals("remove_block")){
            blockModificationType = (value?BlockModificationType.REMOVE:BlockModificationType.NONE);
            lastBlockModificationLocation = null;
        }
        else if(actionName.equals("block_selection_previous") && value){
            onMouseWheelChanged(true);
        }
        else if(actionName.equals("block_selection_next") && value){
            onMouseWheelChanged(false);
        }
        else if(actionName.equals("camera_free") && value){
            boolean enable = (!mainApplication.getFlyByCamera().isEnabled());
            mainApplication.getFlyByCamera().setEnabled(enable);
            if(enable){
                mainApplication.getInputManager().setCursorVisible(false);
            }
            else{
                map.getCameraState().set(mainApplication.getCamera());
            }
        }
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        if((!isGUIInitialized) && (mainApplication.getNiftyAppState().getScreenController(ScreenController_LevelEditor.class) != null)){
            updateSelectedBlockType();
            updateMusic();
            isGUIInitialized = true;
        }
        if(blockModificationType == BlockModificationType.REMOVE){
            Vector3Int blockLocation = getCurrentPointedBlockLocation(false);
            if((blockLocation != null) && (!blockLocation.equals(lastBlockModificationLocation))){
                stopProgram();
                blockTerrain.removeBlock(blockLocation);
                map.prepareForProgram();
                map.getBlockTerrain().updateSpatial();
                lastBlockModificationLocation = getCurrentPointedBlockLocation(false);
            }
        }
    }
    
    private Vector3Int getCurrentPointedBlockLocation(boolean getNeighborLocation){
        CollisionResults results = mainApplication.getRayCastingResults_Cursor(map);
        if(results.size() > 0){
            Vector3f collisionContactPoint = map.worldToLocal(results.getClosestCollision().getContactPoint(), null);
            return BlockNavigator.getPointedBlockLocation(blockTerrain, collisionContactPoint, getNeighborLocation);
        }
        return null;
    }
    
    private void onMouseWheelChanged(boolean upOrDown){
        if(isMouseWheelPressed){
            Vector3Int blockLocation = getCurrentPointedBlockLocation(true);
            if(blockLocation != null){
                currentModifyingRobotSpawn.setDirection(Direction.turn(currentModifyingRobotSpawn.getDirection(), upOrDown));
                shouldSelectedRobotSpawnBeDeleted = false;
                map.prepareForProgram();
                lastBlockModificationLocation = blockLocation;
            }
        }
        else{
            selectBlockType(!upOrDown);
        }
    }
    
    private void selectBlockType(boolean nextOrPrevious){
        int newSelectedBlockClassIndex = (selectedBlockClassIndex + (nextOrPrevious?1:-1));
        if((newSelectedBlockClassIndex >= 0) && (newSelectedBlockClassIndex < blocks.length)){
            selectedBlockClassIndex = newSelectedBlockClassIndex;
            updateSelectedBlockType();
        }
    }
    
    private void updateSelectedBlockType(){
        String blockName = blockNames[selectedBlockClassIndex];
        mainApplication.getNiftyAppState().getScreenController(ScreenController_LevelEditor.class).setSelectedBlockName(blockName);
    }
    
    public void selectNextMusic(){
        selectedMusicIndex++;
        if(selectedMusicIndex >= musicPaths.length){
            selectedMusicIndex = 0;
        }
        map.setMusicPath(musicPaths[selectedMusicIndex]);
        updateMusic();
    }
    
    private void updateMusic(){
        mainApplication.getNiftyAppState().getScreenController(ScreenController_LevelEditor.class).setMusicName(getMusicName(map.getMusicPath()));
        playBackgroundMusic(map.getMusicPath());
    }
    
    private static String getMusicName(String musicPath){
        if(musicPath != null){
            String[] parts = musicPath.split("/");
            String fileName = parts[parts.length - 1];
            return fileName.substring(0, (fileName.length() - 4));
        }
        return "-";
    }

    @Override
    public void onLevelCompleted(){
        
    }

    public BlockTerrainControl getBlockTerrain(){
        return blockTerrain;
    }
}
