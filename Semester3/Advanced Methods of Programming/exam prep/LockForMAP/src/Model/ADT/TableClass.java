package Model.ADT;

import Exceptions.InterpreterException;

import java.util.HashMap;
import java.util.Map;

public class TableClass implements InterfaceTable{

    Map<Integer, Integer> table;

    Integer freePos;

    public TableClass() {
        table = new HashMap<>();
        freePos = 1;
    }

    @Override
    public Integer getFreeValue() {
        return freePos;
    }

    @Override
    public Map<Integer, Integer> getTable() {
        synchronized (this) {
            return table;
        }
    }

    @Override
    public Integer add(Integer integer) {
        synchronized (this) {
            table.put(freePos, integer);
            Integer returnInt = freePos;
            freePos = freePos + 1;
            return returnInt;
        }
    }

    @Override
    public void update(Integer position, Integer value) throws InterpreterException {
        synchronized (this) {
            if (!table.containsKey(position)) {
                throw new InterpreterException("position is not in table");
            }
            table.put(position, value);
        }
    }

    @Override
    public Integer get(Integer position) throws InterpreterException {
        synchronized (this) {
            if (!table.containsKey(position)) {
                throw new InterpreterException("position is not in table");
            }
            return table.get(position);
        }
    }

    @Override
    public void setContent(Map<Integer, Integer> map) {
        synchronized (this) {
            table = map;
        }
    }
}
