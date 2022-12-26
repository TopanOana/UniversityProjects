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
        dictionary.put(key, value);
    }

    @Override
    public void remove(K key) {
        dictionary.remove(key);
    }

    @Override
    public V lookUp(K key) throws InterpreterException {
        if(dictionary.get(key)!=null)
            return dictionary.get(key);
        else throw new InterpreterException("Key doesn't exist in dictionary.");
    }

    @Override
    public Map<K,V> getDictionary(){
        return dictionary;
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
