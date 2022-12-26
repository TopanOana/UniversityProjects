package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceStack;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStmt implements IStmt{
    Expression expression;
    IStmt thenStatement;
    IStmt elseStatement;
    @Override
    public String toString() {
        return "IF(" + expression.toString()+") THEN (" +thenStatement.toString() + ") ELSE ("+elseStatement.toString()+"))";
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(expression.deepCopy(),thenStatement.deepCopy(),elseStatement.deepCopy());
    }

    public IfStmt(Expression expression, IStmt thenStatement, IStmt elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary<String,Value> dict = prgState.getSymTable();
        InterfaceStack<IStmt> exeStack = prgState.getStack();
        Value condition = expression.eval(dict, prgState.getHeap());
        if (!condition.getType().equals(new BoolType())){
            throw new InterpreterException("Conditional expression is not a boolean.");
        }
        else{
            BoolValue boolValue = (BoolValue) condition;
            if ( boolValue.getValue().equals(true))
            {
                exeStack.push(thenStatement);
            }
            else{
                exeStack.push(elseStatement);
            }
        }
        return null;
    }
}
