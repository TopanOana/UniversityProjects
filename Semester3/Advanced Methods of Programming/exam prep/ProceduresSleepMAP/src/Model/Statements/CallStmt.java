package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.DictionaryClass;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceTable;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;
import javafx.util.Pair;

import java.util.ArrayList;

public class CallStmt implements IStmt{
    String procName;
    ArrayList<Expression> expressions;

    @Override
    public String toString() {
        return "call "+procName+" ("+expressions.toString()+")";
    }

    public CallStmt(String procName, ArrayList<Expression> expressions) {
        this.procName = procName;
        this.expressions = expressions;
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceTable<String, Pair<ArrayList<String>, IStmt>> procTable = prgState.getProcTable();
        InterfaceDictionary<String, Value> symTable = prgState.getSymTable();

        if (procTable.lookUp(procName)==null)
            throw new InterpreterException("n-am gasit porcul");

        Pair<ArrayList<String>,IStmt> pair = procTable.lookUp(procName);
        InterfaceDictionary<String, Value> newSymTable = new DictionaryClass<>();

        for (int i=0;i< expressions.size();i++){
            Value val = expressions.get(i).eval(symTable, prgState.getHeap());
            newSymTable.add(pair.getKey().get(i),val);
        }
        prgState.getSymTableStack().push(newSymTable);

        prgState.getStack().push(new ReturnStmt());
        prgState.getStack().push(pair.getValue());
        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
