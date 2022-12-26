package Model.Statements;

import Exceptions.InterpreterException;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt{
    Expression expression;

    public CloseRFile(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "closeRFile(" + expression.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        Value value = expression.eval(prgState.getSymTable());
        if (!value.getType().equals(new StringType())){
            throw new InterpreterException("value not a string.");
        }
        StringValue stringValue = (StringValue) value;

        try {
            BufferedReader bufferedReader = prgState.getFileTable().lookUp(stringValue);
            bufferedReader.close();
            prgState.getFileTable().remove(stringValue);
        }catch (InterpreterException ie){
            throw new InterpreterException("file doesn't exist in file table.");
        }
        catch (IOException io){
            throw new InterpreterException("io error closing file.");
        }
        return prgState;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(expression.deepCopy());
    }
}
