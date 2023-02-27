package Model;

import Exceptions.InterpreterException;
import Model.ADT.*;
import Model.Expressions.ArithExp;
import Model.Expressions.ValueExp;
import Model.Expressions.VarExp;
import Model.Statements.*;
import Model.Types.IntType;
import Model.Values.StringValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.*;

public class PrgState {

    private InterfaceStack<IStmt> exeStack;
    private InterfaceList<Value> output;
    private InterfaceStack<InterfaceDictionary<String, Value>> symTableStack;

    private InterfaceTable<String, Pair<ArrayList<String>,IStmt>> procTable;

    private InterfaceHeap heap;
    private int id_thread;

    public static int id=0;

    public int getId_thread() {
        return id_thread;
    }

    public void setId_thread(int id_thread) {

        this.id_thread = id_thread;
    }

    public InterfaceTable<String, Pair<ArrayList<String>, IStmt>> getProcTable() {
        return procTable;
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
        this.symTableStack = new StackClass<>();
        this.symTableStack.push(new DictionaryClass<>());
        this.fileTable = new DictionaryClass<>();
        this.heap = new HeapClass();
        this.setId_thread(id);
        this.changeId();
        this.procTable = new TableClass<>();
        initProcTable();
    }

    public PrgState(InterfaceStack<IStmt> exeStack, InterfaceList<Value> output, InterfaceStack<InterfaceDictionary<String, Value>> symTableStack, InterfaceHeap heap, InterfaceDictionary<StringValue, BufferedReader> fileTable) {
        this.exeStack = exeStack;
        this.output = output;
        this.symTableStack = symTableStack;
        this.heap = heap;
        this.fileTable = fileTable;
        this.setId_thread(id);
        this.changeId();
        this.procTable = new TableClass<>();
        initProcTable();
    }

    public InterfaceStack<InterfaceDictionary<String, Value>> getSymTableStack() {
        return symTableStack;
    }

    @Override
    public String toString() {
        return "PrgState id :" + id + "\n" +
                "exeStack=" + exeStack + "\n"+
                " output=" + output + "\n"+
                " symTable=" + symTableStack + "\n"+
                " heap = " + heap;
    }

    private void initProcTable(){
        //Pair<ArrayList<String>,IStmt>
        ArrayList<String> arr =new ArrayList<>();
        arr.add("a"); arr.add("b");
        IStmt sum = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(
                new AssignStmt("v", new ArithExp("+", new VarExp("a"), new VarExp("b"))), new PrintStmt(new VarExp("v"))
        ));
        Pair<ArrayList<String>, IStmt> pair = new Pair<>(arr,sum);
        procTable.add("sum", pair);

        IStmt product = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(
                new AssignStmt("v", new ArithExp("*", new VarExp("a"), new VarExp("b"))), new PrintStmt(new VarExp("v"))
        ));
        Pair<ArrayList<String>, IStmt> pair2 = new Pair<>(arr,product);
        procTable.add("product", pair2);

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
        return symTableStack.top();
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

    public PrgState(InterfaceStack<IStmt> exeStack, InterfaceList<Value> output, InterfaceDictionary<String, Value> symTable, InterfaceDictionary<StringValue,BufferedReader> fileTable, InterfaceHeap heap) {
        this.exeStack = exeStack;
        this.output = output;
        this.symTableStack = new StackClass<>();
        this.symTableStack.push(symTable);
        this.fileTable = fileTable;
        this.heap = heap;
        //this.originalProgram = originalProgram.deepCopy();
        this.setId_thread(id);
        this.changeId();
        this.procTable = new TableClass<>();
        initProcTable();
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
        Map<String,Value> auxDict = symTableStack.top().getDictionary();
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
