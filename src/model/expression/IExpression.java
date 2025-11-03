package model.expression;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.value.IValue;
import exceptions.DictionaryException;

public interface IExpression  {
    IValue evaluate(MyIDictionary<String, IValue> symbolTable) throws Exception;
}

