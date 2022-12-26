package Repository;

import Model.PrgState;
import Model.Statements.IStmt;

public interface InterfaceRepository {
    PrgState getCurrentProgram();

    void pushPrg(PrgState prg);
}
