/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine;

import java.util.HashMap;
import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import com.destroflyer.roblocks.application.Settings;

/**
 *
 * @author Carl
 */
public class AudioManager{

    public AudioManager(SimpleApplication application){
        this.application = application;
        application.getRootNode().attachChild(audioNode);
    }
    private SimpleApplication application;
    private Node audioNode = new Node("audio_manager_node");
    private AudioNode backgroundMusicNode;
    private String currentBackgroundMusicFilePath;
    private HashMap<String, AudioNode> soundNodes = new HashMap<String, AudioNode>();
    
    public void playBackgroundMusic(String filePath){
        if(!filePath.equals(currentBackgroundMusicFilePath)){
            stopBackgroundMusic();
            backgroundMusicNode = new AudioNode(application.getAssetManager(), filePath);
            backgroundMusicNode.setPositional(false);
            backgroundMusicNode.setLooping(true);
            backgroundMusicNode.setVolume(Settings.getFloat("audio_volume_music"));
            audioNode.attachChild(backgroundMusicNode);
            backgroundMusicNode.play();
            currentBackgroundMusicFilePath = filePath;
        }
    }
    
    public void stopBackgroundMusic(){
        if(backgroundMusicNode != null){
            backgroundMusicNode.stop();
            audioNode.detachChild(backgroundMusicNode);
            currentBackgroundMusicFilePath = null;
        }
    }
    
    public void playSound(String filePath){
        AudioNode soundNode = getSoundNode(filePath);
        soundNode.playInstance();
    }
    
    private AudioNode getSoundNode(String filePath){
        AudioNode soundNode = soundNodes.get(filePath);
        if(soundNode == null){
            soundNode = new AudioNode(application.getAssetManager(), filePath);
            soundNode.setPositional(false);
            soundNode.setLooping(false);
            soundNode.setVolume(Settings.getFloat("audio_volume_sounds"));
            audioNode.attachChild(soundNode);
            soundNodes.put(filePath, soundNode);
        }
        return soundNode;
    }
}
