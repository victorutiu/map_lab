package model.state;

import model.adt.MyIStack;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.statement.IStatement;
import model.value.IValue;
import java.io.BufferedReader;
import model.value.StringValue;
import model.adt.MyIHeap;
import exceptions.*;
public class ProgramState {

    private MyIStack<IStatement> executionStack;
    private MyIDictionary<String, IValue> symbolTable;
    private MyIList<IValue> outputList;
    private IStatement originalProgram;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap heap;

    private int id;
    private static int lastId = 1;

    private static synchronized int generateId() {
        return lastId++;
    }

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

        this.id = generateId();
    }

    public ProgramState(
            MyIStack<IStatement> stack,
            MyIDictionary<String, IValue> symTable,
            MyIList<IValue> out,
            MyIDictionary<StringValue, BufferedReader> fileTable,
            MyIHeap heap
    ) {
        this.executionStack = stack;
        this.symbolTable = symTable;
        this.outputList = out;
        this.fileTable = fileTable;
        this.heap = heap;

        this.originalProgram = null;
        this.id = generateId();
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

    public int getId() {
        return id;
    }

    public boolean isNotCompleted() {
        return !executionStack.isEmpty();
    }

    public ProgramState oneStep() throws Exception {
        if (executionStack.isEmpty()) {
            throw new StackException("program state stack is empty");
        }

        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString() {
        return "ProgramState ID:" + id + "\n" + "Execution Stack: " + executionStack + "\n" + "Symbol Table: " + symbolTable + "\n" + "Heap: " + heap.toString() + "\n" + "Output List: " + outputList + "\n";
    }
}