package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.Values.Value;

public interface Expression {
    Value eval(InterfaceDictionary<String, Value> tbl) throws InterpreterException;

    Expression deepCopy();
    String toString();
}
