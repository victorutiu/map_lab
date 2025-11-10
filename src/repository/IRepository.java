package repository;

import model.state.ProgramState;

public interface IRepository {
    ProgramState getCurrentProgram();
    void loggingProgramStateExec() throws Exception;
}
