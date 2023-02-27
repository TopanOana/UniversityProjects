package Repository;

import Exceptions.InterpreterException;
import Model.PrgState;
import Model.Statements.IStmt;

import java.util.List;

public interface InterfaceRepository {


    List<PrgState> getPrgList();

    void setPrgList(List<PrgState> prgList);
    void pushPrg(PrgState prg);

    void logPrgStateExec(PrgState prgState) throws InterpreterException;
}
