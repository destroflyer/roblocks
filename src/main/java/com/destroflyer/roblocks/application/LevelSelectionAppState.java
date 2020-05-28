/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application;

import com.destroflyer.roblocks.game.maps.LevelSelectionMap;
import com.destroflyer.roblocks.game.*;
import com.destroflyer.roblocks.game.program_steps.*;
import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;

/**
 *
 * @author Carl
 */
public class LevelSelectionAppState extends MapAppState{

    public LevelSelectionAppState(){
        super(new LevelSelectionMap());
    }

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        stateManager.detach(stateManager.getState(StartMenuAppState.class));
        stateManager.detach(stateManager.getState(LevelEditorAppState.class));
        stateManager.detach(stateManager.getState(LevelAppState.class));
        ProgramStep[] programSteps = new ProgramStep[]{
            new MoveStep(),
            new MoveStep(),
            new JumpStep(),
            new TurnRightStep(),
            new JumpStep(),
            new MoveStep(),
            new TurnRightStep(),
            new MoveStep(),
            new JumpStep(),
            new JumpStep(),
            new TurnRightStep(),
            new MoveStep(),
            new MoveStep(),
            new TurnRightStep(),
            null
        };
        SimpleProgram program = new SimpleProgram(programSteps);
        programSteps[programSteps.length - 1] = new SubprogramStep(program);
        startProgram(new ProgramExecution(program));
    }

    @Override
    public void update(float tpf){
        super.update(tpf);
        Robot robot = map.getRobots()[0];
        mainApplication.getCamera().lookAt(robot.getWorldTranslation(), Vector3f.UNIT_Y);
    }
}
