package Model.ADT;

public interface InterfaceStack<T>{
    T pop();
    void push(T v);

    boolean isEmpty();

    String toString();
}
