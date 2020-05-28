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
public class LevelSelectionMap extends Map{

    public LevelSelectionMap(){
        cameraState = new CameraState(new Vector3f(2.153f, 10.85f, 32.367f), new Vector3f());
        addRobotSpawn(new RobotSpawn(new Vector3Int(1, 1, 1), Direction.EAST));
        musicPath = null;
    }

    @Override
    public void prepareBlockTerrainForProgram(){
        super.prepareBlockTerrainForProgram();
        blockTerrain = BlockAssets.createNewBlockTerrain(new Vector3Int(1, 1, 1));
        blockTerrain.setBlockArea(new Vector3Int(0, 0, 0), new Vector3Int(6, 1, 5), BlockAssets.BLOCK_SNOW_EARTH);
        blockTerrain.setBlock(new Vector3Int(4, 1, 1), BlockAssets.BLOCK_SNOW_EARTH);
        blockTerrain.setBlock(new Vector3Int(2, 1, 3), BlockAssets.BLOCK_SNOW_EARTH);
    }

    @Override
    public boolean isObjectiveAccomplished(){
        return false;
    }
}
