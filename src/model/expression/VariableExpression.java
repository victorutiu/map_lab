package model.expression;

import exceptions.ExpressionException;
import exceptions.VariableNotExistsException;
import model.adt.MyIDictionary;
import model.value.IValue;
import exceptions.DictionaryException;
import model.adt.MyIHeap;

public class VariableExpression implements IExpression {
    private String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable, MyIHeap heap) throws Exception {
        if (!symbolTable.isDefined(variableName)) {
            throw new VariableNotExistsException("Undefined variable: " + variableName);
        }
        return symbolTable.lookup(variableName);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(variableName);
    }

    @Override
    public String toString() {
        return variableName;
    }
}
