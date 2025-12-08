package model.adt;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import exceptions.StackException;
import exceptions.StackIsEmptyException;

public class MyStack<T> implements MyIStack<T> {

    private Deque<T> stack = new ArrayDeque<>();

    @Override
    public void push(T element) {
        stack.push(element);
    }

    @Override
    public T pop() throws Exception {
        if (stack.isEmpty()) {
            throw new StackIsEmptyException("Stack is empty.");
        }
        return stack.pop();
    }

    @Override
    public T peek() throws Exception {
        if (stack.isEmpty()) {
            throw new StackIsEmptyException("Stack is empty.");
        }
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    @Override
    public MyIStack<T> createNewEmptyStack() {
        return new MyStack<>();
    }
}

