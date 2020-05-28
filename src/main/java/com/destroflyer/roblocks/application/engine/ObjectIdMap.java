/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine;

import java.util.HashMap;

/**
 *
 * @author Carl
 */
public class ObjectIdMap {

    public ObjectIdMap(Object[] objects){
        for(int i=0;i<objects.length;i++){
            objectsMap.put(objects[i], i);
        }
    }
    private HashMap<Object, Integer> objectsMap = new HashMap<Object, Integer>();
    
    public int getID(Object object){
        return objectsMap.get(object);
    }
}
