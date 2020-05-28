/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game.maps;

import com.jme3.math.Vector3f;
import com.destroflyer.jme3.cubes.BlockTerrainControl;
import com.destroflyer.jme3.cubes.Vector3Int;
import com.destroflyer.roblocks.application.engine.*;
import com.destroflyer.roblocks.blocks.*;
import com.destroflyer.roblocks.game.*;

/**
 *
 * @author Carl
 */
public class StartMenuMap extends Map{

    public StartMenuMap(){
        cameraState = new CameraState(new Vector3f(26, 5.2f, 32.4f), new Vector3f(0, 0, -1));
        musicPath = "sounds/music/two_fat_gangsters.ogg";
    }

    @Override
    public void prepareBlockTerrainForProgram(){
        super.prepareBlockTerrainForProgram();
        blockTerrain = BlockAssets.createNewBlockTerrain(new Vector3Int(1, 1, 1));
        blockTerrain.setBlockArea(new Vector3Int(0, 0, 0), new Vector3Int(18, 1, 10), BlockAssets.BLOCK_GRASS);
        blockTerrain.setBlockArea(new Vector3Int(0, 0, 0), new Vector3Int(18, 5, 1), BlockAssets.BLOCK_GRASS);
        blockTerrain.removeBlockArea(new Vector3Int(3, 4, 0), new Vector3Int(2, 1, 1));
        blockTerrain.removeBlockArea(new Vector3Int(6, 4, 0), new Vector3Int(1, 1, 1));
        blockTerrain.removeBlockArea(new Vector3Int(9, 4, 0), new Vector3Int(2, 1, 1));
        blockTerrain.removeBlockArea(new Vector3Int(12, 4, 0), new Vector3Int(1, 1, 1));
        blockTerrain.removeBlockArea(new Vector3Int(14, 4, 0), new Vector3Int(3, 1, 1));
        blockTerrain.setBlockArea(new Vector3Int(1, 1, 1), new Vector3Int(16, 1, 1), BlockAssets.BLOCK_BOX);
        blockTerrain.setBlockArea(new Vector3Int(3, 2, 1), new Vector3Int(3, 1, 1), BlockAssets.BLOCK_BOX);
        blockTerrain.setBlockArea(new Vector3Int(4, 3, 1), new Vector3Int(1, 1, 1), BlockAssets.BLOCK_BOX);
        blockTerrain.setBlockArea(new Vector3Int(7, 2, 1), new Vector3Int(5, 1, 1), BlockAssets.BLOCK_BOX);
        blockTerrain.setBlockArea(new Vector3Int(8, 3, 1), new Vector3Int(2, 1, 1), BlockAssets.BLOCK_BOX);
        blockTerrain.setBlockArea(new Vector3Int(13, 2, 1), new Vector3Int(2, 1, 1), BlockAssets.BLOCK_BOX);
        addTreeBlocks(blockTerrain, new Vector3Int(13, 1, 3));
    }
    
    private void addTreeBlocks(BlockTerrainControl blockTerrain, Vector3Int location){
        blockTerrain.setBlockArea(location.add(-1, 3, -1), new Vector3Int(3, 1, 3), BlockAssets.BLOCK_LEAVES);
        blockTerrain.setBlock(location.add(0, 4, 0), BlockAssets.BLOCK_LEAVES);
        blockTerrain.setBlockArea(location, new Vector3Int(1, 3, 1), BlockAssets.BLOCK_PINE_BARK);
    }

    @Override
    public boolean isObjectiveAccomplished(){
        return false;
    }
}
