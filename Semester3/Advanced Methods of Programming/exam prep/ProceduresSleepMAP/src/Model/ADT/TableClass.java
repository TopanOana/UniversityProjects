package Model.ADT;

import Exceptions.InterpreterException;

import java.util.HashMap;
import java.util.Map;

public class TableClass<K,V> implements InterfaceTable<K,V>{

    Map<K,V> table;

    public TableClass() {
        this.table = new HashMap<>();
    }

    @Override
    public void add(K key, V value) {
        synchronized (this) {
            table.put(key, value);
        }
    }

    @Override
    public void remove(K key) {
        synchronized (this) {
            table.remove(key);
        }
    }

    @Override
    public V lookUp(K key) throws InterpreterException {
        synchronized (this) {
            return table.get(key);
        }
    }

    @Override
    public void update(K key, V value) {
        synchronized (this) {
            table.put(key, value);
        }
    }

    @Override
    public InterfaceTable<K, V> deepCopy() {
        InterfaceTable<K,V> toReturn = new TableClass<>();
        for (K key : table.keySet())
            toReturn.add(key, table.get(key));
        return toReturn;
    }

    @Override
    public Map<K, V> getTable() {
        synchronized (this){
            return table;
        }
    }
}
