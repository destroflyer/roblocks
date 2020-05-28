/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.destroflyer.roblocks.game.program_steps;

import com.destroflyer.roblocks.game.*;

/**
 *
 * @author Carl
 */
public class SubprogramStep extends ProgramStep{

    public SubprogramStep(SimpleProgram program){
        this.program = program;
    }
    private SimpleProgram program;

    public SimpleProgram getProgram(){
        return program;
    }

    @Override
    public ProgramStepPart[] generateParts(){
        throw new UnsupportedOperationException();
    }
}
