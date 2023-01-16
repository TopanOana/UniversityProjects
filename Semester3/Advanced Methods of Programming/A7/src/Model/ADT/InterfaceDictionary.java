package Model.ADT;

import Exceptions.InterpreterException;

import java.util.Dictionary;
import java.util.Map;

public interface InterfaceDictionary<K, V> {
    /**
     * Adds a (key,value) pair to the dictionary.
     *
     * @param key - id/variable name
     * @param value - variable value
     */
    void add(K key, V value);
    void remove(K key);
    V lookUp(K key) throws InterpreterException;

    void update(K key, V value);

    Map<K,V> getDictionary();
    String toString();

    InterfaceDictionary<K,V> deepCopy();
}
