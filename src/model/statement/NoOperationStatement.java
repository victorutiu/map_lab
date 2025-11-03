package model.statement;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import model.state.ProgramState;
import exceptions.ListException;

public class NoOperationStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        return state;
    }


    @Override
    public String toString(){
        return "NoOperationStatement";
    }
}
