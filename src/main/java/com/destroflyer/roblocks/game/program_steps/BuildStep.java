/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game.program_steps;

import com.destroflyer.jme3.cubes.Block;
import com.destroflyer.roblocks.game.*;
import com.destroflyer.roblocks.game.program_steps.parts.*;

/**
 *
 * @author Carl
 */
public class BuildStep extends ProgramStep{

    public BuildStep(Block block){
        this.block = block;
    }
    private Block block;

    @Override
    public ProgramStepPart[] generateParts(){
        return new ProgramStepPart[]{
            new AnimationPart("auto_attack", 1, 1),
            new BuildPart(block),
            new PausePart(1)
        };
    }
}
