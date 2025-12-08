package repository;
import java.util.List;
import model.state.ProgramState;

public interface IRepository {

    List <ProgramState> getProgramStates();
    void setProgramStates(List <ProgramState> programStates);
    void loggingProgramStateExec(ProgramState programState) throws Exception;
}
