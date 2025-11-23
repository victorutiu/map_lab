package model.value;
import model.type.IType;
import model.type.RefType;

public class RefValue implements IValue {
    private int address;
    private IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    public IType getLocationType(IType locationType) {
        return locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    public String toString() {
        return "(" + address + "," + locationType.toString() + ")";
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefValue otherRef) {
            return address == otherRef.address && locationType.equals(otherRef.locationType);
        }
        return false;
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType.deepCopy());
    }
}
