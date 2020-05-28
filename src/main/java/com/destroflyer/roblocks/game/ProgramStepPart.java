/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game;

/**
 *
 * @author Carl
 */
public abstract class ProgramStepPart{

    public ProgramStepPart(){
        
    }
    
    public abstract void execute(Robot robot);
    
    public abstract float getDuration();
}
