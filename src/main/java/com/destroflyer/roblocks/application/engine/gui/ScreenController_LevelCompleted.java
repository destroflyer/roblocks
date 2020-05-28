/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

/**
 *
 * @author Carl
 */
public class ScreenController_LevelCompleted extends GameScreenController{

    public ScreenController_LevelCompleted(){
        
    }
    
    public void goToLevelSelection(){
        application.openLevelSelection();
    }
}
