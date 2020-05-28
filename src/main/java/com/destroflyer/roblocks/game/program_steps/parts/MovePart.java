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
public class MovePart extends ProgramStepPart{

    public MovePart(){
        
    }

    @Override
    public void execute(Robot robot){
        robot.move();
    }

    @Override
    public float getDuration(){
        return 0;
    }
}
