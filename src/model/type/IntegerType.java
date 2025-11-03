package model.type;

public class IntegerType implements IType {
    @Override
    public boolean equals(Object another) {
        return another instanceof IntegerType;
    }

    @Override
    public String toString() {
        return "int";
    }
}
