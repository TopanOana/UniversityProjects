package Model.ADT;

import Exceptions.InterpreterException;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;


public interface InterfaceHeap {
    Integer getFreeValue();
    Map<Integer, Value> getHeap();
    Integer add(Value val);
    void update(Integer pos, Value val) throws InterpreterException;
    Value get(Integer pos) throws InterpreterException;

    void setContent(Map<Integer, Value> map);

}
