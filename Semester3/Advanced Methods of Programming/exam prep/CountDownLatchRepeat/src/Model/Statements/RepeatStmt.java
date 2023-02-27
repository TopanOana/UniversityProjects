package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.Expressions.*;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;

public class RepeatStmt implements IStmt{
    IStmt stmt;
    Expression exp;

    @Override
    public String toString() {
        return "repeat "+stmt.toString()+" until "+exp.toString();
    }

    public RepeatStmt(IStmt stmt, Expression exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        IStmt toPush = new CompStmt(stmt, new WhileStmt(new NegExp(exp), stmt));
        prgState.getStack().push(toPush);
        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if(!exp.typeCheck(typeEnv).equals(new BoolType()))
            throw new InterpreterException("you ain't bool'ing me!");
        return stmt.typeCheck(typeEnv);
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
