package Model.ADT;

import Model.Values.Value;

import java.util.ArrayDeque;

public interface InterfaceStack<T>{
    T pop();
    void push(T v);

    T top();

    boolean isEmpty();
    public ArrayDeque<T> getContent();
    String toString();

    InterfaceStack<InterfaceDictionary<String, Value>> deepCopy();
}
