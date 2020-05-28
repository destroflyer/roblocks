/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

import java.io.File;
import com.destroflyer.roblocks.game.*;
import com.destroflyer.roblocks.application.LevelEditorAppState;

/**
 *
 * @author Carl
 */
public class ScreenController_LevelEditor extends GameScreenController{

    public ScreenController_LevelEditor(){
        
    }
    private LevelEditorAppState levelEditorAppState;

    @Override
    public void onStartup(){
        super.onStartup();
        levelEditorAppState = application.getStateManager().getState(LevelEditorAppState.class);
        MapInformation mapInformation = levelEditorAppState.getMap().getMapInformation();
        getTextField("input_levelName").setText((mapInformation != null)?mapInformation.getName():("test_" + System.currentTimeMillis()));
        looseFocus();
    }
    
    public void looseFocus(){
        getElementByID("input_levelName").disableFocus();
    }
    
    public void setSelectedBlockName(String blockName){
        getTextRenderer("selectedBlockName").setText("Block: " + blockName);
    }
    
    public void selectNextMusic(){
        levelEditorAppState.selectNextMusic();
    }
    
    public void setMusicName(String musicName){
        getTextRenderer("musicName").setText("Music: " + musicName);
    }
    
    public void saveMap(){
        String levelName = getTextField("input_levelName").getText();
        if(!levelName.isEmpty()){
            File file = new File(MapFileHandler.MAPS_DIRECTORY + "editor/" + levelName + MapFileHandler.MAP_FILE_SUFFIX);
            MapFileHandler.saveFile(file, levelEditorAppState.getMap());
        }
    }
}
