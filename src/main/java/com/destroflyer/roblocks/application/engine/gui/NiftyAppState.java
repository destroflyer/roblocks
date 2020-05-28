/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

import java.util.ArrayList;
import java.util.LinkedList;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.destroflyer.roblocks.application.MainApplication;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Carl
 */
public class NiftyAppState extends AbstractAppState{

    public NiftyAppState(){
    }
    private MainApplication mainApplication;
    private Nifty nifty_IngameMenu;
    private Nifty nifty_LevelEditor;
    private ArrayList<Nifty> runningNifties = new ArrayList<>();
    private LinkedList<ScreenChange> remainingScreenChanges = new LinkedList<>();

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        mainApplication = (MainApplication) application;
        nifty_IngameMenu = createNifty("interface/ingame_menu.xml");
        nifty_LevelEditor = createNifty("interface/level_editor.xml");
        mainApplication.openStartMenu();
    }

    @Override
    public void update(float tpf){
        super.update(tpf);
        for(int i=0;i<remainingScreenChanges.size();i++){
            ScreenChange screenChange = remainingScreenChanges.get(i);
            if(screenChange.hasChanged()){
                ScreenController screenController = screenChange.getNifty().getCurrentScreen().getScreenController();
                if(screenController instanceof GameScreenController){
                    GameScreenController gameScreenController = (GameScreenController) screenController;
                    gameScreenController.setApplication(mainApplication);
                    gameScreenController.setNifty(screenChange.getNifty());
                    gameScreenController.onStartup();
                }
                remainingScreenChanges.remove(i);
                i--;
            }
        }
    }
    
    private Nifty createNifty(String filePath){
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(mainApplication.getAssetManager(), mainApplication.getInputManager(), mainApplication.getAudioRenderer(), mainApplication.getGuiViewPort());
        mainApplication.getGuiViewPort().addProcessor(niftyDisplay);
        Nifty nifty = niftyDisplay.getNifty();
        nifty.addXml(filePath);
        goToScreen(nifty, "start");
        runningNifties.add(nifty);
        return nifty;
    }
    
    private void goToScreen(Nifty nifty, String screenID){
        nifty.gotoScreen(screenID);
        remainingScreenChanges.add(new ScreenChange(nifty, screenID));
    }
    
    public void openStartMenu(){
        goToScreen(nifty_IngameMenu, "start");
    }
    
    public void openLevelSelection(){
        goToScreen(nifty_IngameMenu, "levelSelection");
    }
    
    public void openHowTo(){
        goToScreen(nifty_IngameMenu, "howTo");
    }
    
    public void openLevelEditor(){
        goToScreen(nifty_LevelEditor, "editor");
    }
    
    public void closeLevelEditor(){
        goToScreen(nifty_LevelEditor, "start");
    }
    
    public void openLevel(){
        goToScreen(nifty_IngameMenu, "level_SimpleProgram");
        //goToScreen(nifty_IngameMenu, "level_ScriptProgram");
    }
    
    public void showLevelCompletedScreen(){
        goToScreen(nifty_IngameMenu, "levelCompleted");
    }
    
    public <T extends ScreenController> T getScreenController(Class<T> screenControllerClass){
        for(int i=0;i<runningNifties.size();i++){
            ScreenController screenController = getNiftyScreenController(runningNifties.get(i));
            if(screenControllerClass.isAssignableFrom(screenController.getClass())){
                return (T) screenController;
            }
        }
        return null;
    }

    private ScreenController getNiftyScreenController(Nifty nifty){
        return nifty.getCurrentScreen().getScreenController();
    }

    public boolean isIngameMenuShowed(){
        ScreenController gameScreenController = getNiftyScreenController(nifty_IngameMenu);
        return (gameScreenController instanceof ScreenController_SimpleProgram);
    }
}
