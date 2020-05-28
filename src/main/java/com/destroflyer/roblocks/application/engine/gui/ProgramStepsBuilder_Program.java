/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.application.engine.gui;

import de.lessvoid.nifty.controls.dragndrop.builder.DroppableBuilder;
import com.destroflyer.roblocks.application.engine.Util;

/**
 *
 * @author Carl
 */
public class ProgramStepsBuilder_Program extends ProgramStepsBuilder{

    public ProgramStepsBuilder_Program(ScreenController_SimpleProgram.ProgramType programType, float x, float y, int rowsCount){
        super(x, y, rowsCount);
        this.programType = programType;
    }
    private static final String CONTAINER_ID_PREFIX = "programStep_";
    private static final String CONTAINER_ID_SEPERATOR = "_";
    private ScreenController_SimpleProgram.ProgramType programType;
    
    @Override
    protected void buildTile(final int stepIndex){
        buildElement(new DroppableBuilder(getContainerID(programType, stepIndex)){{
            x(x + "%");
            y(y + "%");
            width(tileWidth + "%");
            height(tileHeight + "%");
            backgroundImage("interface/images/program_step.png");
        }});
    }
    
    public static String getContainerID(ScreenController_SimpleProgram.ProgramType programType, int stepIndex){
        return (CONTAINER_ID_PREFIX + programType.ordinal() + CONTAINER_ID_SEPERATOR + stepIndex);
    }
    
    public static ProgramStepContainer parseContainerID(String id){
        String content = id.substring(CONTAINER_ID_PREFIX.length());
        int separatorIndex = content.indexOf("_");
        int programOrdinal = Util.parseInt(content.substring(0, separatorIndex));
        int stepIndex = Util.parseInt(content.substring(separatorIndex + 1));
        return new ProgramStepContainer(ScreenController_SimpleProgram.ProgramType.values()[programOrdinal], stepIndex);
    }
}
