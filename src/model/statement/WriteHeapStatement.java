package model.statement;

import exceptions.ExpressionException;
import exceptions.DictionaryException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IType;
import model.type.RefType;
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

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws Exception {

        IType variableType = typeEnvironment.lookup(varName);

        if (!(variableType instanceof RefType refType)) {
            throw new Exception("wH: variable " + varName + " must have a RefType.");
        }

        IType expressionType = expression.typecheck(typeEnvironment);

        if (!expressionType.equals(refType.getInner())) {
            throw new Exception("wH: type mismatch. Variable " + varName +
                    " expects " + refType.getInner() +
                    " but expression has " + expressionType);
        }

        return typeEnvironment;
    }

}
