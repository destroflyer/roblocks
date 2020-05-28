/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

    
/**
 *
 * @author Carl
 */
public class ScreenController_HowTo extends GameScreenController{

    public ScreenController_HowTo(){
        
    }
    
    public void back(){
        application.openStartMenu();
    }
    
    public void setCurrentProgramStep(int programStepId){
        getImageRenderer("currentProgramStepIcon").setImage(createImage("interface/images/program_steps/" + programStepId + ".png"));
    }
}
