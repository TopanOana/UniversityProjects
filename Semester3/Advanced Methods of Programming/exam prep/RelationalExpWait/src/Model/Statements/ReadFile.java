package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt{
    Expression expression;
    String var_name;

    public ReadFile(Expression expression, String var_name) {
        this.expression = expression;
        this.var_name = var_name;
    }



    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        if(expression.typeCheck(typeEnv).equals(new StringType())){
            if(typeEnv.lookUp(var_name).equals(new IntType()))
                return typeEnv;
            else
                throw new InterpreterException("ReadFile needs an int as a parameter");
        }
        else
            throw new InterpreterException("Expression not of StringType");
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary<String, Value> table = prgState.getSymTable();
        try{
            if(!table.lookUp(var_name).getType().equals(new IntType())){
                throw new InterpreterException("type of variable isn't int.");
            }
        }catch (InterpreterException interpreterException){
            throw new InterpreterException("variable isn't declared.");
        }
        Value expressionValue = expression.eval(prgState.getSymTable(), prgState.getHeap());
        if(!expressionValue.getType().equals(new StringType())){
            throw new InterpreterException("Expression is not a string.");
        }

        StringValue stringValue = (StringValue) expressionValue;
        BufferedReader bufferedReader;
        try {
            bufferedReader = prgState.getFileTable().lookUp(stringValue);

        }catch(InterpreterException ie){
            throw new InterpreterException("String doesn't exist in the fileTable.");
        }
        try {
            String line = bufferedReader.readLine().toString();
            if(line.isEmpty())
                line="0";
            IntValue value = new IntValue(Integer.parseInt(line));
            prgState.getSymTable().update(var_name,value);
        }catch (IOException ie){
            throw new InterpreterException("IO error occurred.");
        }

        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + expression.toString()+", " +var_name+")";
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(expression.deepCopy(),var_name.toString());
    }
}
