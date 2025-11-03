package model.adt;

import exceptions.ListException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MyList<T> implements MyIList<T> {

    private List<T> outputList = new ArrayList<>();

    @Override
    public void add(T element) throws Exception {
        if (element == null) {
            throw new ListException("Cannot add null value to output list.");
        }
        outputList.add(element);
    }

    @Override
    public List<T> getAll() {
        return outputList;
    }

    @Override
    public int size() {
        return outputList.size();
    }

    @Override
    public String toString() {
        return outputList.toString();
    }
}
