package model.statement;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import exceptions.ListException;
import model.adt.MyIDictionary;
import model.state.ProgramState;
import model.expression.IExpression;
import model.type.BooleanType;
import model.type.IType;
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

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(conditionExpression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }


    @Override
    public String toString() {
        return "If(" + conditionExpression.toString() + ") Then(" + thenStatement.toString() + ") Else(" + elseStatement.toString() + ")";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws Exception {
        IType conditionType = conditionExpression.typecheck(typeEnvironment);

        if (!conditionType.equals(new BooleanType())) {
            throw new Exception("IF condition is not of type boolean.");
        }

        thenStatement.typecheck(typeEnvironment.cloneDictionary());
        elseStatement.typecheck(typeEnvironment.cloneDictionary());

        return typeEnvironment;
    }

}
