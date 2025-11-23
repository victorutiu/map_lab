package model.statement;

import exceptions.DictionaryException;
import exceptions.ListException;
import exceptions.VariableNotExistsException;
import model.state.ProgramState;
import model.expression.IExpression;
import model.value.IValue;

import java.beans.Expression;
import exceptions.ExpressionException;

public class AssignmentStatement implements IStatement {
    private String variableName;
    private IExpression expression;

    public AssignmentStatement(String variableName, IExpression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        var symbolTable = state.getSymbolTable();

        if (!symbolTable.isDefined(variableName)) {
            throw new VariableNotExistsException("Variable '" + variableName + "' was not declared.");
        }

        IValue valueToAssign = expression.evaluate(symbolTable, state.getHeap());
        IValue existingValue = symbolTable.lookup(variableName);

        if (!valueToAssign.getType().equals(existingValue.getType())) {
            throw new DictionaryException("Type mismatch for variable '" + variableName + "'.");
        }

        symbolTable.update(variableName, valueToAssign);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(variableName, expression.deepCopy());
    }


    @Override
    public String toString() {
        return variableName + " = " + expression.toString();
    }
}
