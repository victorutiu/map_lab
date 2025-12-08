package model.statement;

import model.adt.MyIStack;
import model.adt.MyIDictionary;
import model.adt.MyIList;
import model.adt.MyIHeap;
import model.state.ProgramState;
import model.value.IValue;
import model.state.ProgramState;

public class ForkStatement implements IStatement{
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws Exception {

        MyIDictionary<String, IValue> newSymbolTable = programState.getSymbolTable().cloneDictionary();
        MyIStack<IStatement> newExecutionStack = programState.getExecutionStack().createNewEmptyStack();
        newExecutionStack.push(statement);

        MyIHeap heap = programState.getHeap();
        MyIDictionary fileTable = programState.getFileTable();
        MyIList<IValue> output = programState.getOutputList();

        return new ProgramState(newExecutionStack, newSymbolTable, output, fileTable, heap);
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }
}
