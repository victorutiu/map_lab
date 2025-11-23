package model.statement;

import exceptions.ExpressionException;
import exceptions.DictionaryException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expression.IExpression;
import model.state.ProgramState;
import model.value.IValue;
import model.value.RefValue;

public class WriteHeapStatement implements IStatement {
    private String varName;
    private IExpression expression;

    public WriteHeapStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyIDictionary<String, IValue> symTable = state.getSymbolTable();
        MyIHeap heap = state.getHeap();

        if (!symTable.isDefined(varName))
            throw new DictionaryException("Variable " + varName + " is not defined!");

        IValue varValue = symTable.lookup(varName);

        if (!(varValue instanceof RefValue refVal))
            throw new ExpressionException("Variable " + varName + " must contain a RefValue!");

        int address = refVal.getAddress();

        if (!heap.contains(address))
            throw new ExpressionException("Invalid heap address: " + address);

        IValue evaluated = expression.evaluate(symTable, heap);

        if (!evaluated.getType().equals(refVal.getLocationType()))
            throw new ExpressionException("Type mismatch in wH for variable " + varName);

        heap.put(address, evaluated);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + expression + ")";
    }
}
