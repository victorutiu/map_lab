package controller;

import model.state.ProgramState;
import exceptions.*;

public interface IController {
    ProgramState oneStep(ProgramState state) throws Exception;
    void allStep() throws Exception;
    void displayCurrentProgram();
    void setDisplayFlag(boolean value);
}

