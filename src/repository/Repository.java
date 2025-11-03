package repository;

import model.state.ProgramState;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<ProgramState> programStates;

    public Repository(ProgramState initialState) {
        programStates = new ArrayList<>();
        programStates.add(initialState);
    }

    @Override
    public ProgramState getCurrentProgram() {
        return programStates.get(0);
    }

    @Override
    public String toString() {
        return "Repository:\n" + programStates.toString();
    }
}
