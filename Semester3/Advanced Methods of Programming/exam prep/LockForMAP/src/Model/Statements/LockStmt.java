package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceStack;
import Model.ADT.InterfaceTable;
import Model.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class LockStmt implements IStmt{

    String varName;

    @Override
    public String toString() {
        return "lock(" + varName +")";
    }

    public LockStmt(String varName) {
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary<String, Value> symTable = prgState.getSymTable();
        InterfaceTable lockTable = prgState.getLockTable();
        InterfaceStack<IStmt> exeStack = prgState.getStack();

        if(symTable.lookUp(varName)==null)
            throw new InterpreterException("variable isn't in symTable");
        if (!(symTable.lookUp(varName) instanceof IntValue))
            throw new InterpreterException("variable isn't of int value");
        IntValue foundIndex = (IntValue) symTable.lookUp(varName);

        if (!lockTable.getTable().containsKey(foundIndex.getValue()))
            throw new InterpreterException("variable isn't in lockTable");

        if (lockTable.get(foundIndex.getValue())==-1)
            lockTable.update(foundIndex.getValue(), prgState.getId_thread());
        else
            exeStack.push(new LockStmt(varName));

        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if (typeEnv.getDictionary().containsKey(varName) && !typeEnv.lookUp(varName).equals(new StringType()))
            throw new InterpreterException("Crapa la typecheck LockStmt");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
