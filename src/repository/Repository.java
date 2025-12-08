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
    public List<ProgramState> getProgramStates() {
        return programStates;
    }

    @Override
    public void setProgramStates(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void loggingProgramStateExec(ProgramState programState) throws Exception{
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(loggingFile,true)));

        logFile.println("Program State ID: " + programState.getId());

        logFile.println("ExeStack:");
        logFile.println(programState.getExecutionStack().toString());

        logFile.println("SymTable:");
        logFile.println(programState.getSymbolTable().toString());

        logFile.println("Heap:");
        logFile.println(programState.getHeap().toString());

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
