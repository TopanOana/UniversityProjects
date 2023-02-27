package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceStack;
import Model.PrgState;
import Model.Types.Type;

public class CompStmt implements IStmt{
    IStmt first;
    IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(),second.deepCopy());
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }

    @Override
    public String toString() {
        return "(" + first.toString() +
                ";" + second.toString() +
                ")";
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceStack<IStmt> stack = prgState.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }
}
