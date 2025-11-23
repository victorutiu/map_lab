package model.statement;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import exceptions.ListException;
import model.state.ProgramState;
import model.expression.IExpression;
import model.value.IValue;

public class PrintStatement implements IStatement {
    private IExpression expression;

    public PrintStatement(IExpression expression) {

        this.expression = expression;
    }

    @Override
     public ProgramState execute(ProgramState state) throws Exception {

        var outputList = state.getOutputList();
        var symbolTable = state.getSymbolTable();

        IValue value = expression.evaluate(symbolTable, state.getHeap());
        outputList.add(value);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
