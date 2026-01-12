package gui;

import controller.IController;
import model.state.ProgramState;

import java.util.List;

public class InterpreterService {

    private final IController controller;

    public InterpreterService(IController controller) {
        this.controller = controller;
    }

    public List<ProgramState> getProgramStates() {
        return controller.getRepository().getProgramStates();
    }

    public IController getController() {
        return controller;
    }
}
