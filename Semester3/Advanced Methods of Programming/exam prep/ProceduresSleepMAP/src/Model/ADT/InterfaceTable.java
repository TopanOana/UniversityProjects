package Model.ADT;

import Exceptions.InterpreterException;

import java.util.Map;

public interface InterfaceTable<K,V> {
    void add(K key, V value);

    void remove(K key);

    V lookUp(K key) throws InterpreterException;

    void update(K key, V value);

    InterfaceTable<K,V> deepCopy();

    Map<K,V> getTable();


}
