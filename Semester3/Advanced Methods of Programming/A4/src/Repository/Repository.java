package Repository;

import Exceptions.InterpreterException;
import Model.PrgState;
import Model.Statements.IStmt;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Repository implements InterfaceRepository{

    ArrayList<PrgState> prgStateArrayList;
    String logFilePath;

    public Repository(ArrayList<PrgState> prgStateArrayList, String logFilePath) {
        this.prgStateArrayList = prgStateArrayList;
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCurrentProgram() {
        return prgStateArrayList.get(0);
    }

    @Override
    public void pushPrg(PrgState prgState) {
        prgStateArrayList.clear();
        prgStateArrayList.add(prgState);
    }

    @Override
    public void logPrgStateExec() throws InterpreterException {
        try{

            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
            PrgState prgState = prgStateArrayList.get(0);
            logFile.print(prgState.toStringFile());
            logFile.close();

        }
        catch (IOException ie){
            throw new InterpreterException("Error creating file");
        }
    }
}
