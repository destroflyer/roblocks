/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game.maps;

import com.jme3.math.Vector3f;
import com.destroflyer.jme3.cubes.Vector3Int;
import com.destroflyer.roblocks.application.engine.*;
import com.destroflyer.roblocks.blocks.*;
import com.destroflyer.roblocks.game.*;

/**
 *
 * @author Carl
 */
public class HowToMap extends Map{

    public HowToMap(){
        cameraState = new CameraState(new Vector3f(24, 15, 42), new Vector3f(0, -0.6f, -0.8f));
        addRobotSpawn(new RobotSpawn(new Vector3Int(5, 1, 7), Direction.EAST));
        musicPath = null;
    }

    @Override
    public void prepareBlockTerrainForProgram(){
        super.prepareBlockTerrainForProgram();
        blockTerrain = BlockAssets.createNewBlockTerrain(new Vector3Int(1, 1, 1));
        blockTerrain.setBlockArea(new Vector3Int(0, 0, 0), new Vector3Int(16, 1, 12), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlockArea(new Vector3Int(0, 1, 0), new Vector3Int(1, 2, 12), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlockArea(new Vector3Int(15, 1, 0), new Vector3Int(1, 2, 12), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlockArea(new Vector3Int(1, 1, 0), new Vector3Int(14, 2, 1), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlockArea(new Vector3Int(5, 0, 7), new Vector3Int(5, 1, 1), BlockAssets.BLOCK_SAND);
        blockTerrain.setBlockArea(new Vector3Int(5, 0, 9), new Vector3Int(5, 1, 1), BlockAssets.BLOCK_SAND);
        blockTerrain.setBlock(new Vector3Int(5, 0, 8), BlockAssets.BLOCK_SAND);
        blockTerrain.setBlock(new Vector3Int(10, 1, 7), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlock(new Vector3Int(10, 1, 7), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlock(new Vector3Int(10, 1, 8), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlock(new Vector3Int(10, 1, 9), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlock(new Vector3Int(8, 0, 5), BlockAssets.BLOCK_GOLD);
    }

    @Override
    public boolean isObjectiveAccomplished(){
        return false;
    }
}
