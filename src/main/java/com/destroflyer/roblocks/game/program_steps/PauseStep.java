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
public class PauseStep extends ProgramStep{

    public PauseStep(float duration){
        this.duration = duration;
    }
    private float duration;

    @Override
    public ProgramStepPart[] generateParts(){
        return new ProgramStepPart[]{
            new PausePart(duration)
        };
    }
}
