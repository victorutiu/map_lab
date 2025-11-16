package model.value;

import model.type.IType;
import model.type.StringType;

public class StringValue implements IValue{
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof StringValue)
            return ((StringValue) another).getValue().equals(value);
        return false;
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(value);
    }
}
