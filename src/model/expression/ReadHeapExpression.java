package model.expression;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class ReadHeapExpression implements IExpression{
    private IExpression expression;

    public  ReadHeapExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTable, MyIHeap heap) throws Exception {
        IValue evaluated = expression.evaluate(symTable, heap);

        if (!(evaluated instanceof RefValue refVal))
            throw new ExpressionException("rH argument must evaluate to a RefValue");

        int address = refVal.getAddress();

        if (!heap.contains(address))
            throw new ExpressionException("Invalid heap address: " + address);

        return heap.get(address);
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "rH(" +  expression.toString() + ")";
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnvironment) throws Exception {
        IType expressionType = expression.typecheck(typeEnvironment);

        if (!(expressionType instanceof RefType refType)) {
            throw new Exception("The rH argument must have a RefType");
        }

        return refType.getInner();
    }

}
