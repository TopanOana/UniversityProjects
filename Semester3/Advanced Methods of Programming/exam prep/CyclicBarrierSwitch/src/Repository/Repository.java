package Repository;

import Exceptions.InterpreterException;
import Model.PrgState;
import Model.Statements.IStmt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements InterfaceRepository{

    public List<PrgState> getPrgList() {
        return prgStateArrayList;
    }

    public void setPrgList(List<PrgState> prgStateArrayList) {
        this.prgStateArrayList = prgStateArrayList;
    }

    List<PrgState> prgStateArrayList;
    String logFilePath;

    public Repository(ArrayList<PrgState> prgStateArrayList, String logFilePath) {
        this.prgStateArrayList = prgStateArrayList;
        this.logFilePath = logFilePath;
    }

    public Repository(String logFilePath){
        this.prgStateArrayList = new ArrayList<>();
        this.logFilePath=logFilePath;
    }


    @Override
    public void pushPrg(PrgState prgState) {
        prgStateArrayList.clear();
        prgStateArrayList.add(prgState);
    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws InterpreterException {
        try{

            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
            logFile.print(prgState.toStringFile());
            logFile.close();

        }
        catch (IOException ie){
            throw new InterpreterException("Error creating file");
        }
    }
}
