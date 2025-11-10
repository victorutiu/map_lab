package repository;

import model.state.ProgramState;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Repository implements IRepository{
    private List<ProgramState> programStates;
    private String loggingFile;

    public Repository(ProgramState initialState, String loggingFile) {
        programStates = new ArrayList<>();
        programStates.add(initialState);
        this.loggingFile = loggingFile;
    }

    @Override
    public ProgramState getCurrentProgram() {
        return programStates.get(0);
    }

    @Override
    public void loggingProgramStateExec() throws Exception{
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(loggingFile,true)));

        ProgramState programState = getCurrentProgram();

        logFile.println("ExeStack:");
        logFile.println(programState.getExecutionStack().toString());

        logFile.println("SymTable:");
        logFile.println(programState.getSymbolTable().toString());

        logFile.println("Out:");
        logFile.println(programState.getOutputList().toString());

        logFile.println();
        logFile.close();
    }

    @Override
    public String toString() {
        return "Repository:\n" + programStates.toString();
    }
}
