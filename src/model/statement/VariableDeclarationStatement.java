package model.statement;

import exceptions.ExpressionException;
import exceptions.VariableAlreadyExistsException;
import model.state.ProgramState;
import model.type.*;
import model.value.*;
import exceptions.DictionaryException;
import exceptions.ListException;

public class VariableDeclarationStatement implements IStatement {
    private String variableName;
    private IType variableType;

    public VariableDeclarationStatement(String variableName, IType variableType) {
        this.variableName = variableName;
        this.variableType = variableType;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        var symbolTable = state.getSymbolTable();

        if (symbolTable.isDefined(variableName)) {
            throw new VariableAlreadyExistsException("Variable '" + variableName + "' already exists");
        }

        IValue defaultValue = getDefauldValue(variableType);
        symbolTable.put(variableName, defaultValue);
        return state;
    }

    private IValue getDefauldValue(IType variableType) throws DictionaryException {
        if (variableType instanceof IntegerType) {
            return new IntegerValue(0);
        } else if (variableType instanceof BooleanType) {
            return new BooleanValue(false);
        } else {
            throw new DictionaryException("Invalid variable type");
        }
    }

    @Override
    public String toString() {
        return variableType + " " + variableName;
    }
}

