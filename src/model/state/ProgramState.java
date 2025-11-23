package model.state;

import model.adt.MyIStack;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.statement.IStatement;
import model.value.IValue;
import java.io.BufferedReader;
import model.value.StringValue;
import model.adt.MyIHeap;

public class ProgramState {

    private MyIStack<IStatement> executionStack;
    private MyIDictionary<String, IValue> symbolTable;
    private MyIList<IValue> outputList;
    private IStatement originalProgram;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap heap;
    public ProgramState(MyIStack<IStatement> executionStack,
                        MyIDictionary<String, IValue> symbolTable,
                        MyIList<IValue> outputList,
                        MyIDictionary<StringValue, BufferedReader> fileTable,
                        MyIHeap heap,
                        IStatement program) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = program.deepCopy();
        this.executionStack.push(this.originalProgram);
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

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap getHeap() {
        return heap;
    }

    public void setHeap(MyIHeap heap) {
        this.heap = heap;
    }

    @Override
    public String toString() {
        return "ProgramState:\n" + "Execution Stack: " + executionStack + "\n" + "Symbol Table: " + symbolTable + "\n" + "Heap: " + heap.toString() + "\n" + "Output List: " + outputList + "\n";
    }
}