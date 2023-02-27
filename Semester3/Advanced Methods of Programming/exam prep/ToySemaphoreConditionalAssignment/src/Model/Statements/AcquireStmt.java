package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.ADT.InterfaceTable;
import Model.ADT.Triple;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.ArrayList;

public class AcquireStmt implements IStmt{
    String varName;

    @Override
    public String toString() {
        return "acquire("+varName+")";
    }

    public AcquireStmt(String varName) {
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary<String, Value> symTable = prgState.getSymTable();
        InterfaceTable<Integer, Triple<Integer, ArrayList<Integer>, Integer>> semaphoreTable = prgState.getSemaphoreTable();
        Value val = symTable.lookUp(varName);

        if(val==null)
            throw new InterpreterException("can't find the variable, just like your taste in people");
        if(!val.getType().equals(new IntType()))
            throw new InterpreterException("not an int, just the way you aren't a smart person");
        Integer foundIndex = ((IntValue) val).getValue();

        if(!semaphoreTable.containsKey(foundIndex))
            throw new InterpreterException("the semaphore is red, i haven't found the semaphore idiot");

        Triple<Integer, ArrayList<Integer>, Integer> triple = semaphoreTable.get(foundIndex);
        if (triple.getFirst()- triple.getThird()>triple.getSecond().size()) {
            if (!triple.getSecond().contains(prgState.getId_thread()))
                triple.getSecond().add(prgState.getId_thread());
        }
        else
            prgState.getStack().push(new AcquireStmt(varName));

        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if (!typeEnv.lookUp(varName).equals(new IntType()))
            throw new InterpreterException("you need to acquire a taste for ints");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
