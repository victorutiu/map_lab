package model.type;

import model.value.IValue;

public interface IType {
    boolean equals(Object another);
    IValue defaultValue();
    IType deepCopy();
}
