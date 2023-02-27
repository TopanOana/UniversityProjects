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

public class NewLatchStmt implements IStmt{
    String varName;
    Expression exp;


    public NewLatchStmt(String varName, Expression exp) {
        this.varName = varName;
        this.exp = exp;
    }


    @Override
    public String toString() {
        return "newLatch("+varName+","+exp.toString()+")";
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary<String, Value> symTable = prgState.getSymTable();
        InterfaceHeap heap = prgState.getHeap();
        InterfaceTable<Integer,Integer> latchTable = prgState.getLatchTable();

        Value val = exp.eval(symTable,heap);
        if(!val.getType().equals(new IntType()))
            throw new InterpreterException("get the type right, int is intportant");

        IntValue intValue = (IntValue) val;

        Integer newFreeLocation = latchTable.add(intValue.getValue());

        if(symTable.lookUp(varName)==null){
            symTable.add(varName, new IntValue(newFreeLocation));
        }
        else{
            symTable.update(varName,new IntValue(newFreeLocation));
        }
        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if (!exp.typeCheck(typeEnv).equals(new IntType()))
            throw new InterpreterException("int ain't your fave, innit?");
        if (typeEnv.lookUp(varName)==null)
            typeEnv.add(varName, new IntType());
        if (!typeEnv.lookUp(varName).equals(new IntType()))
            throw new InterpreterException("look up the word int please and use it more often");
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
