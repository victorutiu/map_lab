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

public class NewStatement implements IStatement {
    private String varName;
    private IExpression expression;

    public NewStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIHeap heap = state.getHeap();

        if (!symbolTable.isDefined(varName)) {
            throw new DictionaryException("Variable " + varName + " is not defined");
        }

        IValue varValue = symbolTable.lookup(varName);

        if (!(varValue.getType() instanceof RefType)) {
            throw new ExpressionException("Variable " + varName + " is not a ref type");
        }

        RefType varRefType = (RefType) varValue.getType();
        IValue evaluated = expression.evaluate(symbolTable, heap);

        if (!evaluated.getType().equals(varRefType.getInner()))
            throw new ExpressionException("Type mismatch: variable " + varName +
                    " expects " + varRefType.getInner() +
                    " but expression has type " + evaluated.getType());

        int newAddress = heap.allocate(evaluated);

        symbolTable.update(varName, new RefValue(newAddress, varRefType.getInner()));

        return null;

    }

    @Override
    public IStatement deepCopy() {
        return new NewStatement(varName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + expression + ")";
    }
}
