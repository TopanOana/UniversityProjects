package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.PrgState;
import Model.Types.Type;

public interface IStmt {
    PrgState execute(PrgState prgState) throws InterpreterException;

    InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException;
    IStmt deepCopy();
    String toString();

}
