package model.type;

public class BooleanType implements IType {
    @Override
    public boolean equals(Object another) {
        return another instanceof BooleanType;
    }

    @Override
    public String toString() {
        return "bool";
    }
}
