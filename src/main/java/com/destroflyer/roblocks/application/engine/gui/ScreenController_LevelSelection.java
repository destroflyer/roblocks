/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

import java.io.File;
import java.util.List;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import com.destroflyer.roblocks.application.engine.*;
import com.destroflyer.roblocks.game.*;
import com.destroflyer.roblocks.game.maps.*;
    
/**
 *
 * @author Carl
 */
public class ScreenController_LevelSelection extends GameScreenController{

    public ScreenController_LevelSelection(){
        
    }
    private Map[] levelsMaps;
    private Map[] editorMaps;

    @Override
    public void onStartup(){
        super.onStartup();
        removeMapLists();
        levelsMaps = loadMaps("levels/");
        editorMaps = loadMaps("editor/");
        buildMapsList(3, 25, false);
        buildMapsList(42, 25, true);
    }
    
    private void removeMapLists(){
        List<Element> children = getElementByID("levels").getChildren();
        for (Element child : children) {
            child.markForRemoval();
        }
    }
    
    private Map[] loadMaps(String subDirectory){
        Map[] maps = new Map[0];
        if(application.isRunningOnMobile()){
            if(subDirectory.equals("levels/")){
                maps = new Map[7];
                for(int i=0;i<maps.length;i++){
                    int mapNumber = (i + 1);
                    maps[i] = MapFileHandler.loadInputStream("" + mapNumber, Util.getResourceInputStrean("/com/destroflyer/roblocks/resources/maps/levels/" + mapNumber + ".map"));
                }
            }
        }
        else{
            File[] files = MapFileHandler.getMapFiles(subDirectory);
            maps = new Map[files.length];
            for(int i=0;i<files.length;i++){
                maps[i] = MapFileHandler.loadFile(files[i]);
            }
        }
        return maps;
    }
    
    private void buildMapsList(float offsetX, float offsetY, boolean showEditorMaps){
        Screen screen = nifty.getCurrentScreen();
        Element levelsLayer = screen.findElementByName("levels");
        float x;
        float y = offsetY;
        int columnsCount = 4;
        int rowIndex = 0;
        int columnIndex = 0;
        Map[] maps = (showEditorMaps?editorMaps:levelsMaps);
        for(int i=0;i<maps.length;i++){
            x = offsetX;
            for(int r=0;r<columnIndex;r++){
                x += 8;
            }
            Map map = maps[i];
            ImageBuilder containerImage = new ImageBuilder();
            containerImage.x(x + "%");
            containerImage.y(y + "%");
            containerImage.width("6.25%");
            containerImage.height("11.1%");
            containerImage.filename("interface/images/" + (showEditorMaps?"level_container_editor.png":"level_container.png"));
            containerImage.interactOnClick("openLevel(" + (showEditorMaps?"true":"false") + "," + i + ")");
            containerImage.build(nifty, screen, levelsLayer);
            y += 3.7f;
            TextBuilder levelNumber = new TextBuilder();
            levelNumber.x(x + "%");
            levelNumber.y(y + "%");
            levelNumber.width("6.25%");
            String title = (showEditorMaps?map.getMapInformation().getName():"" + (i + 1));
            levelNumber.text(title);
            levelNumber.font("interface/fonts/IrisUPC_30b.fnt");
            levelNumber.build(nifty, screen, levelsLayer);
            y -= 3.7f;
            columnIndex++;
            if(columnIndex >= columnsCount){
                rowIndex++;
                columnIndex = 0;
                y += 13.5f;
            }
        }
    }
    
    public void openLevel(String nifty_OpenInEditor, String nifty_LevelIndex){
        boolean openInEditor = nifty_OpenInEditor.equals("true");
        int levelIndex = Util.parseInt(nifty_LevelIndex);
        if(openInEditor){
            application.openLevelEditor(editorMaps[levelIndex]);
        }
        else{
            application.openLevel(levelsMaps[levelIndex]);
        }
    }
    
    public void back(){
        application.openStartMenu();
    }
    
    public void openEditor(){
        application.openLevelEditor(new EditorStartMap());
    }
}
