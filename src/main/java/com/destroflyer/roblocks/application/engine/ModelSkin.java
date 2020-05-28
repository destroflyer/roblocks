/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine;

import java.io.File;
import java.util.LinkedList;
import com.jme3.material.Material;
import com.jme3.material.RenderState.FaceCullMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Attribute;
import com.destroflyer.roblocks.application.MainApplication;

/**
 *
 * @author Carl
 */
public class ModelSkin{

    public ModelSkin(String filePath){
        this.filePath = filePath;
        loadFile();
    }
    private static final String[] FILE_EXTENSIONS = new String[]{"j3o", "mesh.xml", "blend"};
    private String filePath;
    private Element rootElement;
    private Element modelElement;
    private Element positionElement;
    private Element materialElement;
    private String name;
    private float modelScale;
    private float materialAmbient;
    
    private void loadFile(){
        try{
            Document document = new SAXBuilder().build(new File(MainApplication.ASSETS_ROOT + filePath));
            rootElement = document.getRootElement();
            name = rootElement.getAttributeValue("name");
            modelElement = rootElement.getChild("model");
            positionElement = modelElement.getChild("position");
            materialElement = modelElement.getChild("material");
            modelScale = getAttributeValue(modelElement, "scale", 1);
            materialAmbient = getAttributeValue(materialElement, "ambient", 0.15f);
        }catch(Exception ex){
            System.out.println("Error while loading model skin '" + filePath + "'");
        }
    }
    
    private boolean getAttributeValue(Element element, String attributeName, boolean defaultValue){
        return (getAttributeValue(element, attributeName, (defaultValue?1:0)) == 1);
    }
    
    private float getAttributeValue(Element element, String attributeName, float defaultValue){
        if(element != null){
            Attribute attribute = element.getAttribute(attributeName);
            if(attribute != null){
                try{
                    return attribute.getFloatValue();
                }catch(Exception ex){
                }
            }
        }
        return defaultValue;
    }
    
    public Spatial loadSpatial(){
        Spatial spatial = loadModel();
        loadMaterial(spatial);
        return spatial;
    }
    
    private Spatial loadModel(){
        String modelPath = getModelFilePath();
        Spatial spatial = MainApplication.INSTANCE.getAssetManager().loadModel(modelPath);
        spatial.setLocalScale(modelScale);
        return spatial;
    }
    
    private String getModelFilePath(){
        for (String fileExtension : FILE_EXTENSIONS) {
            String modelFilePath = getModelFilePath(fileExtension);
            if (new File(MainApplication.ASSETS_ROOT + modelFilePath).exists()) {
                return modelFilePath;
            }
        }
        return null;
    }
    
    private String getModelFilePath(String fileExtension){
        return "models/" + name + "/" + name + "." + fileExtension;
    }
    
    private void loadMaterial(Spatial spatial){
        if(materialElement != null){
            for (Element currentMaterialElement : materialElement.getChildren()) {
                String materialDefintion = currentMaterialElement.getText();
                Material material = null;
                if (currentMaterialElement.getName().equals("color")) {
                    float[] colorComponents = Util.parseToFloatArray(materialDefintion.split(","));
                    ColorRGBA colorRGBA = new ColorRGBA(colorComponents[0], colorComponents[1], colorComponents[2], colorComponents[3]);
                    material = MaterialFactory.generateLightingMaterial(colorRGBA);
                } else if (currentMaterialElement.getName().equals("texture")) {
                    String textureFilePath = getResourcesFilePath() + currentMaterialElement.getText();
                    String normalMapFilePath = null;
                    Attribute normalMapAttribute = currentMaterialElement.getAttribute("normalMap");
                    if (normalMapAttribute != null) {
                        normalMapFilePath = getResourcesFilePath() + normalMapAttribute.getValue();
                    }
                    material = MaterialFactory.generateLightingMaterial(textureFilePath, normalMapFilePath);
                }
                if (material != null) {
                    try {
                        int childIndex = currentMaterialElement.getAttribute("index").getIntValue();
                        Geometry child = (Geometry) JMonkeyUtil.getChild(spatial, childIndex);
                        child.setMaterial(material);
                    } catch (Exception ex) {
                        System.out.println("Error while reading material for object '" + name + "'");
                    }
                }
            }
        }
        LinkedList<Geometry> geometryChildren = JMonkeyUtil.getAllGeometryChilds(spatial);
        for (Geometry geometryChild : geometryChildren) {
            Material material = geometryChild.getMaterial();
            MaterialFactory.generateAmbientColor(material, materialAmbient);
            material.getAdditionalRenderState().setFaceCullMode(FaceCullMode.Off);
        }
    }
    
    private String getResourcesFilePath(){
        return "Models/" + name + "/resources/";
    }
    
    public Vector3f loadPosition(){
        if(positionElement != null){
            Element locationElement = positionElement.getChild("location");
            if(locationElement != null){
                float[] location = Util.parseToFloatArray(locationElement.getText().split(","));
                return new Vector3f(location[0], location[1], location[2]);
            }
        }
        return new Vector3f(0, 0, 0);
    }
    
    public String getIconFilePath(){
        String iconFilePath = "Models/" + name + "/icon.jpg";
        if(!Util.existsResource("/" + iconFilePath)){
            iconFilePath = "Iinterface/images/icon_unknown.jpg";
        }
        return iconFilePath;
    }

    public String getName(){
        return name;
    }

    public float getModelScale(){
        return modelScale;
    }
    
    public float getMaterialAmbient(){
        return materialAmbient;
    }
}
