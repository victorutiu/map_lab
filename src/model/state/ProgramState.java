package model.state;

import model.adt.MyIStack;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.statement.IStatement;
import model.value.IValue;

public class ProgramState {

    private final MyIStack<IStatement> executionStack;
    private final MyIDictionary<String, IValue> symbolTable;
    private final MyIList<IValue> outputList;
    private final IStatement originalProgram;

    public ProgramState(MyIStack<IStatement> executionStack,
                        MyIDictionary<String, IValue> symbolTable,
                        MyIList<IValue> outputList,
                        IStatement program) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;
        this.originalProgram = program;
        this.executionStack.push(program);
    }

    public MyIStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public MyIDictionary<String, IValue> getSymbolTable() {
        return symbolTable;
    }

    public MyIList<IValue> getOutputList() {
        return outputList;
    }

    public IStatement getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public String toString() {
        return "ProgramState:\n" + "Execution Stack: " + executionStack + "\n" + "Symbol Table: " + symbolTable + "\n" + "Output List: " + outputList + "\n";
    }
}