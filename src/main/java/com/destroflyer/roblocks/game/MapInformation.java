/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game;

/**
 *
 * @author Carl
 */
public class MapInformation{

    public MapInformation(String name, String author, long date){
        this.name = name;
        this.author = author;
        this.date = date;
    }
    private String name;
    private String author;
    private long date;

    public String getName(){
        return name;
    }

    public String getAuthor(){
        return author;
    }

    public long getDate(){
        return date;
    }
}
