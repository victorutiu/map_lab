package model.statement;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import model.state.ProgramState;
import exceptions.ListException;

public class CompoundStatement implements IStatement {
    private IStatement firstStatement;
    private IStatement secondStatement;

    public CompoundStatement(IStatement firstStatement, IStatement secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        var stack = state.getExecutionStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(firstStatement.deepCopy(), secondStatement.deepCopy());
    }


    @Override
    public String toString() {
        return "("+ firstStatement.toString() + ";" + secondStatement.toString() + ")";
    }
}
