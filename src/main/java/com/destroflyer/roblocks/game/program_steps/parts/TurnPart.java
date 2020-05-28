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
public class TurnPart extends ProgramStepPart{

    public TurnPart(boolean rightOrLeft){
        this.rightOrLeft = rightOrLeft;
    }
    boolean rightOrLeft;

    @Override
    public void execute(Robot robot){
        robot.turn(rightOrLeft);
    }

    @Override
    public float getDuration(){
        return 0.5f;
    }
}
