package model.statement;

import exceptions.DictionaryException;
import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.state.ProgramState;
import exceptions.ListException;
import model.type.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws Exception;
    IStatement deepCopy();
    MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws Exception;

}
