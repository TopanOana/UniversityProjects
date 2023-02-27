package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceList;
import Model.ADT.InterfaceTable;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class CountDownStmt implements IStmt{
    String varName;

    @Override
    public String toString() {
        return "countDown("+varName+")";
    }

    public CountDownStmt(String varName) {
        this.varName = varName;
    }


    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary<String, Value> symTable = prgState.getSymTable();
        InterfaceTable<Integer,Integer> latchTable = prgState.getLatchTable();
        InterfaceList<Value> output = prgState.getOutput();

        Value val = symTable.lookUp(varName);
        if(val==null)
            throw new InterpreterException("sometimes you win some, sometimes you lose the goddamn value");

        if(!val.getType().equals(new IntType()))
            throw new InterpreterException("my math teacher would be disappointed that you don't know what an integer is");

        IntValue foundIndex = (IntValue) val;

        if(latchTable.getTable().containsKey(foundIndex.getValue())){
            if (latchTable.get(foundIndex.getValue())>0) {
                latchTable.update(foundIndex.getValue(), latchTable.get(foundIndex.getValue()) - 1);
                output.add(new IntValue(prgState.getId_thread()));
            }

        }

        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if(!typeEnv.lookUp(varName).equals(new IntType()))
            throw new InterpreterException("int should be here for you, you idiot sandwich");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
