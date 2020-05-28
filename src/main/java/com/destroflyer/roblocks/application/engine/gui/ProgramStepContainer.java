/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

/**
 *
 * @author Carl
 */
public class ProgramStepContainer{

    public ProgramStepContainer(ScreenController_SimpleProgram.ProgramType programType, int stepIndex){
        this.programType = programType;
        this.stepIndex = stepIndex;
    }
    private ScreenController_SimpleProgram.ProgramType programType;
    private int stepIndex;

    public ScreenController_SimpleProgram.ProgramType getProgram(){
        return programType;
    }

    public int getStepIndex(){
        return stepIndex;
    }
}
