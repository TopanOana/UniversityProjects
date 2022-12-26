package Model.Statements;

import Exceptions.InterpreterException;
import Model.PrgState;

public interface IStmt {
    PrgState execute(PrgState prgState) throws InterpreterException;

    IStmt deepCopy();
    String toString();

}
