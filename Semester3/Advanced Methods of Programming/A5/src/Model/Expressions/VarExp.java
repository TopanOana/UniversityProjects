package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.Values.Value;

public class VarExp implements Expression{
    String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> tbl, InterfaceHeap heap) throws InterpreterException {
        if (tbl.lookUp(id)==null)
            throw new InterpreterException("variable "+id+" is not defined.");
        return tbl.lookUp(id);
    }

    public Expression deepCopy(){
        return new VarExp(id.toString());
    }
    @Override
    public String toString() {
        return "VarExp(" +
                "id='" + id + '\'' +
                ')';
    }
}
