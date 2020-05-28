/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

/**
 *
 * @author Carl
 */
public class ScreenController_Start extends GameScreenController{

    public ScreenController_Start(){
        
    }
    
    public void startGame(){
        application.openLevelSelection();
    }
    
    public void openHowTo(){
        application.openHowTo();
    }
    
    public void exitGame(){
        application.close();
    }
}
