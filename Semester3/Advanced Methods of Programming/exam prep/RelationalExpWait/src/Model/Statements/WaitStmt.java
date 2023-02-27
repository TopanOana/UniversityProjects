package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.Expressions.ValueExp;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.IntValue;

public class WaitStmt implements IStmt{

    IntValue number;

    public WaitStmt(IntValue number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        if (number.getValue()>0)
        {
            prgState.getStack().push(new CompStmt(new PrintStmt(new ValueExp(number)), new WaitStmt(new IntValue(number.getValue()-1))));
        }
        return null;
    }

    @Override
    public String toString() {
        return "wait("+number.getValue()+")";
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new WaitStmt(new IntValue(number.getValue()));
    }
}
