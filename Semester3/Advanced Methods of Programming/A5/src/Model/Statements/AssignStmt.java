package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceStack;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStmt implements IStmt{
    String id;

    public AssignStmt(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    Expression expression;
    @Override
    public String toString() {
        return id  +" = "+expression.toString();
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceStack<IStmt> stack = prgState.getStack();
        InterfaceDictionary<String, Value> symTable = prgState.getSymTable();

        if(symTable.lookUp(id) == null)
            throw new InterpreterException("the used variable " + id +" was not declared before.");
        else{
            Value value = expression.eval(symTable, prgState.getHeap());
            Type typeId = (symTable.lookUp(id)).getType();
            if (value.getType().equals(typeId))
                symTable.update(id, value);
            else{
                throw new InterpreterException("declared type of variable " + id + " and type of assigned expression do not match.");
            }
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {

        return new AssignStmt(id.toString(),expression.deepCopy());
    }
}
