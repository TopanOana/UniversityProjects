package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithExp implements Expression{
    Expression expression1;
    Expression expression2;
    int operator;
    //operator :
    // 0 -> addition
    // 1 -> subtraction
    // 2 -> multiplication
    // 3 -> division

    @Override
    public String toString() {
        String op;
        switch(operator){
            case 0:
                op="+";
                break;
            case 1:
                op="-";
                break;
            case 2:
                op="*";
                break;
            case 3:
                op="/";
                break;
            default:
                return null;
        }

        return "ArithExp(" +
                expression1.toString() +
                op + expression2.toString()+
                ")";
    }

    @Override
    public Expression deepCopy() {
        String op = null;
        switch(operator){
            case 0:
                op="+";
                break;
            case 1:
                op="-";
                break;
            case 2:
                op="*";
                break;
            case 3:
                op="/";
                break;
        }
        return new ArithExp(op.toString(),expression1.deepCopy(),expression2.deepCopy());
    }

    public ArithExp(String operate, Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        if(operate.equals("+"))
            operator=0;
        else
            if(operate.equals("-"))
                operator=1;
            else
                if(operate.equals("*"))
                    operator=2;
                else
                    operator=3;
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> tbl) throws InterpreterException {
        int number1, number2 ;
        if (!expression1.eval(tbl).getType().toString().equals("int")) {
            throw new InterpreterException("Operand1 is not an integer.");
        }
        IntValue i1 = (IntValue) expression1.eval(tbl);
        number1 = i1.getValue();

        if (!expression2.eval(tbl).getType().toString().equals("int")) {
            throw new InterpreterException("Operand2 is not an integer.");
        }
        IntValue i2 = (IntValue) expression2.eval(tbl);
        number2 = i2.getValue();

        switch (operator){
            case 0:
                return new IntValue(number1+number2);
            case 1:
                return new IntValue(number1-number2);
            case 2:
                return new IntValue(number1*number2);
            case 3:
                if (number2==0)
                    throw new InterpreterException("Division by zero.");
                return new IntValue(number1/number2);
        }
        return null;
    }
}
