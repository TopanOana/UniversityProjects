package Model.ADT;

import Exceptions.InterpreterException;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

public class DictionaryClass<K,V> implements InterfaceDictionary<K,V>{
    Map<K,V> dictionary;

    public DictionaryClass() {
        this.dictionary = new Hashtable<>();
    }



    @Override
    public void add(K key, V value) {
        synchronized (this) {
            dictionary.put(key, value);
        }
    }

    @Override
    public void remove(K key) {
        synchronized (this) {
            dictionary.remove(key);
        }
    }

    @Override
    public V lookUp(K key) throws InterpreterException {
        synchronized (this) {
            return dictionary.get(key);
        }
    }

    @Override
    public Map<K,V> getDictionary(){
        synchronized (this) {
            return dictionary;
        }
    }
    @Override
    public String toString(){
        return dictionary.toString();
    }

    @Override
    public void update(K key, V value) {
        synchronized (this) {
            dictionary.put(key, value);
        }
    }

    @Override
    public InterfaceDictionary<K, V> deepCopy() {
        InterfaceDictionary<K,V> toReturn = new DictionaryClass<>();
        for (K key : dictionary.keySet())
            toReturn.add(key, dictionary.get(key));
        return toReturn;
    }
}
