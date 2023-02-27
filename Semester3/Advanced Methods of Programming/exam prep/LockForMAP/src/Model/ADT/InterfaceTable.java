package Model.ADT;

import Exceptions.InterpreterException;

import java.util.Map;

public interface InterfaceTable {
    Integer getFreeValue();

    Map<Integer,Integer> getTable();

    Integer add(Integer integer);
    void update (Integer position, Integer value) throws InterpreterException;

    Integer get(Integer position) throws InterpreterException;

    void setContent(Map<Integer,Integer> map);

}
