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
public class AnimationPart extends ProgramStepPart{

    public AnimationPart(String animationName, float speed, float duration){
        this.animationName = animationName;
        this.speed = speed;
        this.duration = duration;
    }
    private String animationName;
    private float speed;
    private float duration;

    @Override
    public void execute(Robot robot){
        robot.getModelObject().playAnimation(animationName, (ProgramExecution.SPEED * speed));
    }

    @Override
    public float getDuration(){
        return duration;
    }
}
