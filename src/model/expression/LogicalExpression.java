package model.expression;

import exceptions.ExpressionException;
import exceptions.FirstOperandNotGoodException;
import exceptions.SecondOperandNotGoodException;
import model.adt.MyIDictionary;
import model.value.IValue;
import model.value.BooleanValue;
import model.type.BooleanType;

public class LogicalExpression implements IExpression {

    private final IExpression leftExpression;
    private final IExpression rightExpression;
    private final int operationCode;

    public LogicalExpression(IExpression leftExpression, IExpression rightExpression, int operationCode) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operationCode = operationCode;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable) throws Exception {
        IValue leftValue = leftExpression.evaluate(symbolTable);
        if (!leftValue.getType().equals(new BooleanType())) {
            throw new FirstOperandNotGoodException("First operand is not a boolean.");
        }

        IValue rightValue = rightExpression.evaluate(symbolTable);
        if (!rightValue.getType().equals(new BooleanType())) {
            throw new SecondOperandNotGoodException("Second operand is not a boolean.");
        }

        boolean bool1 = ((BooleanValue) leftValue).getValue();
        boolean bool2 = ((BooleanValue) rightValue).getValue();

        return switch (operationCode) {
            case 1 -> new BooleanValue(bool1 && bool2);
            case 2 -> new BooleanValue(bool1 || bool2);
            default -> throw new ExpressionException("Invalid logical operator code: " + operationCode);
        };
    }

    @Override
    public String toString() {
        String operatorSymbol = switch (operationCode) {
            case 1 -> "&&";
            case 2 -> "||";
            default -> "?";
        };
        return "(" + leftExpression.toString() + " " + operatorSymbol + " " + rightExpression.toString() + ")";
    }
}
