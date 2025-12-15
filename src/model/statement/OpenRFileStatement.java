package model.statement;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.expression.IExpression;
import model.state.ProgramState;
import model.type.IType;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStatement{
    private IExpression expression;

    public OpenRFileStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws Exception {

        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        IValue value = expression.evaluate(symbolTable, state.getHeap());
        if (!(value.getType() instanceof StringType)) {
            throw new ExpressionException("open file: expression must evaluate to a string.");
        }

        StringValue fileNameValue = (StringValue) value;
        if (fileTable.isDefined(fileNameValue)) {
            throw new ExpressionException("open file: file '" + fileNameValue + "' is already open.");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileNameValue.getValue()));
            fileTable.put(fileNameValue, br);

        } catch (IOException exception) {
            throw new ExpressionException("open file: error opening file '" + fileNameValue + "': " + exception.getMessage());
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFileStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "openRFile(" + expression.toString() + ")";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnvironment) throws Exception {
        IType expressionType = expression.typecheck(typeEnvironment);

        if (!expressionType.equals(new StringType())) {
            throw new Exception("openRFile: expression must have type string.");
        }

        return typeEnvironment;
    }

}
