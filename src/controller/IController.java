package controller;

import model.state.ProgramState;
import exceptions.*;

public interface IController {
    void allStep() throws Exception;
    void setDisplayFlag(boolean value);
}

