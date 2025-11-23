package model.adt;
import model.value.IValue;
import model.adt.MyIHeap;
import java.util.HashMap;
import java.util.Map;


public class MyHeap implements MyIHeap{
    private Map<Integer, IValue> heap;
    private int freeAddress;

    public MyHeap(){
        heap = new HashMap<>();
        freeAddress = 1;
    }

    @Override
    public int allocate(IValue value){
        heap.put(freeAddress, value);
        freeAddress++;
        return freeAddress - 1;
    }

    @Override
    public void put(int address, IValue value) {
        heap.put(address, value);
    }

    @Override
    public IValue get(int address){
        return heap.get(address);
    }

    @Override
    public boolean contains(int address){
        return heap.containsKey(address);
    }

    @Override
    public void remove (int address){
        heap.remove(address);
    }

    @Override
    public Map<Integer,IValue> getContent(){
        return heap;
    }

    @Override
    public void setContent(Map<Integer,IValue> newMap){
        heap = newMap;
    }

    @Override
    public int getFreeAddress(){
        return freeAddress;
    }

    @Override
    public void setFreeAddress(int newAddress){
        freeAddress = newAddress;
    }

    @Override
    public MyIHeap deepCopy() {
        MyHeap newHeap = new MyHeap();
        newHeap.setFreeAddress(freeAddress);

        Map<Integer, IValue> newMap = new HashMap<>();
        for(Map.Entry<Integer, IValue> entry : heap.entrySet()){
            newMap.put(entry.getKey(), entry.getValue().deepCopy());
        }

        newHeap.setContent(newMap);
        return newHeap;
    }

    @Override
    public String toString() {
        return heap.toString();
    }

}
