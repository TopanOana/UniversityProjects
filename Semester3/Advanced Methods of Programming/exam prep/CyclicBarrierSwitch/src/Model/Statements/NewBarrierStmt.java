package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.ADT.InterfaceTable;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.util.ArrayList;

public class NewBarrierStmt implements IStmt{
    String varName;
    Expression exp;

    public NewBarrierStmt(String varName, Expression exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "newBarrier("+varName+","+exp.toString()+")";
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary<String, Value> symTable = prgState.getSymTable();
        InterfaceHeap heap = prgState.getHeap();

        Value val = exp.eval(symTable,heap);
        if(!val.getType().equals(new IntType()))
            throw new InterpreterException("not the right type my dude");
        IntValue number = (IntValue) val;

        int newFreeLocation = prgState.getBarrierTable().add(new Pair<>(number.getValue(), new ArrayList<>()));

        if(symTable.lookUp(varName)!=null){
            prgState.getSymTable().update(varName,new IntValue(newFreeLocation));
        }
        else{
            prgState.getSymTable().add(varName,new IntValue(newFreeLocation));
        }


        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if (!exp.typeCheck(typeEnv).equals(new IntType()))
            throw new InterpreterException("ra ra rasputin nu merge");
        if(typeEnv.lookUp(varName)==null)
            typeEnv.add(varName, new IntType());
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
