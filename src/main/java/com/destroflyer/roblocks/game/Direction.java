/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game;

/**
 *
 * @author carl
 */
public class Direction {

    public static final int DIRECTIONS_COUNT = 5;
    public static final int NONE = 0;
    public static final int NORTH = 1;
    public static final int EAST = 2;
    public static final int SOUTH = 3;
    public static final int WEST = 4;
    private static int[] MOVE_DIRECTIONS = new int[]{NORTH, EAST, SOUTH, WEST};
    
    public static int getRandomDirection(){
        return MOVE_DIRECTIONS[(int) (Math.random() * MOVE_DIRECTIONS.length)];
    }

    public static int invertDirection(int direction){
	switch(direction){
            case NORTH:
		return SOUTH;
            
            case EAST:
                return WEST;
            
            case SOUTH:
                return NORTH;
            
            case WEST:
                return EAST;
        }
        return NONE;
    }

    public static int turn(int direction, boolean rightOrLeft){
	switch(direction){
            case NORTH:
		return (rightOrLeft?EAST:WEST);
            
            case EAST:
		return (rightOrLeft?SOUTH:NORTH);
            
            case SOUTH:
		return (rightOrLeft?WEST:EAST);
            
            case WEST:
		return (rightOrLeft?NORTH:SOUTH);
        }
        return NONE;
    }
}
