/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game.program_steps;

import com.destroflyer.roblocks.game.*;
import com.destroflyer.roblocks.game.program_steps.parts.*;

/**
 *
 * @author Carl
 */
public class JumpStep extends ProgramStep{

    public JumpStep(){
        
    }

    @Override
    public ProgramStepPart[] generateParts(){
        return new ProgramStepPart[]{
            new JumpPart(),
            new AnimationPart("walk", 2.5f, 1)
        };
    }
}
