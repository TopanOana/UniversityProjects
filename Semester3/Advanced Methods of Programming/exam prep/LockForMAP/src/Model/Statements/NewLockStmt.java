package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceTable;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;

public class NewLockStmt implements IStmt{
    String varName;

    public NewLockStmt(String varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return "newLock(" + varName +")";
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary symTable = prgState.getSymTable();
        InterfaceTable lockTable = prgState.getLockTable();
        Integer newFreeLocation = lockTable.add(-1);
        if (symTable.lookUp(varName) == null){
            symTable.add(varName, new IntValue(newFreeLocation));
        }
        else{
            symTable.update(varName, new IntValue(newFreeLocation));
        }
        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if(typeEnv.lookUp(varName)==null)
            typeEnv.add(varName, new IntType());
        if (!typeEnv.lookUp(varName).equals(new IntType()))
            throw new InterpreterException("locking up this variable, straight to jail");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
