package controller;


import exceptions.*;
import model.state.ProgramState;
import model.statement.IStatement;
import model.adt.MyIStack;
import repository.IRepository;

public class Controller implements IController {

    private IRepository repository;
    private boolean displayFlag = true;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void setDisplayFlag(boolean value) {
        this.displayFlag = value;
    }

    public ProgramState oneStep(ProgramState state) throws Exception {
        MyIStack<IStatement> stack = state.getExecutionStack();

        if (stack.isEmpty()) {
            throw new StackIsEmptyException("Program state stack is empty");
        }

        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allStep() throws Exception {
        ProgramState program = repository.getCurrentProgram();
        repository.loggingProgramStateExec();

        while(!program.getExecutionStack().isEmpty()) {
            oneStep(program);
            //displayCurrentProgram();
            repository.loggingProgramStateExec();

            if (displayFlag) {
                System.out.println(program.toString());
            }
        }

    }

    public void displayCurrentProgram() {
        ProgramState program = repository.getCurrentProgram();
        System.out.println(program);
    }
}
