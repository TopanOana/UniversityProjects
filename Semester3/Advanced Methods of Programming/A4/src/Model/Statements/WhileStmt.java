package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceStack;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

public class WhileStmt implements IStmt {
    IStmt inside;
    Expression expression;

    public WhileStmt(Expression expression, IStmt inside) {
        this.inside = inside;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "(while(" + expression.toString()+") " + inside.toString()+")";
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        Value val = expression.eval(prgState.getSymTable(), prgState.getHeap());
        if (! val.getType().equals(new BoolType())){
            throw new InterpreterException("expression not a boolean");
        }

        BoolValue boolValue = (BoolValue) val;
        if (boolValue.getValue()){
            prgState.getStack().push(this);
            prgState.getStack().push(inside);
        }
        return prgState;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(expression.deepCopy(),inside.deepCopy());
    }
}
