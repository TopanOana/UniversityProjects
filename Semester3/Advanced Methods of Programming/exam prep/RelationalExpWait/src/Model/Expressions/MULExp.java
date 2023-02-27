package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class MULExp implements Expression{
    Expression exp1,exp2;

    public MULExp(Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public String toString() {
        return "MUL("+exp1.toString()+","+exp2.toString()+")";
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> tbl, InterfaceHeap heap) throws InterpreterException {
        Value val1,val2;
        val1 = exp1.eval(tbl,heap);
        val2 = exp2.eval(tbl,heap);

        if(!val1.getType().equals(new IntType()))
            throw new InterpreterException("exp1 is not an integer");

        if(!val2.getType().equals(new IntType()))
            throw new InterpreterException("exp2 is not an integer");

        IntValue v1 = (IntValue) val1;
        IntValue v2 = (IntValue) val2;

        return new IntValue((v1.getValue()*v2.getValue())-(v1.getValue()+v2.getValue()));
    }

    @Override
    public Type typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if (!exp1.typeCheck(typeEnv).equals(new IntType()))
            throw new InterpreterException("not of correct type exp1 in MUL");
        if (!exp2.typeCheck(typeEnv).equals(new IntType()))
            throw new InterpreterException("not of correct type exp2 in MUL");
        return new IntType();
    }

    @Override
    public Expression deepCopy() {
        return null;
    }
}
