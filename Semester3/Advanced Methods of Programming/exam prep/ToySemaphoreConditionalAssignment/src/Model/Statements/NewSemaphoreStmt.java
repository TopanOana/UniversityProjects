package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.ADT.InterfaceTable;
import Model.ADT.Triple;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.ArrayList;

public class NewSemaphoreStmt implements IStmt{
    String varName;
    Expression exp1,exp2;

    @Override
    public String toString() {
        return "newSemaphore("+varName+","+exp1.toString()+","+exp2.toString()+")";
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary<String, Value> symTable = prgState.getSymTable();
        InterfaceHeap heap = prgState.getHeap();
        InterfaceTable<Integer, Triple<Integer, ArrayList<Integer>, Integer>> semaphoreTable = prgState.getSemaphoreTable();

        Value val1 = exp1.eval(symTable,heap);
        if(!val1.getType().equals(new IntType()))
            throw new InterpreterException("poof the int disappeared, like my motivation");
        Value val2 = exp2.eval(symTable,heap);
        if(!val2.getType().equals(new IntType()))
            throw new InterpreterException("abracadabra, your int isn't here, find it with the bunny that should've been in the hat");
        IntValue iv1 = (IntValue) val1;
        IntValue iv2 = (IntValue) val2;

        Integer newFreeLocation = semaphoreTable.add(new Triple<>(iv1.getValue(), new ArrayList<>(), iv2.getValue()));

        if (symTable.lookUp(varName)==null)
            throw new InterpreterException("the variable isn't in the soup you dipshit");
        symTable.update(varName,new IntValue(newFreeLocation));

        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if (typeEnv.lookUp(varName)==null)
            throw new InterpreterException("look for your variable cause i can't fucking find it");
        if (!typeEnv.lookUp(varName).equals(new IntType()))
            throw new InterpreterException("bubble bubble where's the int in the bubbles?");
        if (!exp1.typeCheck(typeEnv).equals(new IntType()))
            throw new InterpreterException("int you sure you forgot an int?");
        if(!exp2.typeCheck(typeEnv).equals(new IntType()))
            throw new InterpreterException("int this, int that, you forgot the int again");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }

    public NewSemaphoreStmt(String varName, Expression exp1, Expression exp2) {
        this.varName = varName;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
}
