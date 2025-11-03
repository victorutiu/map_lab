package model.expression;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.value.IValue;
import exceptions.DictionaryException;

public class ValueExpression implements IExpression {
    private IValue constantValue;

    public ValueExpression(IValue constantValue) {
        this.constantValue = constantValue;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable) throws Exception {
        return constantValue;
    }

    @Override
    public String toString() {
        return constantValue.toString();
    }
}
