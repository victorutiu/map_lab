package model.statement;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStatement implements IStatement {
    private IExpression expression;

    public CloseRFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        IValue value = expression.evaluate(symbolTable);

        if (!(value.getType() instanceof StringType)) {
            throw new ExpressionException("closeRFile: expression must evaluate to a string.");
        }


        StringValue fileNameValue = (StringValue) value;

        if (!fileTable.isDefined(fileNameValue)) {
            throw new ExpressionException(
                    "closeRFile: file '" + fileNameValue.getValue() + "' is not open.");
        }

        BufferedReader br = fileTable.lookup(fileNameValue);

        try {
            br.close();
        } catch (IOException e) {
            throw new ExpressionException(
                    "closeRFile: error closing file '" + fileNameValue.getValue() + "'.");
        }

        fileTable.remove(fileNameValue);

        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFileStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }


}
