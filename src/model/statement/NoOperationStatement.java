package model.statement;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.state.ProgramState;
import exceptions.ListException;
import model.type.IType;

public class NoOperationStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NoOperationStatement();
    }
    @Override
    public String toString(){
        return "NoOperationStatement";
    }
    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) {
        return typeEnvironment;
    }

}
