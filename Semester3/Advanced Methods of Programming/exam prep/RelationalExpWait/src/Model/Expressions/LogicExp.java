package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExp implements Expression{
    Expression expression1;
    Expression expression2;
    int operator;

    public LogicExp(Expression expression1, Expression expression2, String operate) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        if (operate.equals("and"))
            operator=1;
        else
            operator=0;
    }

    @Override
    public Type typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type1,type2;
        type1 = expression1.typeCheck(typeEnv);
        type2 = expression2.typeCheck(typeEnv);
        if(type1.equals(new BoolType())){
            if(type2.equals(new BoolType()))
                return new BoolType();
            else
                throw new InterpreterException("second operand is not a boolean");
        }
        else
            throw new InterpreterException("first operand is not a boolean");
    }

    public String toString(){
        String op;
        if(operator==1)
            op="&&";
        else op="||";
        return "LogicExp("+expression1.toString() + op + expression2.toString()+")";
    }
    @Override
    public Value eval(InterfaceDictionary<String, Value> tbl, InterfaceHeap heap) throws InterpreterException {
        if (!expression1.eval(tbl,heap).getType().toString().equals("boolean"))
            throw new InterpreterException("Operand1 is not a boolean.");
        if (!expression2.eval(tbl,heap).getType().toString().equals("boolean"))
            throw new InterpreterException("Operand2 is not a boolean.");
        if (operator==1){
            if(expression1.eval(tbl,heap).equals(true) && expression2.eval(tbl,heap).equals(true))
                return new BoolValue(true);
            else return new BoolValue(false);
        }
        else {
            if (operator == 0)
                if (expression1.eval(tbl,heap).equals(true) || expression2.eval(tbl,heap).equals(true))
                    return new BoolValue(true);
                else return new BoolValue(false);
        }
        return null;
    }

    @Override
    public LogicExp deepCopy() {
        String operators;
        if(this.operator==0)
            operators="or";
        else
            operators="and";
        return new LogicExp(expression1.deepCopy(),expression2.deepCopy(),operators);
    }
}
