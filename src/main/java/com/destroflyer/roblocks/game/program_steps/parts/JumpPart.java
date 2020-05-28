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
public class JumpPart extends ProgramStepPart{

    public JumpPart(){
        
    }

    @Override
    public void execute(Robot robot){
        robot.jump();
    }

    @Override
    public float getDuration(){
        return 0;
    }
}
