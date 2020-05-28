/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

import de.lessvoid.nifty.Nifty;

/**
 *
 * @author Carl
 */
public class ScreenChange{

    public ScreenChange(Nifty nifty, String targetScreenID){
        this.nifty = nifty;
        this.targetScreenID = targetScreenID;
    }
    private Nifty nifty;
    private String targetScreenID;
    
    public boolean hasChanged(){
        return nifty.getCurrentScreen().getScreenId().equals(targetScreenID);
    }

    public Nifty getNifty(){
        return nifty;
    }
}
