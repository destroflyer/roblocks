/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game.program_steps.parts;

import com.destroflyer.roblocks.game.*;

/**
 *
 * @author Carl
 */
public class PausePart extends ProgramStepPart{

    public PausePart(float duration){
        this.duration = duration;
    }
    private float duration;

    @Override
    public void execute(Robot robot){
        
    }

    @Override
    public float getDuration(){
        return duration;
    }
}
