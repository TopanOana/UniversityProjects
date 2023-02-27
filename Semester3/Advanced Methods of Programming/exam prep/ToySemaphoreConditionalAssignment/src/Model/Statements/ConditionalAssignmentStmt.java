package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.Type;

public class ConditionalAssignmentStmt implements IStmt{
    String varName;
    Expression exp1, exp2, exp3;

    public ConditionalAssignmentStmt(String varName, Expression exp1, Expression exp2, Expression exp3) {
        this.varName = varName;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public String toString() {
        return varName+"=("+exp1.toString()+")?"+exp2.toString()+":"+exp3.toString();
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        IStmt toPush = new IfStmt(exp1, new AssignStmt(varName,exp2), new AssignStmt(varName,exp3));
        prgState.getStack().push(toPush);

        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        //todo
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
