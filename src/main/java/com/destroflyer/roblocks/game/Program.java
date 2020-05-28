/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game;

/**
 *
 * @author Carl
 */
public abstract class Program{

    public Program(){
        
    }

    public abstract ProgramStep getNextStep();
    
    public abstract boolean isStarted();
    
    public abstract boolean isFinished();
    
    public abstract void resetExecution();

    public abstract ProgramStep getCurrentStep();
}
