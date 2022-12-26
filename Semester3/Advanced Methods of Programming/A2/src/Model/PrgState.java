package Model;

import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceList;
import Model.ADT.InterfaceStack;
import Model.Statements.IStmt;
import Model.Values.Value;

public class PrgState {

    private InterfaceStack<IStmt> exeStack;
    private InterfaceList<Value> output;
    private InterfaceDictionary<String, Value> symTable;

    //private IStmt originalProgram;

    @Override
    public String toString() {
        return "PrgState\n" +
                "exeStack=" + exeStack + "\n"+
                " output=" + output + "\n"+
                " symTable=" + symTable ;
    }

    public InterfaceStack<IStmt> getStack(){
        return exeStack;
    }

    public InterfaceList<Value> getOutput(){
        return output;
    }

    public InterfaceDictionary<String, Value> getSymTable(){
        return symTable;
    }

    public PrgState(InterfaceStack<IStmt> exeStack, InterfaceList<Value> output, InterfaceDictionary<String, Value> symTable) {
        this.exeStack = exeStack;
        this.output = output;
        this.symTable = symTable;
        //this.originalProgram = originalProgram.clone();
    }




}
