package model.statement;

import model.adt.MyIStack;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.BooleanType;
import model.value.BooleanValue;
import model.value.IValue;

public class WhileStatement implements IStatement {

    private IExpression expression;
    private IStatement statement;

    public WhileStatement(IExpression expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception{

        MyIStack<IStatement> stack = state.getExecutionStack();
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();

        MyIHeap heap = state.getHeap();

        IValue conditionValue = expression.evaluate(symbolTable, heap);

        if (!conditionValue.getType().equals(new BooleanType())) {
            throw new Exception("While: condition is not boolean");
        }

        BooleanValue boolValue = (BooleanValue) conditionValue;

        if (boolValue.getValue()) {
            stack.push(this);
            stack.push(statement);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return  new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "While("+ expression +")" + statement;
    }
}
