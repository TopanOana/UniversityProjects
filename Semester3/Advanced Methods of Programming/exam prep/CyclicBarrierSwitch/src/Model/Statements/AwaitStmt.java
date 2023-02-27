package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.util.ArrayList;

public class AwaitStmt implements IStmt{
    String varName;

    public AwaitStmt(String varName) {
        this.varName = varName;
    }


    @Override
    public String toString() {
        return "await("+varName+")";
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {

        Value foundIndex = prgState.getSymTable().lookUp(varName);
        if(foundIndex==null){
            throw new InterpreterException("not in sims 4 today");
        }
        IntValue value = (IntValue) foundIndex;

        if(!prgState.getBarrierTable().getTable().containsKey(value.getValue()))
            throw new InterpreterException("the barrier is the error you made");

        Pair<Integer, ArrayList<Integer>> pair = prgState.getBarrierTable().get(value.getValue());

        if(pair.getKey()>pair.getValue().size()){
            if(pair.getValue().contains(prgState.getId_thread()))
                prgState.getStack().push(new AwaitStmt(varName));
            else{
                pair.getValue().add(prgState.getId_thread());
                prgState.getStack().push(new AwaitStmt(varName));
            }
        }
        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if (!typeEnv.lookUp(varName).equals(new IntType()))
            throw new InterpreterException("russia's greatest error my dudes");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
