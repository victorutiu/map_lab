package controller;

import model.state.ProgramState;
import exceptions.*;
import repository.IRepository;

public interface IController {
    void allStep() throws Exception;
    void setDisplayFlag(boolean value);
    IRepository getRepository();
}

