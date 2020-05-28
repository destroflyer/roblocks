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
public class DigPart extends ProgramStepPart{

    public DigPart(){
        
    }

    @Override
    public void execute(Robot robot){
        robot.dig();
    }

    @Override
    public float getDuration(){
        return 0;
    }
}
