package model.adt;

import exceptions.ListException;

import java.util.List;

public interface MyIList<T> {
    void add(T element) throws Exception;
    List<T> getAll();
    int size();
}
