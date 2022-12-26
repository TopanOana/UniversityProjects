package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.Values.RefValue;
import Model.Values.Value;

public class ReadHeapExp implements Expression{
    Expression expression;

    public ReadHeapExp(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Value eval(InterfaceDictionary<String, Value> tbl, InterfaceHeap heap) throws InterpreterException {
        Value evalExpression = expression.eval(tbl,heap);
        if(! (evalExpression instanceof RefValue)){
            throw new InterpreterException("expression not of RefValue");
        }
        Integer address = ((RefValue) evalExpression).getAddr();
        return heap.get(address);
    }

    @Override
    public String toString() {
        return "ReadHeapExp(" +
                expression.toString() +
                ')';
    }

    @Override
    public Expression deepCopy() {
        return new ReadHeapExp(expression.deepCopy());
    }
}
