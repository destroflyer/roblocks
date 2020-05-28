/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game.program_steps.parts;

import com.destroflyer.jme3.cubes.Block;
import com.destroflyer.roblocks.game.*;

/**
 *
 * @author Carl
 */
public class BuildPart extends ProgramStepPart{

    public BuildPart(Block block){
        this.block = block;
    }
    private Block block;

    @Override
    public void execute(Robot robot){
        robot.buildBlock(block);
    }

    @Override
    public float getDuration(){
        return 0;
    }
}
