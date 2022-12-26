package Model.ADT;

import java.util.Stack;

public class StackClass<T> implements InterfaceStack<T>{
    Stack<T>  stack;

    public StackClass() {
        this.stack = new Stack<>();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
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
