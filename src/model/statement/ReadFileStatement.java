package model.statement;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IntegerType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntegerValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private IExpression expression;
    private String variableName;

    public ReadFileStatement(IExpression expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        if (!symbolTable.isDefined(variableName)) {
            throw new ExpressionException("read file: variable '" + variableName + "' is not declared.");
        }

        IValue variableValue = symbolTable.lookup(variableName);
        if (!(variableValue.getType() instanceof IntegerType)) {
            throw new ExpressionException("read file: variable '" + variableName + "' must be of type int.");
        }

        IValue expressionValue = expression.evaluate(symbolTable);
        if (!(expressionValue.getType() instanceof StringType)) {
            throw new ExpressionException("read file: expression must evaluate to a string value.");
        }

        StringValue fileNameValue = (StringValue) expressionValue;
        if (!fileTable.isDefined(fileNameValue)) {
            throw new ExpressionException("read file: file '" + fileNameValue.getValue() + "' is not open.");
        }

        BufferedReader br = fileTable.lookup(fileNameValue);

        String line;
        try {
            line = br.readLine();
        } catch (IOException exception) {
            throw new ExpressionException("read file: error reading from file '" + fileNameValue.getValue());
        }

        int intValue;
        if (line == null) {
            intValue = 0;
        } else {
            try {
                intValue = Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                throw new ExpressionException("read file: invalid integer value in file '" + fileNameValue.getValue());
            }
        }
        symbolTable.update(variableName, new IntegerValue(intValue));
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), variableName);
    }

    @Override
    public String toString() {
        return "openRFile(" + expression.toString() + ", " + variableName + ")";
    }
}
