package Model;

import Exceptions.InterpreterException;
import Model.ADT.*;
import Model.Statements.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.*;

public class PrgState {

    private InterfaceStack<IStmt> exeStack;
    private InterfaceList<Value> output;
    private InterfaceDictionary<String, Value> symTable;

    private InterfaceHeap heap;
    private int id_thread;

    public static int id=0;

    private InterfaceTable<Integer, Pair<Integer,ArrayList<Integer>>> barrierTable;

    public int getId_thread() {
        return id_thread;
    }

    public void setId_thread(int id_thread) {

        this.id_thread = id_thread;
    }

    synchronized void changeId(){
        id = id+1;
    }

    private IStmt originalProgram;
    private InterfaceDictionary<StringValue, BufferedReader> fileTable;

    public PrgState(IStmt initialProgram){
        originalProgram=initialProgram;
        this.exeStack = new StackClass<>();
        exeStack.push(originalProgram);
        this.output = new ListClass<>();
        this.symTable = new DictionaryClass<>();
        this.fileTable = new DictionaryClass<>();
        this.heap = new HeapClass();
        this.barrierTable = new TableClass();
        this.setId_thread(id);
        this.changeId();
    }

    @Override
    public String toString() {
        return "PrgState id :" + id + "\n" +
                "exeStack=" + exeStack + "\n"+
                " output=" + output + "\n"+
                " symTable=" + symTable + "\n"+
                " heap = " + heap;
    }

    public InterfaceTable<Integer, Pair<Integer, ArrayList<Integer>>> getBarrierTable() {
        return barrierTable;
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

    public InterfaceHeap getHeap() {
        return heap;
    }

    public Boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws InterpreterException {
        if (exeStack.isEmpty())
            throw new InterpreterException("prgstate stack is empty");
        IStmt currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public PrgState(InterfaceStack<IStmt> exeStack, InterfaceList<Value> output, InterfaceDictionary<String, Value> symTable, InterfaceDictionary<StringValue,BufferedReader> fileTable, InterfaceHeap heap, InterfaceTable<Integer,Pair<Integer,ArrayList<Integer>>> barrierTable) {
        this.exeStack = exeStack;
        this.output = output;
        this.symTable = symTable;
        this.fileTable = fileTable;
        this.heap = heap;
        this.barrierTable = barrierTable;
        //this.originalProgram = originalProgram.deepCopy();
        this.setId_thread(id);
        this.changeId();
    }

    public String toStringFile(){
        StringBuilder stringBuilder = new StringBuilder("PrgState id: " + id + "\nExeStack:\n");

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

        stringBuilder.append("Heap:\n");
        Map heapString = heap.getHeap();
        heapString.forEach((key, value) -> {
            String toAdd = key.toString() + "->" + value.toString()+"\n";
            stringBuilder.append(toAdd);
        });
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }


}
