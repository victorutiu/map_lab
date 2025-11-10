package model.expression;

import model.adt.MyIDictionary;
import model.value.IValue;
import model.value.IntegerValue;
import model.type.IntegerType;
import exceptions.*;

public class ArithmeticExpression implements IExpression {
    private IExpression leftExpression;
    private IExpression rightExpression;
    private int operationCode;

    public ArithmeticExpression(IExpression leftExpression, IExpression rightExpression, int operationCode) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operationCode = operationCode;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable) throws Exception{
        IValue leftValue = leftExpression.evaluate(symbolTable);
        if (!leftValue.getType().equals(new IntegerType())) {
            throw new FirstOperandNotGoodException("First operand is not an integer");
        }

        IValue rightValue = rightExpression.evaluate(symbolTable);
        if (!rightValue.getType().equals(new IntegerType())) {
            throw new SecondOperandNotGoodException("Second operand is not an integer");
        }

        int number1 = ((IntegerValue) leftValue).getValue();
        int number2 = ((IntegerValue) rightValue).getValue();

        return switch (operationCode) {
            case 1 -> new IntegerValue(number1 + number2);
            case 2 -> new IntegerValue(number1 - number2);
            case 3 -> new IntegerValue(number1 * number2);
            case 4 -> {
                if (number2 == 0) {
                    throw new DivisionByZeroException("Division by zero");
                }
                yield new IntegerValue(number1 / number2);
            }
            default -> throw new ExpressionException("Invalid operation: " + operationCode);
        };
    }

    @Override
    public IExpression deepCopy() {
        return new ArithmeticExpression(leftExpression.deepCopy(), rightExpression.deepCopy(), operationCode);
    }

    @Override
    public String toString() {
        String operatorSymbol = switch (operationCode) {
            case 1 -> "+";
            case 2 -> "-";
            case 3 -> "*";
            case 4 -> "/";
            default -> "?";
        };
        return "(" + leftExpression.toString() + " " + operatorSymbol + " " + rightExpression.toString() + ")";
    }

}