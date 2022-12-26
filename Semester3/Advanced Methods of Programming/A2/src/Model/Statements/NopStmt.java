package Model.Statements;

import Exceptions.InterpreterException;
import Model.PrgState;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        return prgState;
    }

    public NopStmt() {
    }

    @Override
    public String toString(){
        return "";

    }
}
