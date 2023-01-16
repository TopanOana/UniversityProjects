package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class NewStmt implements IStmt{
    String varName;
    Expression expression;

    public NewStmt(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeVar = typeEnv.lookUp(varName);
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(new RefType(typeExp))){
            return typeEnv;
        }
        else
            throw new InterpreterException("New stmt: right hand side and left hand side have different types");
    }

    @Override
    public String toString() {
        return "new("+varName+", "+expression.toString()+")";
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {

        InterfaceDictionary symTable = prgState.getSymTable();
        if (symTable.lookUp(varName) == null){
            throw new InterpreterException("variable name has not been declared");
        }
        Value val = (Value) symTable.lookUp(varName);
        if(!(val.getType() instanceof RefType)){
            throw new InterpreterException("variable not of type reference");
        }
        Value expressionValue = expression.eval(symTable, prgState.getHeap());
        Type varNameType = ((RefValue)val).getLocationType();
        if (!varNameType.equals(expressionValue.getType())){
            throw new InterpreterException("evaluated expression is not of correct type");
        }
        InterfaceHeap heap = prgState.getHeap();
        Integer newPosition = heap.add(expressionValue);
        symTable.add(varName,new RefValue(newPosition, varNameType));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(varName.toString(), expression.deepCopy());
    }
}
