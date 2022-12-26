package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class RelExp implements Expression{
    Expression expression1;
    Expression expression2;
    int operator;
    //operator :
    // 0 -> <
    // 1 -> <=
    // 2 -> ==
    // 3 -> !=
    // 4 -> >
    // 5 -> >=

    @Override
    public String toString() {
        String op;
        switch(operator){
            case 0:
                op="<";
                break;
            case 1:
                op="<=";
                break;
            case 2:
                op="==";
                break;
            case 3:
                op="!=";
                break;
            case 4:
                op=">";
                break;
            case 5:
                op=">=";
                break;
            default:
                return null;
        }

        return "RelExp(" +
                expression1.toString() +
                op + expression2.toString()+
                ")";
    }

    @Override
    public Expression deepCopy() {
        String op = null;

        switch(operator){
            case 0:
                op="<";
                break;
            case 1:
                op="<=";
                break;
            case 2:
                op="==";
                break;
            case 3:
                op="!=";
                break;
            case 4:
                op=">";
                break;
            case 5:
                op=">=";
                break;
            default:
                return null;
        }
        return new RelExp(op.toString(),expression1.deepCopy(),expression2.deepCopy());
    }

    public RelExp(String operate, Expression expression1, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        switch(operate){
            case "<":
                this.operator=0;
                break;
            case "<=":
                this.operator=1;
                break;
            case "==":
                this.operator=2;
                break;
            case "!=":
                this.operator=3;
                break;
            case ">":
                this.operator=4;
                break;
            case ">=":
                this.operator=5;
                break;
        }
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> tbl, InterfaceHeap heap) throws InterpreterException {
        int number1, number2;
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

        switch(operator){
            case 0:
                return new BoolValue(number1<number2);
            case 1:
                return new BoolValue(number1<=number2);
            case 2:
                return new BoolValue(number1==number2);
            case 3:
                return new BoolValue(number1!=number2);
            case 4:
                return new BoolValue(number1>number2);
            case 5:
                return new BoolValue(number1>=number2);
            default:
                return null;
        }
    }
}
