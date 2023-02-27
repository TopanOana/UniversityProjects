package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.PrgState;
import Model.Types.Type;

public class ReturnStmt implements IStmt{
    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        prgState.getSymTableStack().pop();
        return null;
    }

    @Override
    public String toString() {
        return "return";
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
