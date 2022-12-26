package Model.ADT;

import Exceptions.InterpreterException;

import java.util.Dictionary;
import java.util.Hashtable;

public class DictionaryClass<K,V> implements InterfaceDictionary<K,V>{
    Dictionary<K,V> dictionary;

    public DictionaryClass() {
        this.dictionary = new Hashtable<>();
    }

    @Override
    public void add(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public void remove(K key) {
        dictionary.remove(key);
    }

    @Override
    public V lookUp(K key) throws InterpreterException {
        return dictionary.get(key);
    }

    @Override
    public String toString(){
        return dictionary.toString();
    }

    @Override
    public void update(K key, V value) {
        dictionary.put(key, value);
    }
}
