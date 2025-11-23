package model.statement;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import exceptions.ListException;
import model.state.ProgramState;
import model.expression.IExpression;
import model.value.IValue;
import model.value.BooleanValue;

public class IfStatement implements IStatement {
    private IExpression conditionExpression;
    private IStatement thenStatement;
    private IStatement elseStatement;

    public IfStatement(IExpression conditionExpression, IStatement thenStatement, IStatement elseStatement) {
        this.conditionExpression = conditionExpression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        var stack = state.getExecutionStack();
        var symbolTable = state.getSymbolTable();

        IValue conditionValue = conditionExpression.evaluate(symbolTable, state.getHeap());

        if (!(conditionValue instanceof BooleanValue)) {
            throw new ExpressionException("Condition is not a boolean.");
        }

        boolean conditionResult = ((BooleanValue) conditionValue).getValue();

        if (conditionResult) {
            stack.push(thenStatement);
        } else {
            stack.push(elseStatement);
        }

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(conditionExpression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }


    @Override
    public String toString() {
        return "If(" + conditionExpression.toString() + ") Then(" + thenStatement.toString() + ") Else(" + elseStatement.toString() + ")";
    }
}
