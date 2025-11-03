package model.adt;

import exceptions.StackException;
import java.util.List;

public interface MyIStack<T> {
    void push(T element);
    T pop() throws Exception;
    T peek()  throws Exception;
    boolean isEmpty();

}

