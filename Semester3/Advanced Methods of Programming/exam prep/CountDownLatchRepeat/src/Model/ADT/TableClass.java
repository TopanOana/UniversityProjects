package Model.ADT;

import Exceptions.InterpreterException;

import java.util.HashMap;
import java.util.Map;

public class TableClass implements InterfaceTable<Integer,Integer>{

    Map<Integer,Integer> table;
    java.lang.Integer freeLocation;

    public TableClass() {
        table = new HashMap<>();
        freeLocation=1;
    }

    @Override
    public Map<Integer, Integer> getTable() {
        synchronized (this){
            return table;
        }
    }

    @Override
    public Integer add(Integer val) {
        synchronized (this) {
            table.put(freeLocation, val);
            java.lang.Integer returnInt = freeLocation;
            freeLocation = freeLocation + 1;
            return returnInt;
        }
    }

    @Override
    public void update(Integer pos, Integer val) throws InterpreterException {
        synchronized (this) {
            if (!table.containsKey(pos)) {
                throw new InterpreterException("position is not in heap");
            }
            table.put(pos, val);
        }
    }

    @Override
    public Integer get(Integer pos) {
        synchronized (this) {
           return table.get(pos);
        }
    }
}
