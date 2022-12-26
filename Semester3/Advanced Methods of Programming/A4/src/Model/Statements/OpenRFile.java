package Model.Statements;

import Exceptions.InterpreterException;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStmt{

    Expression expression;

    public OpenRFile(Expression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        Value expressionValue = expression.eval(prgState.getSymTable(), prgState.getHeap());
        if(!expressionValue.getType().equals(new StringType())){
            throw new InterpreterException("Expression is not a string.");
        }
        StringValue stringValue = (StringValue) expressionValue;
        try {
            prgState.getFileTable().lookUp(stringValue);
            throw new InterpreterException("String already exists in the fileTable.");
        }catch(InterpreterException ie){

        }
        String value = stringValue.getValue();
        BufferedReader bufferedReader;
        try{
            bufferedReader = new BufferedReader(new FileReader(value));
        } catch (IOException ie) {
            throw new InterpreterException("Error occurred with file.");
        }
        prgState.getFileTable().add(stringValue,bufferedReader);
        return prgState;
    }

    @Override
    public String toString() {
        return "openRFile(" + expression.toString() +")";
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFile(expression.deepCopy());
    }
}
