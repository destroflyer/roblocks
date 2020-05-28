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
public class DigStep extends ProgramStep{

    public DigStep(){
        
    }

    @Override
    public ProgramStepPart[] generateParts(){
        return new ProgramStepPart[]{
            new AnimationPart("auto_attack", 1, 1),
            new DigPart(),
            new PausePart(1)
        };
    }
}
