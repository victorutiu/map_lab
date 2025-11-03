package model.value;

import model.type.IType;
import model.type.IntegerType;

public class IntegerValue implements IValue {
    private int value;

    public IntegerValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new IntegerType();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
