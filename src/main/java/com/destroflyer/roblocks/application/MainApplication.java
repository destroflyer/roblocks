package com.destroflyer.roblocks.application;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.system.AppSettings;
import com.jme3.collision.CollisionResults;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;
import com.destroflyer.roblocks.game.Map;
import com.destroflyer.roblocks.blocks.BlockAssets;
import com.destroflyer.roblocks.application.engine.gui.NiftyAppState;
import com.destroflyer.roblocks.application.engine.*;
import com.jme3.shadow.DirectionalLightShadowRenderer;

/**
 * @author Carl
 */
public class MainApplication extends SimpleApplication {

    public static void main(String[] args){
        Logger.getLogger("").setLevel(Level.SEVERE);
        try{
            FileOutputStream logFileOutputStream = new FileOutputStream("./log.txt");
            System.setOut(new PrintStream(new MultipleOutputStream(System.out, logFileOutputStream)));
            System.setErr(new PrintStream(new MultipleOutputStream(System.err, logFileOutputStream)));
        }catch(Exception ex){
        }
        MainApplication application = new MainApplication();
        application.start();
    }

    public MainApplication(){
        loadSettings();
    }
    public static MainApplication INSTANCE;
    public static String ASSETS_ROOT = "./assets/";
    private NiftyAppState niftyAppState;
    private AudioManager audioManager;
    private FilterManager filterManager;
    private final Vector3f lightDirection = new Vector3f(-0.8f, -1, -0.8f).normalizeLocal();
    private DirectionalLight directionalLight;
    private boolean isRunningOnMobile;
    
    private void loadSettings(){
        settings = new AppSettings(true);
        if(Settings.getBoolean("fullscreen")){
            settings.setWidth(-1);
            settings.setHeight(-1);
            settings.setFullscreen(true);
        }
        else{
            settings.setWidth(Settings.getInt("resolution_width"));
            settings.setHeight(Settings.getInt("resolution_height"));
        }
        settings.setVSync(Settings.getBoolean("vsync"));
        settings.setFrameRate(Settings.getInt("frame_rate"));
        settings.setSamples(Settings.getInt("antialiasing"));
        settings.setTitle("Roblocks - GameClient [Version 0.91]");
        settings.setIcons(new BufferedImage[] {
            Util.getImage(ASSETS_ROOT + "interface/images/icon/16.png"),
            Util.getImage(ASSETS_ROOT + "interface/images/icon/32.png"),
            Util.getImage(ASSETS_ROOT + "interface/images/icon/64.png"),
            Util.getImage(ASSETS_ROOT + "interface/images/icon/128.png")
        });
        setShowSettings(false);
        setPauseOnLostFocus(false);
    }

    @Override
    public void simpleInitApp(){
        INSTANCE = this;
        assetManager.registerLocator(ASSETS_ROOT, FileLocator.class);
        BlockAssets.registerBlocks();
        isRunningOnMobile = (renderer.getCaps().size() <= 2);
        initLight();
        if(!isRunningOnMobile){
            initShadows();
            initFilters();
            setDisplayStatView(false);
            setDisplayFps(false);
        }
        initSky();
        initCamera();
        niftyAppState = new NiftyAppState();
        stateManager.attach(niftyAppState);
        audioManager = new AudioManager(this);
    }
    
    private void initLight(){
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White);
        rootNode.addLight(ambientLight);
        directionalLight = new DirectionalLight();
        directionalLight.setDirection(lightDirection);
        directionalLight.setColor(new ColorRGBA(1f, 1f, 1f, 1.0f));
        rootNode.addLight(directionalLight);
    }
    
    private void initShadows(){
        String shadowMode = Settings.get("shadows");
        if(shadowMode.equals("low")){
            BasicShadowRenderer basicShadowRenderer = new BasicShadowRenderer(assetManager, 2048);
            basicShadowRenderer.setDirection(lightDirection);
            viewPort.addProcessor(basicShadowRenderer);
        }
        else if(shadowMode.equals("high")){
            DirectionalLightShadowRenderer shadowRenderer = new DirectionalLightShadowRenderer(assetManager, 2048, 3);
            shadowRenderer.setLight(directionalLight);
            shadowRenderer.setShadowIntensity(0.2f);
            viewPort.addProcessor(shadowRenderer);
        }
    }
    
    private void initFilters(){
        filterManager = new FilterManager(this);
        if(Settings.getBoolean("filter_water")){
            WaterFilter waterFilter = filterManager.createWaterFilter();
            waterFilter.setUseFoam(false);
            waterFilter.setUseRipples(false);
            waterFilter.setReflectionDisplace(0);
            filterManager.addFilter(waterFilter);
        }
    }
    
    private void initSky(){
        rootNode.attachChild(SkyFactory.createSky(assetManager, "textures/sky.jpg", true));
    }
    
    private void initCamera(){
        flyCam.setMoveSpeed(100);
        flyCam.setEnabled(false);
    }

    @Override
    public void simpleUpdate(float tpf){
        super.simpleUpdate(tpf);
    }
    
    public void openStartMenu(){
        stateManager.attach(new StartMenuAppState());
        niftyAppState.openStartMenu();
    }
    
    public void openLevelSelection(){
        stateManager.attach(new LevelSelectionAppState());
        niftyAppState.openLevelSelection();
    }
    
    public void openHowTo(){
        stateManager.attach(new HowToAppState());
        niftyAppState.openHowTo();
    }
    
    public void openLevelEditor(Map map){
        stateManager.attach(new LevelEditorAppState(map));
    }
    
    public void openLevel(Map map){
        stateManager.attach(new LevelAppState(map));
    }
    
    public CollisionResults getRayCastingResults_Cursor(Node node){
        Vector3f cursorRayOrigin = getCursorLocation(0);
        Vector3f cursorRayDirection = getCursorLocation(1).subtractLocal(cursorRayOrigin).normalizeLocal();
        return getRayCastingResults(node, new Ray(cursorRayOrigin, cursorRayDirection));
    }
    
    private Vector3f getCursorLocation(float z){
        return cam.getWorldCoordinates(inputManager.getCursorPosition(), z);
    }
    
    public CollisionResults getRayCastingResults_Camera(Node node){
        Vector3f origin = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2f), (settings.getHeight() / 2f)), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2f), (settings.getHeight() / 2f)), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();
        return getRayCastingResults(node, new Ray(origin, direction));
    }
    
    private  CollisionResults getRayCastingResults(Node node, Ray ray){
        Vector3f origin = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2f), (settings.getHeight() / 2f)), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(new Vector2f((settings.getWidth() / 2f), (settings.getHeight() / 2f)), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();
        CollisionResults results = new CollisionResults();
        node.collideWith(ray, results);
        return results;
    }

    public AppSettings getSettings(){
        return settings;
    }

    public NiftyAppState getNiftyAppState(){
        return niftyAppState;
    }

    public AudioManager getAudioManager(){
        return audioManager;
    }

    public Vector3f getLightDirection(){
        return lightDirection;
    }

    public boolean isRunningOnMobile(){
        return isRunningOnMobile;
    }

    public void enqueueTask(final Runnable runnable){
        enqueue(new Callable<Object>() {

            public Object call(){
                runnable.run();
                return null;
            }
        });
    }
    
    public void close(){
        stop();
        System.exit(0);
    }
}
