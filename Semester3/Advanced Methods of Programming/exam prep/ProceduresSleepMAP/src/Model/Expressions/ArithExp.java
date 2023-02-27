package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.Types.IntType;
import Model.Types.Type;
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
    public Type typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnv);
        type2 = expression2.typeCheck(typeEnv);
        if (type1.equals(new IntType())){
            if (type2.equals(new IntType()))
                return new IntType();
            else
                throw new InterpreterException("second operand is not an integer");
        }
        else
            throw new InterpreterException("first operand is not an integer");
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> tbl, InterfaceHeap heap) throws InterpreterException {
        int number1, number2 ;
        if (!expression1.eval(tbl,heap).getType().toString().equals("int")) {
            throw new InterpreterException("Operand1 is not an integer.");
        }
        IntValue i1 = (IntValue) expression1.eval(tbl,heap);
        number1 = i1.getValue();

        if (!expression2.eval(tbl,heap).getType().toString().equals("int")) {
            throw new InterpreterException("Operand2 is not an integer.");
        }
        IntValue i2 = (IntValue) expression2.eval(tbl,heap);
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
