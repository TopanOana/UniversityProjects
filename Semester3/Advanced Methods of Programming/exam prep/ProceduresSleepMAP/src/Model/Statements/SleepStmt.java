package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.PrgState;
import Model.Types.Type;

public class SleepStmt implements IStmt{

    int number;

    @Override
    public String toString() {
        return "sleep("+number+")";
    }

    public SleepStmt(int number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {

        if (number!=0){
            prgState.getStack().push(new SleepStmt(number-1));
        }
        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new SleepStmt(number);
    }
}
