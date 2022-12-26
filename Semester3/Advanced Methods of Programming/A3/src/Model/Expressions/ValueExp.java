package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.Values.Value;

public class ValueExp implements Expression{
    Value value;

    public ValueExp(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValueExp(" +
                "value=" + value +
                ')';
    }

    @Override
    public Expression deepCopy() {
        return new ValueExp(value.deepCopy());
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> tbl) throws InterpreterException {
        return value;
    }
}
