package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceList;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Values.Value;

public class PrintStmt implements IStmt{
    Expression expression;

    public PrintStmt(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString(){
        return "print("+expression.toString()+")";
    }
    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceList<Value> list = prgState.getOutput();
        list.add(expression.eval(prgState.getSymTable()));
        return prgState;
    }
}
