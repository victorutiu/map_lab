package model.expression;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.value.IValue;
import model.adt.MyIHeap;

public interface IExpression  {
    IValue evaluate(MyIDictionary<String, IValue> symbolTable, MyIHeap heap) throws Exception;
    IExpression deepCopy();
}

