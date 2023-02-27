package Model.ADT;

import Exceptions.InterpreterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableClass implements InterfaceTable<Integer, Triple<Integer, ArrayList<Integer>, Integer>> {
    Map<Integer, Triple<Integer, ArrayList<Integer>, Integer>> table;
    Integer freeLocation;

    public TableClass() {
        table = new HashMap<>();
        freeLocation=1;
    }

    @Override
    public Map<Integer, Triple<Integer, ArrayList<Integer>, Integer>> getTable() {
        synchronized (this) {
            return table;
        }
    }

    @Override
    public Integer add(Triple<Integer, ArrayList<Integer>, Integer> integerArrayListIntegerTriple) {
        synchronized (this) {
            table.put(freeLocation, integerArrayListIntegerTriple);
            Integer returnInt = freeLocation;
            freeLocation = freeLocation + 1;
            return returnInt;
        }
    }

    @Override
    public void update(Integer key, Triple<Integer, ArrayList<Integer>, Integer> val) throws InterpreterException {
        synchronized (this) {
            if (!table.containsKey(key)) {
                throw new InterpreterException("position is not in table");
            }
            table.put(key, val);
        }
    }

    @Override
    public Triple<Integer, ArrayList<Integer>, Integer> get(Integer pos) throws InterpreterException {
        synchronized (this) {
            if (!table.containsKey(pos)) {
                throw new InterpreterException("position is not in table");
            }
            return table.get(pos);
        }
    }

    @Override
    public boolean containsKey(Integer key) {
        synchronized (this){
            return table.containsKey(key);
        }
    }
}
