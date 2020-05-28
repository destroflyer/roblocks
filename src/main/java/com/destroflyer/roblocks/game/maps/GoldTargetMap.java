/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game.maps;

import com.destroflyer.roblocks.blocks.BlockAssets;
import com.destroflyer.roblocks.game.Map;
import com.destroflyer.roblocks.game.Robot;

/**
 *
 * @author Carl
 */
public class GoldTargetMap extends Map{

    public GoldTargetMap(){
        
    }

    @Override
    public boolean isObjectiveAccomplished(){
        for(Robot robot : robots){
            if(blockTerrain.getBlock(robot.getGridLocation().subtract(0, 1, 0)) != BlockAssets.BLOCK_GOLD){
                return false;
            }
        }
        return true;
    }
}
