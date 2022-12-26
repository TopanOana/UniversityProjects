package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceStack;
import Model.ADT.StackClass;
import Model.PrgState;

public class ForkStmt implements IStmt{
    IStmt statement;

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceStack<IStmt> newExeStack = new StackClass<>();
        newExeStack.push(statement);
        return new PrgState(newExeStack,prgState.getOutput(), prgState.getSymTable().deepCopy(), prgState.getFileTable(), prgState.getHeap());
    }

    @Override
    public String toString() {
        return "Fork(" + statement.toString() +")";
    }

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(statement.deepCopy());
    }
}
