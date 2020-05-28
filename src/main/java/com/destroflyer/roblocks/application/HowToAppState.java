/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.destroflyer.roblocks.application.engine.ObjectIdMap;
import com.destroflyer.roblocks.application.engine.gui.ScreenController_HowTo;
import com.destroflyer.roblocks.blocks.*;
import com.destroflyer.roblocks.game.*;
import com.destroflyer.roblocks.game.maps.HowToMap;
import com.destroflyer.roblocks.game.program_steps.*;

/**
 *
 * @author Carl
 */
public class HowToAppState extends MapAppState{

    public HowToAppState(){
        super(new HowToMap());
    }
    private SimpleProgram program;
    private ProgramStep lastProgramStep;
    private ObjectIdMap programStepsIdMap = new ObjectIdMap(new Class[]{
        MoveStep.class,
        TurnLeftStep.class,
        TurnRightStep.class,
        JumpStep.class,
        BuildStep.class,
        DigStep.class
    });

    @Override
    public void initialize(AppStateManager stateManager, Application application){
        super.initialize(stateManager, application);
        stateManager.detach(stateManager.getState(StartMenuAppState.class));
        ProgramStep[] programSteps = new ProgramStep[]{
            new MoveStep(),
            new MoveStep(),
            new MoveStep(),
            new MoveStep(),
            new JumpStep(),
            new TurnRightStep(),
            new MoveStep(),
            new MoveStep(),
            new TurnRightStep(),
            new JumpStep(),
            new MoveStep(),
            new BuildStep(BlockAssets.BLOCK_WOOD),
            new JumpStep(),
            new DigStep(),
            new MoveStep(),
            new MoveStep(),
            new TurnRightStep(),
            new MoveStep(),
            new MoveStep(),
            new TurnRightStep(),
            null
        };
        program = new SimpleProgram(programSteps);
        programSteps[programSteps.length - 1] = new SubprogramStep(program);
        startProgram(new ProgramExecution(program));
    }

    @Override
    public void update(float lastTimePerFrame){
        super.update(lastTimePerFrame);
        if(program.isStarted()){
            ScreenController_HowTo screenController_HowTo = mainApplication.getNiftyAppState().getScreenController(ScreenController_HowTo.class);
            ProgramStep currentProgramStep = program.getCurrentStep();
            if((lastProgramStep == null) || (currentProgramStep != lastProgramStep)){
                int programStepId = programStepsIdMap.getID(currentProgramStep.getClass());
                screenController_HowTo.setCurrentProgramStep(programStepId);
                lastProgramStep = currentProgramStep;
            }
        }
    }
}
