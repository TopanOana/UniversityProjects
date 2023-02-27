package Model.ADT;

import Exceptions.InterpreterException;
import Model.Values.Value;

import java.util.Map;

public interface InterfaceTable<K,V> {
    Map<K,V> getTable();

    K add(V val);

    void update(K pos, V val) throws InterpreterException;

    V get(Integer pos);


}
