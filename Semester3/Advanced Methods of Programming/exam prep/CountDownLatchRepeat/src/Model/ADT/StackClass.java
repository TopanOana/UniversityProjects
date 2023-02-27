package Model.ADT;

import java.util.ArrayDeque;
import java.util.Stack;

public class StackClass<T> implements InterfaceStack<T>{
    private ArrayDeque<T> stack;

    public StackClass() {
        this.stack = new ArrayDeque<>();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public ArrayDeque<T> getContent() {
        return this.stack;
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }
}
