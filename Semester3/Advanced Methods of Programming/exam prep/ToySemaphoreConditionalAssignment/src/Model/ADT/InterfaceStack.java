package Model.ADT;

import java.util.ArrayDeque;

public interface InterfaceStack<T>{
    T pop();
    void push(T v);

    boolean isEmpty();
    public ArrayDeque<T> getContent();
    String toString();
}
