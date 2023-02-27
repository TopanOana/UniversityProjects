package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.Expressions.Expression;
import Model.Expressions.RelExp;
import Model.PrgState;
import Model.Types.Type;

public class SwitchStmt implements IStmt {

    Expression exp, exp1, exp2;
    IStmt stmt1, stmt2, stmt3;

    @Override
    public String toString() {
        return "switch("+exp.toString()+") (case "+exp1.toString()+": "+stmt1.toString()+") (case "+exp2.toString()+": "+stmt2.toString()+") (default: "+stmt3.toString();
    }

    public SwitchStmt(Expression exp, Expression exp1, IStmt stmt1, Expression exp2, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        IStmt toPush = new IfStmt(new RelExp("==",exp,exp1),stmt1, new IfStmt(new RelExp("==", exp, exp2), stmt2,stmt3));
        prgState.getStack().push(toPush);
        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if (!exp.typeCheck(typeEnv).equals(exp1.typeCheck(typeEnv)))
            throw new InterpreterException("nintendo switch the fucking types");
        if (!exp.typeCheck(typeEnv).equals(exp2.typeCheck(typeEnv)))
            throw new InterpreterException("the old switcheroo of types dude!!");
        return stmt3.typeCheck(stmt2.typeCheck(stmt1.typeCheck(typeEnv)));
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
