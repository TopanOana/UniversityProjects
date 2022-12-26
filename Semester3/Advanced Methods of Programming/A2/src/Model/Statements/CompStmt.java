package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceStack;
import Model.PrgState;

public class CompStmt implements IStmt{
    IStmt first;
    IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
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
        return prgState;
    }
}
