package Model.ADT;

import Exceptions.InterpreterException;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HeapClass implements InterfaceHeap{
    Map<Integer, Value> table;
    Integer freePos;

    public Integer newValue(){
        Random rand = new Random();
        freePos = rand.nextInt();
        if(freePos == 0 || table.containsKey(freePos))
            freePos = rand.nextInt();
        return freePos;
    }

    public HeapClass() {
        table = new HashMap<>();
        freePos = 1;
    }

    @Override
    public Integer getFreeValue() {
        return freePos;
    }

    @Override
    public Map<Integer, Value> getHeap() {
        return table;
    }

    @Override
    public Integer add(Value val) {
        table.put(freePos,val);
        Integer returnInt = freePos;
        freePos = freePos+1;
        return returnInt;
    }

    @Override
    public void setContent(Map<Integer, Value> map) {
        table=map;
    }

    @Override
    public void update(Integer pos, Value val) {
        if (!table.containsKey(pos)){
            throw new InterpreterException("position is not in heap");
        }
        table.put(pos,val);
    }

    @Override
    public Value get(Integer pos) throws InterpreterException {
        if (!table.containsKey(pos)) {
            throw new InterpreterException("position is not in heap");
        }
        return table.get(pos);
    }
}
