package model.adt;
import java.util.Map;
import model.value.IValue;

public interface MyIHeap {
    int allocate(IValue value);
    void put(int address, IValue value);
    IValue get(int address);
    boolean contains(int address);
    void remove(int address);
    Map<Integer,IValue> getContent();
    void setContent(Map<Integer,IValue> newMap);
    int getFreeAddress();
    void setFreeAddress(int newAddress);
    MyIHeap deepCopy();
}
