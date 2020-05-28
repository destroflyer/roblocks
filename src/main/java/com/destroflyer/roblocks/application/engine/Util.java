package com.destroflyer.roblocks.application.engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Carl
 */
public class Util{

    public static BufferedImage getImage(String filePath){
        try{
            return ImageIO.read(new File(filePath));
        }catch(Exception ex){
            System.err.println("Error while reading image file '" + filePath + "'.");
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean existsResource(String resourcePath){
        return (getResourceURL(resourcePath) != null);
    }
    
    public static InputStream getResourceInputStrean(String resourcePath){
        return Util.class.getResourceAsStream(resourcePath);
    }
    
    public static URL getResourceURL(String resourcePath){
        return Util.class.getResource(resourcePath);
    }
    
    public static String getFormattedDate(long timestamp){
        return getFormattedDate(timestamp, false);
    }
    
    public static String getFormattedDate(long timestamp, boolean onlyDate){
        DateFormat dateFormat = new SimpleDateFormat("d.M.y" + (onlyDate?"":", k:m"));
        return dateFormat.format(new Date(timestamp * 1000));
    }
    
    public static boolean isTimeElapsed(long start, int duration){
        return ((System.currentTimeMillis() - start) >= duration);
    }
    
    public static float getTimeBasedDistance(long lastTimestamp, int intervall){
        return (((float) (System.currentTimeMillis() - lastTimestamp)) / intervall);
    }
    
    public static int getPercentage_Rounded(float value, float percentageValue){
        return (int) Math.round(getPercentage(value, percentageValue));
    }
    
    public static float getPercentage(float value, float percentageValue){
        return ((percentageValue / value) * 100);
    }
    
    public static int getPercentageValue_Rounded(float value, float percentage){
        return (int) Math.round(getPercentageValue(value, percentage));
    }
    
    public static float getPercentageValue(float value, float percentage){
        return ((value * percentage) / 100);
    }
    
    public static <T> Class<? extends T> getClassForName(String className, Class<T> convertionClass){
        try{
            return (Class<? extends T>) Class.forName(className);
        }catch(ClassNotFoundException ex){
            System.err.println("Error while creating class for name '" + className + "'.");
            ex.printStackTrace();
        }
        return null;
    }
    
    public static <T> T createObjectByClass(Class<T> objectClass){
        return createObjectByClassName(objectClass.getName(), objectClass);
    }
    
    public static <T> T createObjectByClassName(String className, Class<T> convertionClass){
        try{
            return (T) Class.forName(className).newInstance();
        }catch(Exception ex){
            System.err.println("Error while creating object of class '" + className + "'.");
            ex.printStackTrace();
        }
        return null;
    }
    
    public static int getUniqueObjectID(Object object){
        return object.hashCode();
    }
    
    public static boolean containsArrayElement(int[] array, int element){
        if(array != null){
            for(int i=0;i<array.length;i++){
                if(array[i] == element){
                    return true;
                }
            }
        }
        return false;
    }
    
    public static <T> boolean containsArrayElement(T[] array, T element){
        for(int i=0;i<array.length;i++){
            if(array[i] == element){
                return true;
            }
        }
        return false;
    }
    
    public static <T> boolean containsListElement(List<T> list, T element){
        for(int i=0;i<list.size();i++){
            if(list.get(i) == element){
                return true;
            }
        }
        return false;
    }
    
    public static <T> T[] cloneArray(T[] array, Class<? extends T> objectClass){
        T[] clonedArray = (T[]) Array.newInstance(objectClass, array.length);
        for(int i=0;i<clonedArray.length;i++){
            clonedArray[i] = array[i];
        }
        return clonedArray;
    }
    
    public static int[] cloneArray(int[] array){
        int[] clonedArray = new int[array.length];
        for(int i=0;i<clonedArray.length;i++){
            clonedArray[i] = array[i];
        }
        return clonedArray;
    }
    
    public static int[] parseToIntArray(String[] array){
        int[] intArray = new int[array.length];
        for(int i=0;i<intArray.length;i++){
            intArray[i] = Integer.parseInt(array[i]);
        }
        return intArray;
    }
    
    public static float[] parseToFloatArray(String[] array){
        float[] floatArray = new float[array.length];
        for(int i=0;i<floatArray.length;i++){
            floatArray[i] = Float.parseFloat(array[i]);
        }
        return floatArray;
    }
    
    public static int getArraySum(int[] array){
        int sum = 0;
        for(int i=0;i<array.length;i++){
            sum += array[i];
        }
        return sum;
    }
    
    public static int[] getIncrementingNumbers(int count){
        return getIncrementingNumbers(count, 0);
    }
    
    public static int[] getIncrementingNumbers(int count, int offset){
        int[] numbers = new int[count];
        for(int i=0;i<numbers.length;i++){
            numbers[i] = (offset + i);
        }
        return numbers;
    }
    
    public static <T> T[] toArray(List list, Class objectClass){
        T[] array = (T[]) Array.newInstance(objectClass, list.size());
        for(int i=0;i<array.length;i++){
            array[i] = (T) list.get(i);
        }
        return array;
    }
    
    public static <T, E> E[] castArray(T[] array, Class<E> newClass){
        E[] castedArray = (E[]) Array.newInstance(newClass, array.length);
        for(int i=0;i<castedArray.length;i++){
            castedArray[i] = (E) array[i];
        }
        return castedArray;
    }
    
    public static <T> T[] extractObjects(Object[] array, Class objectClass){
        LinkedList<T> extractedObjectsList = new LinkedList<T>();
        for(int i=0;i<array.length;i++){
            Object object = array[i];
            if(objectClass.isAssignableFrom(object.getClass())){
                extractedObjectsList.add((T) object);
            }
        }
        T[] extractedArray = (T[]) Array.newInstance(objectClass, extractedObjectsList.size());
        extractedObjectsList.toArray(extractedArray);
        return extractedArray;
    }
    
    public static <T> T getRandomArrayElement(T[] array){
        return array[(int) (Math.random() * array.length)];
    }
    
    public static <T, E> T getHashKeyByValue(Map<T, E> map, E value){
        for(Entry<T, E> entry : map.entrySet()){
            if(value.equals(entry.getValue())){
                return entry.getKey();
            }
        }
        return null;
    }
    
    public static int convertUnit(int number, int unit){
        return (int) Math.ceil(((float) number) / unit);
    }
    
    public static int parseInt(String text){
        try{
            return Integer.parseInt(text);
        }catch(Exception ex){
        }
        return -1;
    }
    
    public static float parseFloat(String text){
        try{
            return Float.parseFloat(text);
        }catch(Exception ex){
        }
        return -1;
    }
    
    public static float compensateFloatRoundingErrors(float number){
        final float MAX_FLOAT_ROUNDING_DIFFERENCE = 0.0001f;
        float remainder = (number % 1);
        if ((remainder < MAX_FLOAT_ROUNDING_DIFFERENCE) || (remainder > (1 - MAX_FLOAT_ROUNDING_DIFFERENCE))) {
            number = Math.round(number);
        }
        return number;
    }
    
    public static <T> boolean containsArrayIndex(T[][] array, int index1, int index2){
        if(array.length == 0){
            return false;
        }
        return ((index1 >= 0) && (index1 < array.length) && (index2 >= 0) && (index2 < array[0].length));
    }
    
    public static <T> ArrayList<T> getUniqueList(ArrayList<T> list){
        ArrayList<T> uniqueList = new ArrayList<T>();
        for(int i=0;i<list.size();i++){
            T element = list.get(i);
            if(!uniqueList.contains(element)){
                uniqueList.add(element);
            }
        }
        return uniqueList;
    }
    
    public static <T> T[] mergeArrays(T[]... arrays){
        Class arrayClass = null;
        LinkedList<T> mergedList = new LinkedList<T>();
        for(int i=0;i<arrays.length;i++){
            for(int r=0;r<arrays[i].length;r++){
                T element = arrays[i][r];
                mergedList.add(element);
                if(arrayClass != null){
                    while(!arrayClass.isAssignableFrom(element.getClass())){
                        arrayClass = arrayClass.getSuperclass();
                    }
                }
                else{
                    arrayClass = element.getClass();
                }
            }
        }
        T[] mergedArray = (T[]) Array.newInstance(arrayClass, mergedList.size());
        Object[] objectsArray = mergedList.toArray();
        for(int i=0;i<objectsArray.length;i++){
            mergedArray[i] = (T) objectsArray[i];
        }
        return mergedArray;
    }
    
    public static int getRandomNumberBetween(int minimum, int maximum){
        return (((int) (Math.random() * (maximum - minimum + 1))) + minimum);
    }
    
    public static float getRandomNumberBetween_Decimal(int minimum, int maximum){
        return (((float) (Math.random() * (maximum - minimum + 1))) + minimum);
    }
    
    public static int getMinimum(int... numbers){
        int minimum = Integer.MAX_VALUE;
        for(int i=0;i<numbers.length;i++){
            if(numbers[i] < minimum){
                minimum = numbers[i];
            }
        }
        return minimum;
    }

    public static <T> T getArrayElement(T[][] array, int index1, int index2){
        if((array.length > index1) && (array[index1].length > index2)){
            return array[index1][index2];
        }
        return null;
    }
}
