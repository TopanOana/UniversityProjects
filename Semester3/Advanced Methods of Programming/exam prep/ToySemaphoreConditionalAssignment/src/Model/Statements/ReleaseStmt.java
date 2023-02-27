package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceTable;
import Model.ADT.Triple;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.ArrayList;

public class ReleaseStmt implements IStmt{
    String varName;

    @Override
    public String toString() {
        return "release("+varName+")";
    }

    public ReleaseStmt(String varName) {
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary<String, Value> symTable = prgState.getSymTable();
        InterfaceTable<Integer, Triple<Integer, ArrayList<Integer>, Integer>> semaphoreTable = prgState.getSemaphoreTable();
        Value val = symTable.lookUp(varName);

        if(val==null)
            throw new InterpreterException("no bueno dude, try again later");
        if(!val.getType().equals(new IntType()))
            throw new InterpreterException("my man please stop it");
        Integer foundIndex = ((IntValue) val).getValue();

        if(!semaphoreTable.containsKey(foundIndex))
            throw new InterpreterException("i'm damn tired of these messages, get it right");

        Triple<Integer, ArrayList<Integer>, Integer> triple = semaphoreTable.get(foundIndex);
        if (triple.getSecond().contains(prgState.getId_thread()))
            triple.getSecond().remove(triple.getSecond().indexOf(prgState.getId_thread()));

        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if (!typeEnv.lookUp(varName).equals(new IntType()))
            throw new InterpreterException("release me from this cage of existence");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
