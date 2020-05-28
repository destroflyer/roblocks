/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

import java.util.LinkedList;
import com.destroflyer.roblocks.blocks.*;
import com.destroflyer.roblocks.game.*;
import com.destroflyer.roblocks.game.program_steps.*;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.controls.*;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.controls.dragndrop.DroppableControl;

/**
 *
 * @author Carl
 */
public class ScreenController_SimpleProgram extends ScreenController_Level{

    public ScreenController_SimpleProgram(){
        
    }
    public enum ProgramType{
        PALETTE,
        MAIN,
        FUNCTION_1,
        FUNCTION_2
    }
    private SimpleProgram[] programs = new SimpleProgram[]{
        new SimpleProgram(new ProgramStep[12]),
        new SimpleProgram(new ProgramStep[8]),
        new SimpleProgram(new ProgramStep[8])
    };
    private ProgramStep[] PROGRAM_STEPS = new ProgramStep[]{
        new MoveStep(),
        new TurnLeftStep(),
        new TurnRightStep(),
        new JumpStep(),
        new BuildStep(BlockAssets.BLOCK_BOX),
        new DigStep(),
        new SubprogramStep(programs[1]),
        new SubprogramStep(programs[2])
    };
    private ProgramStepsBuilder paletteStepsBuilder;
    private LinkedList<Element> activeStepDraggables = new LinkedList<Element>();
    
    @Override
    protected void initialize(){
        super.initialize();
        paletteStepsBuilder = new ProgramStepsBuilder(0, 0, 2){

            @Override
            protected void buildTile(final int stepIndex){
                buildElement(new PanelBuilder(){{
                    x(x + "%");
                    y(y + "%");
                    width(tileWidth + "%");
                    height(tileHeight + "%");
                    childLayoutOverlay();
                    image(new ImageBuilder(){{
                        filename("interface/images/program_step.png");
                    }});
                    image(new ImageBuilder(){{
                        filename("interface/images/program_steps/" + stepIndex + ".png");
                    }});
                }});
            }
        };
        paletteStepsBuilder.build(nifty);
        new ProgramStepsBuilder_Program(ProgramType.MAIN, 0, 25.5f, 3).build(nifty);
        new ProgramStepsBuilder_Program(ProgramType.FUNCTION_1, 0, 58.7f, 2).build(nifty);
        new ProgramStepsBuilder_Program(ProgramType.FUNCTION_2, 0, 83.65f, 2).build(nifty);
    }

    @Override
    public void onStartup(){
        super.onStartup();
        for (SimpleProgram program : programs) {
            for (int i = 0; i < program.getSteps().length; i++) {
                program.getSteps()[i] = null;
            }
        }
        for (Element stepDraggable : activeStepDraggables) {
            stepDraggable.markForRemoval();
        }
        activeStepDraggables.clear();
        for(int i=0;i<8;i++){
            paletteStepsBuilder.buildNewDraggablePaletteStep(i);
        }
    }
    
    @NiftyEventSubscriber(pattern=".*")
    public void onDragCancelled(String id, DraggableDragCanceledEvent event){
        if(event.getSource() != null){
            ProgramStepContainer stepContainer = ProgramStepsBuilder_Program.parseContainerID(event.getSource().getId());
            setProgramStep(stepContainer, null);
            event.getDraggable().getElement().markForRemoval();
            activeStepDraggables.remove(event.getDraggable().getElement());
        }
    }
    
    @NiftyEventSubscriber(pattern=".*")
    public void onDrop(String id, DroppableDroppedEvent event){
        if(event.getSource() != null){
            ProgramStepContainer oldContainer = ProgramStepsBuilder_Program.parseContainerID(event.getSource().getId());
            setProgramStep(oldContainer, null);
        }
        else{
            activeStepDraggables.add(event.getDraggable().getElement());
        }
        ProgramStepContainer paletteContainer = ProgramStepsBuilder_Program.parseContainerID(event.getDraggable().getId());
        ProgramStepContainer stepContainer = ProgramStepsBuilder_Program.parseContainerID(event.getTarget().getId());
        ProgramStep programStep = PROGRAM_STEPS[paletteContainer.getStepIndex()];
        setProgramStep(stepContainer, programStep);
        //Remove previous draggables
        Element targetedElement = ((DroppableControl) event.getTarget()).getElement();
        for(int i=0;i<activeStepDraggables.size();i++){
            Element stepDraggable = activeStepDraggables.get(i);
            Element parent = stepDraggable.getParent().getParent();
            if(parent == targetedElement){
                stepDraggable.markForRemoval();
                activeStepDraggables.remove(stepDraggable);
                i--;
            }
        }
        if(event.getSource() == null){
            paletteStepsBuilder.buildNewDraggablePaletteStep(paletteContainer.getStepIndex());
        }
        stopProgramIfRunning();
    }
    
    private void setProgramStep(ProgramStepContainer stepContainer, ProgramStep programStep){
        programs[stepContainer.getProgram().ordinal() - 1].getSteps()[stepContainer.getStepIndex()] = programStep;
    }

    @Override
    protected Program getProgramToExecute(){
        return programs[0];
    }
}
