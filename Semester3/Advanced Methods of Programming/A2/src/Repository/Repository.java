package Repository;

import Model.PrgState;
import Model.Statements.IStmt;

import java.util.ArrayList;

public class Repository implements InterfaceRepository{

    ArrayList<PrgState> prgStateArrayList;

    public Repository(ArrayList<PrgState> prgStateArrayList) {
        this.prgStateArrayList = prgStateArrayList;
    }

    @Override
    public PrgState getCurrentProgram() {
        return prgStateArrayList.get(prgStateArrayList.size()-1);
    }

    @Override
    public void pushPrg(PrgState prgState) {
        prgStateArrayList.add(prgState);
    }
}
