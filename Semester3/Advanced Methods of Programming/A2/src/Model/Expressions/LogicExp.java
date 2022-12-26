package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.Types.BoolType;
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

    public String toString(){
        String op;
        if(operator==1)
            op="&&";
        else op="||";
        return "LogicExp("+expression1.toString() + op + expression2.toString()+")";
    }
    @Override
    public Value eval(InterfaceDictionary<String, Value> tbl) throws InterpreterException {
        if (!expression1.eval(tbl).getType().toString().equals("boolean"))
            throw new InterpreterException("Operand1 is not a boolean.");
        if (!expression2.eval(tbl).getType().toString().equals("boolean"))
            throw new InterpreterException("Operand2 is not a boolean.");
        if (operator==1){
            if(expression1.eval(tbl).equals(true) && expression2.eval(tbl).equals(true))
                return new BoolValue(true);
            else return new BoolValue(false);
        }
        else {
            if (operator == 0)
                if (expression1.eval(tbl).equals(true) || expression2.eval(tbl).equals(true))
                    return new BoolValue(true);
                else return new BoolValue(false);
        }
        return null;
    }
}
