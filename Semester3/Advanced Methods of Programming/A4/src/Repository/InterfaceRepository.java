package Repository;

import Exceptions.InterpreterException;
import Model.PrgState;
import Model.Statements.IStmt;

public interface InterfaceRepository {
    PrgState getCurrentProgram();

    void pushPrg(PrgState prg);

    void logPrgStateExec() throws InterpreterException;
}
