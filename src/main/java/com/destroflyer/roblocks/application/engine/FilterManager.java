/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine;

import com.jme3.math.ColorRGBA;
import com.jme3.post.*;
import com.jme3.post.filters.*;
import com.jme3.water.WaterFilter;
import com.destroflyer.roblocks.application.MainApplication;

/**
 *
 * @author Carl
 */
public class FilterManager{

    public FilterManager(MainApplication application){
        this.application = application;
        filterPostProcessor = new FilterPostProcessor(application.getAssetManager());
        application.getViewPort().addProcessor(filterPostProcessor);
    }
    private MainApplication application;
    private FilterPostProcessor filterPostProcessor;
    
    public WaterFilter createWaterFilter(){
        return new WaterFilter(application.getRootNode(), application.getLightDirection());
    }
    
    public void addRadialBlur(float strength){
        RadialBlurFilter radialBlurFilter = new RadialBlurFilter(0.2f, strength);
        addFilter(radialBlurFilter);
    }
    
    public void addColorOverlay(ColorRGBA color){
        ColorOverlayFilter colorOverlayFilter = new ColorOverlayFilter(new ColorRGBA(color));
        addFilter(colorOverlayFilter);
    }
    
    public void addFilter(final Filter filter){
        application.enqueueTask(new Runnable(){

            public void run(){
                filterPostProcessor.addFilter(filter);
            }
        });
    }
    
    public void removeFilter(final Filter filter){
        application.enqueueTask(new Runnable(){

            public void run(){
                filterPostProcessor.removeFilter(filter);
            }
        });
    }
}
