package model.statement;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import model.state.ProgramState;
import exceptions.ListException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws Exception;
    IStatement deepCopy();
}
