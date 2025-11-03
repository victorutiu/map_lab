package model.value;

import model.type.IType;
import model.type.BooleanType;

public class BooleanValue implements IValue {
    private boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public IType getType() {
        return new  BooleanType();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
