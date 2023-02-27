package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class NegExp implements Expression{
    Expression exp;

    @Override
    public String toString() {
        return "!"+exp.toString();
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> tbl, InterfaceHeap heap) throws InterpreterException {
        Value val = exp.eval(tbl,heap);
        if(!val.getType().equals(new BoolType()))
            throw new InterpreterException("fuckl you");

        BoolValue value = (BoolValue) val;
        return new BoolValue(!value.getValue());
    }

    @Override
    public Type typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type = exp.typeCheck(typeEnv);
        if(!type.equals(new BoolType()))
            throw new InterpreterException("i don't like this");
        return new BoolType();
    }

    @Override
    public Expression deepCopy() {
        return null;
    }

    public NegExp(Expression exp) {
        this.exp = exp;
    }
}
