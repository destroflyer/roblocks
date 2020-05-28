/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game.maps;

import com.destroflyer.jme3.cubes.BlockTerrainControl;
import com.destroflyer.jme3.cubes.Vector3Int;
import com.jme3.math.Vector3f;
import com.destroflyer.roblocks.application.engine.*;
import com.destroflyer.roblocks.blocks.*;
import com.destroflyer.roblocks.game.Direction;
import com.destroflyer.roblocks.game.RobotSpawn;

/**
 *
 * @author Carl
 */
public class TestMap extends GoldTargetMap{

    public TestMap(){
        cameraState = new CameraState(new Vector3f(18.2f, 37.7f, 67), new Vector3f(0, -0.7f, -0.71f));
        addRobotSpawn(new RobotSpawn(new Vector3Int(3, 1, 4), Direction.SOUTH));
    }

    @Override
    public void prepareBlockTerrainForProgram(){
        super.prepareBlockTerrainForProgram();
        blockTerrain = BlockAssets.createNewBlockTerrain(new Vector3Int(1, 1, 1));
        blockTerrain.setBlockArea(new Vector3Int(1, 0, 2), new Vector3Int(13, 1, 14), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlockArea(new Vector3Int(7, 0, 1), new Vector3Int(10, 1, 4), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlockArea(new Vector3Int(0, 0, 3), new Vector3Int(1, 1, 6), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlockArea(new Vector3Int(14, 0, 9), new Vector3Int(1, 1, 3), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlockArea(new Vector3Int(14, 0, 13), new Vector3Int(1, 1, 1), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlockArea(new Vector3Int(12, 0, 0), new Vector3Int(3, 1, 1), BlockAssets.BLOCK_GRASS);
        blockTerrain.removeBlockArea(new Vector3Int(10, 0, 15), new Vector3Int(4, 1, 1));
        blockTerrain.setBlock(new Vector3Int(0, 0, 0), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlock(new Vector3Int(4, 0, 1), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlock(new Vector3Int(11, 0, 15), BlockAssets.BLOCK_GRASS);
        blockTerrain.removeBlock(new Vector3Int(0, 0, 15));
        blockTerrain.removeBlock(new Vector3Int(1, 0, 15));
        blockTerrain.removeBlock(new Vector3Int(0, 0, 14));
        blockTerrain.removeBlock(new Vector3Int(4, 0, 15));
        blockTerrain.removeBlock(new Vector3Int(5, 0, 15));
        blockTerrain.removeBlock(new Vector3Int(16, 0, 1));
        blockTerrain.removeBlock(new Vector3Int(16, 0, 4));
        blockTerrain.removeBlock(new Vector3Int(1, 0, 11));
        blockTerrain.removeBlock(new Vector3Int(1, 0, 12));
        blockTerrain.removeBlock(new Vector3Int(1, 0, 2));
        blockTerrain.removeBlock(new Vector3Int(0, 0, 3));
        blockTerrain.removeBlock(new Vector3Int(0, 0, 6));
        addTreeBlocks(blockTerrain, new Vector3Int(1, 1, 5));
        addTreeBlocks(blockTerrain, new Vector3Int(14, 1, 2));
        addTreeBlocks(blockTerrain, new Vector3Int(12, 1, 3));
        blockTerrain.setBlockArea(new Vector3Int(3, 0, 4), new Vector3Int(10, 1, 10), BlockAssets.BLOCK_STONE_TILE);
        blockTerrain.setBlock(new Vector3Int(7, 0, 6), BlockAssets.BLOCK_GOLD);
    }
    
    private void addTreeBlocks(BlockTerrainControl blockTerrain, Vector3Int location){
        blockTerrain.setBlockArea(location.add(-1, 3, -1), new Vector3Int(3, 1, 3), BlockAssets.BLOCK_LEAVES);
        blockTerrain.setBlock(location.add(0, 4, 0), BlockAssets.BLOCK_LEAVES);
        blockTerrain.setBlockArea(location, new Vector3Int(1, 3, 1), BlockAssets.BLOCK_PINE_BARK);
    }
}
