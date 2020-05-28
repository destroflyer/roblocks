/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
import com.jme3.math.Vector4f;
import com.destroflyer.roblocks.application.MainApplication;

/**
 *
 * @author Carl
 */
public class MaterialFactory{

    public static Material generateLightingMaterial(ColorRGBA color){
        Material material = new Material(getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse",  color);
        material.setColor("Ambient",  color);
        material.setColor("Specular", ColorRGBA.White);
        material.setFloat("Shininess", 10);
        return material;
    }

    public static void generateAmbientColor(Material material, float ambient){
        if((material.getParam("Diffuse") != null) && (material.getParam("Ambient") != null)){
            ColorRGBA diffuseColor = (ColorRGBA) (material.getParam("Diffuse").getValue());
            Vector4f newAmbient = diffuseColor.toVector4f().multLocal(ambient, ambient, ambient, 1);
            material.setVector4("Ambient", newAmbient);
        }
    }

    public static Material generateLightingMaterial(String textureFilePath){
        return generateLightingMaterial(textureFilePath, null);
    }
    
    public static Material generateLightingMaterial(String textureFilePath, String normalMapFilePath){
        Material material = new Material(getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        Texture textureDiffuse = loadTexture(textureFilePath);
        textureDiffuse.setWrap(Texture.WrapMode.Repeat);
        material.setTexture("DiffuseMap", textureDiffuse);
        if(normalMapFilePath != null){
            Texture textureNormalMap = loadTexture(normalMapFilePath);
            material.setTexture("NormalMap", textureNormalMap);
        }
        material.setFloat("Shininess", 5);
        return material;
    }
    
    private static Texture loadTexture(String filePath){
        return getAssetManager().loadTexture(new TextureKey(filePath, false));
    }
    
    public static AssetManager getAssetManager(){
        return MainApplication.INSTANCE.getAssetManager();
    }
}
