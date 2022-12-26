package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.Types.RefType;
import Model.Types.Type;
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
    public Type typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        Type type = expression.typeCheck(typeEnv);
        if (type instanceof RefType){
            RefType refType = (RefType) type;
            return refType.getInner();
        }
        else
            throw new InterpreterException("the readHeap is not a RefType");
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
