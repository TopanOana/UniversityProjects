package Model.ADT;

import Exceptions.InterpreterException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TableClass implements InterfaceTable<Integer, Pair<Integer, ArrayList<Integer>>> {

    Map<Integer,Pair<Integer,ArrayList<Integer>>> table;
    Integer freePos;

    @Override
    public Integer getFreeValue() {
        return freePos;
    }

    public TableClass() {
        table = new HashMap<>();
        freePos = 1;
    }

    @Override
    public Map<Integer, Pair<Integer, ArrayList<Integer>>> getTable() {
        synchronized (this){
            return table;
        }
    }

    @Override
    public Integer add(Pair<Integer, ArrayList<Integer>> val) {
        synchronized (this) {
            table.put(freePos, val);
            Integer returnInt = freePos;
            freePos = freePos + 1;
            return returnInt;
        }
    }

    @Override
    public void update(Integer pos, Pair<Integer, ArrayList<Integer>> val) throws InterpreterException {
        synchronized (this) {
            if (!table.containsKey(pos)) {
                throw new InterpreterException("position is not in heap");
            }
            table.put(pos, val);
        }
    }

    @Override
    public Pair<Integer, ArrayList<Integer>> get(Integer pos) throws InterpreterException {
        synchronized (this) {
            if (!table.containsKey(pos)) {
                throw new InterpreterException("position is not in heap");
            }
            return table.get(pos);
        }
    }

    @Override
    public void setContent(Map<Integer, Pair<Integer, ArrayList<Integer>>> map) {
        synchronized (this) {
            table = map;
        }
    }
}
