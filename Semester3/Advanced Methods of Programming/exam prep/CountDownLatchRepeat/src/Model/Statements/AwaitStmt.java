package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceTable;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class AwaitStmt implements IStmt{
    String varName;

    @Override
    public String toString() {
        return "await("+varName+")";
    }

    public AwaitStmt(String varName) {
        this.varName = varName;
    }


    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary<String, Value> symTable = prgState.getSymTable();
        InterfaceTable<Integer,Integer> latchTable = prgState.getLatchTable();

        Value val = symTable.lookUp(varName);
        if(val==null)
            throw new InterpreterException("your brain is a null pointer, fuck off");

        if(!val.getType().equals(new IntType()))
            throw new InterpreterException("your type can't be described as an int, it's just shit");

        IntValue foundIndex = (IntValue) val;

        if (!latchTable.getTable().containsKey(foundIndex.getValue()))
            throw new InterpreterException("the index can't be found, just like your will to live");

        if(latchTable.get(foundIndex.getValue())!=0){
            prgState.getStack().push(new AwaitStmt(varName));
        }


        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if(!typeEnv.lookUp(varName).equals(new IntType()))
            throw new InterpreterException("integers should be your favourite but you're irrational");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
