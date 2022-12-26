package Model;

import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceList;
import Model.ADT.InterfaceStack;
import Model.Statements.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.util.*;

public class PrgState {

    private InterfaceStack<IStmt> exeStack;
    private InterfaceList<Value> output;
    private InterfaceDictionary<String, Value> symTable;


    private IStmt originalProgram;
    private InterfaceDictionary<StringValue, BufferedReader> fileTable;

    @Override
    public String toString() {
        return "PrgState\n" +
                "exeStack=" + exeStack + "\n"+
                " output=" + output + "\n"+
                " symTable=" + symTable ;
    }

    public InterfaceDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
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

    public PrgState(InterfaceStack<IStmt> exeStack, InterfaceList<Value> output, InterfaceDictionary<String, Value> symTable, InterfaceDictionary<StringValue,BufferedReader> fileTable) {
        this.exeStack = exeStack;
        this.output = output;
        this.symTable = symTable;
        this.fileTable = fileTable;
        //this.originalProgram = originalProgram.deepCopy();
    }

    public String toStringFile(){
        StringBuilder stringBuilder = new StringBuilder("ExeStack:\n");

        Stack<IStmt> auxStack = new Stack<>();
        while(!exeStack.isEmpty()){
            IStmt currentStatement = exeStack.pop();
            stringBuilder.append(currentStatement.toString()).append("\n");
            auxStack.push(currentStatement);
        }

        while(!auxStack.isEmpty()){
            IStmt currentStatement = auxStack.pop();
            exeStack.push(currentStatement);
        }

        stringBuilder.append("SymTable:\n");
        Map<String,Value> auxDict = symTable.getDictionary();
        Iterator<Map.Entry<String,Value>> iterator = auxDict.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Value> stringValueEntry = iterator.next();
            String toAdd = stringValueEntry.getKey().toString() +" --> "+stringValueEntry.getValue().toString()+"\n";
            stringBuilder.append(toAdd);
        }

        stringBuilder.append("Out:\n");
        ArrayList<Value> auxList = (ArrayList<Value>) output.getList();
        for(Value value:auxList){
            String toAdd = value.toString()+"\n";
            stringBuilder.append(toAdd);
        }

        stringBuilder.append("FileTable:\n");
        Set<StringValue> arrayList  = fileTable.getDictionary().keySet();
        for (StringValue s : arrayList){
            String toAdd = s.getValue() + "\n";
            stringBuilder.append(toAdd);
        }
        return stringBuilder.toString();
    }


}
