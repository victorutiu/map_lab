package model.expression;

import exceptions.ExpressionException;
import exceptions.FirstOperandNotGoodException;
import exceptions.SecondOperandNotGoodException;
import model.adt.MyIDictionary;
import model.type.BooleanType;
import model.type.IntegerType;
import model.value.BooleanValue;
import model.value.IValue;
import model.value.IntegerValue;

public class RelationalExpression implements IExpression{
        private IExpression leftExpression;
        private IExpression rightExpression;
        private String operator;

        public RelationalExpression(IExpression leftExpression, IExpression rightExpression, String operator){
            this.leftExpression = leftExpression;
            this.rightExpression = rightExpression;
            this.operator = operator;
        }

        @Override
        public IValue evaluate(MyIDictionary<String, IValue> symbolTable) throws Exception {
            IValue leftValue = leftExpression.evaluate(symbolTable);
            if (!leftValue.getType().equals(new IntegerType())) {
                throw new FirstOperandNotGoodException("Left operand is not an integer");
            }

            IValue rightValue = rightExpression.evaluate(symbolTable);
            if (!rightValue.getType().equals(new IntegerType())) {
                throw new SecondOperandNotGoodException("Right operand is not an integer");
            }

            int left = ((IntegerValue)leftValue).getValue();
            int right = ((IntegerValue)rightValue).getValue();

            return switch (operator) {
                case "<"  -> new BooleanValue(left < right);
                case "<=" -> new BooleanValue(left <= right);
                case "==" -> new BooleanValue(left == right);
                case "!=" -> new BooleanValue(left != right);
                case ">"  -> new BooleanValue(left > right);
                case ">=" -> new BooleanValue(left >= right);
                default -> throw new ExpressionException("Invalid relational operator: " + operator);
            };
        }

        @Override
        public IExpression deepCopy() {
            return new RelationalExpression(leftExpression.deepCopy(), rightExpression.deepCopy(), operator);
        }

        @Override
        public String toString(){
            return leftExpression.toString() + " " + operator + " " + rightExpression.toString();
        }
}
