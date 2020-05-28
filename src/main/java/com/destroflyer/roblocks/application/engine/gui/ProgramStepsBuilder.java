/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.controls.dragndrop.builder.DraggableBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;
import com.destroflyer.roblocks.application.MainApplication;
import com.destroflyer.roblocks.application.engine.Util;

/**
 *
 * @author Carl
 */
public abstract class ProgramStepsBuilder{

    public ProgramStepsBuilder(float x, float y, int rowsCount){
        offsetX = x;
        offsetY = y;
        this.rowsCount = rowsCount;
        tileWidthPx = Util.getPercentageValue_Rounded(MainApplication.INSTANCE.getSettings().getWidth(), tileWidth);
        tileHeightPx = Util.getPercentageValue_Rounded(MainApplication.INSTANCE.getSettings().getHeight(), tileHeight);
    }
    public static final int COLUMNS_COUNT = 4;
    protected float offsetX;
    protected float offsetY;
    protected float x;
    protected float y;
    private int rowsCount;
    protected Nifty nifty;
    protected Screen screen;
    protected Element layer;
    protected final float tileWidth = 4.61f;
    protected final float tileHeight = 8.2f;
    protected int tileWidthPx;
    protected int tileHeightPx;
    
    public void build(Nifty nifty){
        setNifty(nifty);
        for(int i=0;i<(rowsCount * COLUMNS_COUNT);i++){
            calculateLocation(i);
            buildTile(i);
        }
    }

    private void setNifty(Nifty nifty){
        this.nifty = nifty;
        screen = nifty.getCurrentScreen();
        layer = screen.findElementByName("sidebar");
    }
    
    private void calculateLocation(int stepIndex){
        x = (offsetX + ((stepIndex % COLUMNS_COUNT) * tileWidth));
        y = (offsetY + ((stepIndex / COLUMNS_COUNT) * tileHeight));
    }
    
    protected abstract void buildTile(final int stepIndex);
    
    public void buildElement(ElementBuilder elementBuilder){
        elementBuilder.build(nifty, screen, layer);
    }
    
    public void buildNewDraggablePaletteStep(final int stepID){
        calculateLocation(stepID);
        buildElement(new DraggableBuilder(ProgramStepsBuilder_Program.getContainerID(ScreenController_SimpleProgram.ProgramType.PALETTE, stepID)){{
            x(x + "%");
            y(y + "%");
            width(tileWidthPx + "px");
            height(tileHeightPx + "px");
            childLayoutOverlay();
            image(new ImageBuilder(){{
                filename("interface/images/program_step.png");
            }});
            image(new ImageBuilder(){{
                filename("interface/images/program_steps/" + stepID + ".png");
            }});
        }});
    }
}
