/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.destroflyer.jme3.cubes.Vector3Int;
import com.destroflyer.roblocks.application.engine.Util;
import com.destroflyer.roblocks.game.*;
import com.destroflyer.roblocks.game.maps.StartMenuMap;
import com.destroflyer.roblocks.game.program_steps.*;
import com.destroflyer.roblocks.blocks.*;

/**
 *
 * @author Carl
 */
public class StartMenuAppState extends MapAppState{

    public StartMenuAppState(){
        super(new StartMenuMap());
    }
    private long lastCinematicProgramTimestamp = 0;
    private int currentCinematicProgramIndex = -1;

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        stateManager.detach(stateManager.getState(LevelSelectionAppState.class));
        stateManager.detach(stateManager.getState(HowToAppState.class));
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        ProgramExecution programExecution = null;
        if(Util.isTimeElapsed(lastCinematicProgramTimestamp, 16000)){
            currentCinematicProgramIndex++;
            if(currentCinematicProgramIndex > 2){
                currentCinematicProgramIndex = 0;
            }
            switch(currentCinematicProgramIndex){
                case 0:
                    map.clearRobotSpawns();
                    map.addRobotSpawn(new RobotSpawn(new Vector3Int(0, 1, 2), Direction.SOUTH));
                    programExecution = new ProgramExecution(new SimpleProgram(new ProgramStep[]{
                        new MoveStep(),
                        new TurnLeftStep(),
                        new MoveStep(),
                        new MoveStep(),
                        new MoveStep(),
                        new MoveStep(),
                        new MoveStep(),
                        new BuildStep(BlockAssets.BLOCK_STONE_TILE),
                        new TurnRightStep(),
                        new MoveStep(),
                        new MoveStep(),
                        new MoveStep(),
                        new MoveStep(),
                    }));
                    break;

                case 1:
                    map.clearRobotSpawns();
                    map.addRobotSpawn(new RobotSpawn(new Vector3Int(13, 1, 5), Direction.SOUTH));
                    programExecution = new ProgramExecution(new SimpleProgram(new ProgramStep[]{
                        new TurnRightStep(),
                        new MoveStep(),
                        new MoveStep(),
                        new TurnLeftStep(),
                        new PauseStep(0.5f),
                        new TurnRightStep(),
                        new MoveStep(),
                        new TurnRightStep(),
                        new PauseStep(0.5f),
                        new TurnLeftStep(),
                        new PauseStep(0.5f),
                        new TurnLeftStep(),
                        new TurnLeftStep(),
                        new MoveStep(),
                        new MoveStep(),
                        new MoveStep()
                    }));
                    break;

                case 2:
                    map.clearRobotSpawns();
                    map.addRobotSpawn(new RobotSpawn(new Vector3Int(0, 5, 0), Direction.SOUTH));
                    programExecution = new ProgramExecution(new SimpleProgram(new ProgramStep[]{
                        new TurnLeftStep(),
                        new MoveStep(),
                        new MoveStep(),
                        new JumpStep(),
                        new MoveStep(),
                        new JumpStep(),
                        new JumpStep(),
                        new JumpStep(),
                        new MoveStep(),
                        new JumpStep(),
                        new MoveStep(),
                        new JumpStep(),
                        new JumpStep(),
                        new JumpStep(),
                        new JumpStep(),
                        new MoveStep()
                    }));
                    break;
            }
            lastCinematicProgramTimestamp = System.currentTimeMillis();
        }
        if(programExecution != null){
            startProgram(programExecution);
        }
    }
}
