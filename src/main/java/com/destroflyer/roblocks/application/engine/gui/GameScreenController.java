/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

import com.jme3.niftygui.RenderImageJme;
import com.jme3.texture.Texture2D;
import java.util.List;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.HoverEffectBuilder;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.Droppable;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.*;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import com.destroflyer.roblocks.application.MainApplication;

/**
 *
 * @author Carl
 */
public class GameScreenController implements ScreenController{

    public GameScreenController(){
        
    }
    protected MainApplication application;
    protected Nifty nifty;
    private boolean isInitialized = false;
    
    public void onStartup(){
        if(!isInitialized){
            initialize();
            isInitialized = true;
        }
    }
    
    protected void initialize(){
        
    }

    public MainApplication getApplication(){
        return application;
    }

    public void setApplication(MainApplication application){
        this.application = application;
    }

    public Nifty getNifty(){
        return nifty;
    }

    public void setNifty(Nifty nifty){
        this.nifty = nifty;
    }
    
    public String getCurrentScreenID(){
        return nifty.getCurrentScreen().getScreenId();
    }
    
    protected PanelRenderer getPanelRenderer(String id){
        return getElementByID(id).getRenderer(PanelRenderer.class);
    }
    
    protected TextRenderer getTextRenderer(String id){
        return getElementByID(id).getRenderer(TextRenderer.class);
    }
    
    protected ImageRenderer getImageRenderer(String id){
        return getElementByID(id).getRenderer(ImageRenderer.class);
    }
    
    protected Button getButton(String id){
        return nifty.getCurrentScreen().findNiftyControl(id, Button.class);
    }
    
    protected TextField getTextField(String id){
        return nifty.getCurrentScreen().findNiftyControl(id, TextField.class);
    }
    
    protected Slider getSlider(String id){
        return nifty.getCurrentScreen().findNiftyControl(id, Slider.class);
    }
    
    protected Droppable getDroppable(String id){
        return nifty.getCurrentScreen().findNiftyControl(id, Droppable.class);
    }
    
    protected Element getElementByID(String id){
        return nifty.getCurrentScreen().findElementByName(id);
    }
    
    public void goToScreen(String screenID){
        nifty.gotoScreen(screenID);
    }
    
    protected void removeAllChildElements(Element element){
        List<Element> children = element.getChildren();
        for (Element child : children) {
            child.markForRemoval();
        }
    }
    
    protected NiftyImage createImage(String filePath){
        return nifty.createImage(filePath, false);
    }
    
    protected NiftyImage createImage(Texture2D texture2D){
        return new NiftyImage(nifty.getRenderEngine(), new RenderImageJme(texture2D));
    }
    
    protected void setHoverEffect(ElementBuilder elementBuilder, String onStartHover, String onEndHover){
        HoverEffectBuilder startHoverEffect = new HoverEffectBuilder("nop");
        startHoverEffect.effectParameter("onStartEffect", onStartHover);
        elementBuilder.onStartHoverEffect(startHoverEffect);
        HoverEffectBuilder endHoverEffect = new HoverEffectBuilder("nop");
        endHoverEffect.effectParameter("onStartEffect", onEndHover);
        elementBuilder.onEndHoverEffect(endHoverEffect);
    }
    
    //This method can be called to avoid the user interface to receive the event
    public void doNothing(){
        
    }
    
    //ScreenControllerInterface

    public void bind(Nifty nifty, Screen screen){
        
    }

    public void onStartScreen(){
        
    }

    public void onEndScreen(){
        
    }
}
