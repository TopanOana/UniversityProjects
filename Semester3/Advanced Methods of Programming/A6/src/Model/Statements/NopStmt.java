package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.PrgState;
import Model.Types.Type;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        return null;
    }

    public NopStmt() {
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public String toString(){
        return "";

    }
}
