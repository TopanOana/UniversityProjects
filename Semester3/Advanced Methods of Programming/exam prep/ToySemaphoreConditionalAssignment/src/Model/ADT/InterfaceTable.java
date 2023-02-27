package Model.ADT;

import Exceptions.InterpreterException;

import java.util.Map;

public interface InterfaceTable<K,V> {
    Map<K,V> getTable();

    K add(V v);

    void update(K key, V val) throws InterpreterException;

    V get(K pos) throws InterpreterException;

    boolean containsKey(K key);

}
