package model.type;
import model.value.IValue;
import model.value.BooleanValue;

public class BooleanType implements IType {
    @Override
    public boolean equals(Object another) {
        return another instanceof BooleanType;
    }

    @Override
    public IValue defaultValue() {
        return new BooleanValue(false);
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public IType deepCopy() {
        return new BooleanType();
    }
}
