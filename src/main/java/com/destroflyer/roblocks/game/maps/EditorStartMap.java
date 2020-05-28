/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game.maps;

import com.jme3.math.Vector3f;
import com.destroflyer.jme3.cubes.Vector3Int;
import com.destroflyer.roblocks.application.engine.*;
import com.destroflyer.roblocks.blocks.*;
import com.destroflyer.roblocks.game.Direction;
import com.destroflyer.roblocks.game.RobotSpawn;

/**
 *
 * @author Carl
 */
public class EditorStartMap extends GoldTargetMap{

    public EditorStartMap(){
        cameraState = new CameraState(new Vector3f(18.2f, 37.7f, 67), new Vector3f(0, -0.7f, -0.71f));
        addRobotSpawn(new RobotSpawn(new Vector3Int(0, 1, 0), Direction.SOUTH));
    }

    @Override
    public void prepareBlockTerrainForProgram(){
        super.prepareBlockTerrainForProgram();
        blockTerrain = BlockAssets.createNewBlockTerrain(new Vector3Int(1, 1, 1));
        blockTerrain.setBlockArea(new Vector3Int(), new Vector3Int(16, 1, 16), BlockAssets.BLOCK_GRASS);
    }
}
