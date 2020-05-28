/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game;

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import com.jme3.math.Vector3f;
import com.destroflyer.jme3.cubes.BlockTerrainControl;
import com.destroflyer.jme3.cubes.Vector3Int;
import com.destroflyer.jme3.cubes.network.*;
import com.destroflyer.roblocks.application.*;
import com.destroflyer.roblocks.application.engine.*;
import com.destroflyer.roblocks.blocks.*;
import com.destroflyer.roblocks.game.maps.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Carl
 */
public class MapFileHandler{

    public static String MAPS_DIRECTORY = MainApplication.ASSETS_ROOT + "maps/";
    public static String MAP_FILE_SUFFIX = ".map";
    private static final int XML_BYTE_OFFSET = 0x20;
    private static final String VECTOR_COORDINATES_SEPARATOR = ",";
    static{
        FileManager.createDirectoryIfNotExists(MAPS_DIRECTORY);
    }
    
    public static File[] getMapFiles(String subDirectory){
        File[] files = new File(MapFileHandler.MAPS_DIRECTORY + subDirectory).listFiles(new FileFilter(){

            public boolean accept(File file){
                return file.getPath().endsWith(MAP_FILE_SUFFIX);
            }
        });
        if(files == null){
            files = new File[0];
        }
        return files;
    }
    
    public static void saveFile(File file, Map map){
        try{
            Element root = new Element("map");
            root.setAttribute("author", System.getProperty("user.name"));
            root.setAttribute("date", "" + System.currentTimeMillis());
            Document document = new Document(root);
            Element elementCameraState = new Element("cameraState");
            elementCameraState.addContent(getVector3fElement("location", map.getCameraState().getLocation()));
            elementCameraState.addContent(getVector3fElement("direction", map.getCameraState().getDirection()));
            root.addContent(elementCameraState);
            Element elementRobotSpawns = new Element("robotSpawns");
            for(RobotSpawn robotSpawn : map.getRobotSpawns()){
                Element elementSpawn = new Element("spawn");
                elementSpawn.addContent(getVector3IntElement("location", robotSpawn.getLocation()));
                elementSpawn.addContent(new Element("direction").setText("" + robotSpawn.getDirection()));
                elementRobotSpawns.addContent(elementSpawn);
            }
            root.addContent(elementRobotSpawns);
            if(map.getMusicPath() != null){
                Element elementMusic = new Element("music");
                elementMusic.setText(map.getMusicPath());
                root.addContent(elementMusic);
            }
            Element elementTerrain = new Element("terrain");
            byte[] serializedTerrainData = CubesSerializer.writeToBytes(map.getBlockTerrain());
            for(int i=0;i<serializedTerrainData.length;i++){
                serializedTerrainData[i] += XML_BYTE_OFFSET;
            }
            elementTerrain.setText(new String(serializedTerrainData, "UTF8"));
            root.addContent(elementTerrain);
            FileManager.putFileContent(file.getPath(), new XMLOutputter().outputString(document));
        }catch(Exception ex){
            System.out.println("Fehler beim Speichern der Map: " + ex.toString());
        }
    }

    public static Map loadFile(File file){
        try{
            String mapName = file.getName().substring(0, (file.getName().length() - MAP_FILE_SUFFIX.length()));
            return loadDocument(mapName, new SAXBuilder().build(file));
        }catch(Exception ex){
            System.out.println("Fehler beim Laden der Map: " + ex.toString());
        }
        return null;
    }

    public static Map loadInputStream(String mapName, InputStream inputStream){
        try{
            return loadDocument(mapName, new SAXBuilder().build(inputStream));
        }catch(Exception ex){
            System.out.println("Fehler beim Laden der Map: " + ex.toString());
        }
        return null;
    }

    private static Map loadDocument(String mapName, Document document){
        try{
            Element root = document.getRootElement();
            MapInformation mapInformation = new MapInformation(mapName, root.getAttributeValue("author"), root.getAttribute("date").getLongValue());
            Element elementCameraState = root.getChild("cameraState");
            Element elementRobotSpawns = root.getChild("robotSpawns");
            Element elementMusic = root.getChild("music");
            byte[] serializedTerrainData = root.getChild("terrain").getText().getBytes("UTF8");
            for(int i=0;i<serializedTerrainData.length;i++){
                serializedTerrainData[i] -= XML_BYTE_OFFSET;
            }
            final BlockTerrainControl mapBlockTerrain = new BlockTerrainControl(BlockAssets.getSettings(MainApplication.INSTANCE), new Vector3Int());
            CubesSerializer.readFromBytes(mapBlockTerrain, serializedTerrainData);
            Map map = new GoldTargetMap(){

                @Override
                public void prepareBlockTerrainForProgram(){
                    super.prepareBlockTerrainForProgram();
                    blockTerrain.setBlocksFromTerrain(mapBlockTerrain);
                }
            };
            map.setMapInformation(mapInformation);
            map.setCameraState(new CameraState(getVector3f(elementCameraState.getChild("location")), getVector3f(elementCameraState.getChild("direction"))));
            for(Object child : elementRobotSpawns.getChildren()){
                Element elementSpawn = (Element) child;
                Vector3Int spawnLocation = getVector3Int(elementSpawn.getChild("location"));
                int spawnDirection = Integer.parseInt(elementSpawn.getChild("direction").getText());
                map.addRobotSpawn(new RobotSpawn(spawnLocation, spawnDirection));
            }
            if(elementMusic != null){
                map.setMusicPath(elementMusic.getText());
            }
            return map;
        }catch(Exception ex){
            System.out.println("Fehler beim Laden der Map: " + ex.toString());
        }
        return null;
    }
    
    private static Element getVector3IntElement(String tagName, Vector3Int vector3Int){
        Element elementVector = new Element(tagName);
        elementVector.setText(vector3Int.getX() + VECTOR_COORDINATES_SEPARATOR + vector3Int.getY() + VECTOR_COORDINATES_SEPARATOR + vector3Int.getZ());
        return elementVector;
    }
    
    private static Vector3Int getVector3Int(Element elementVector){
        int[] coordinates = Util.parseToIntArray(elementVector.getText().split(VECTOR_COORDINATES_SEPARATOR));
        return new Vector3Int(coordinates[0], coordinates[1], coordinates[2]);
    }
    
    private static Element getVector3fElement(String tagName, Vector3f vector3f){
        Element elementVector = new Element(tagName);
        elementVector.setText(vector3f.getX() + VECTOR_COORDINATES_SEPARATOR + vector3f.getY() + VECTOR_COORDINATES_SEPARATOR + vector3f.getZ());
        return elementVector;
    }
    
    private static Vector3f getVector3f(Element elementVector){
        float[] coordinates = Util.parseToFloatArray(elementVector.getText().split(VECTOR_COORDINATES_SEPARATOR));
        return new Vector3f(coordinates[0], coordinates[1], coordinates[2]);
    }
}
