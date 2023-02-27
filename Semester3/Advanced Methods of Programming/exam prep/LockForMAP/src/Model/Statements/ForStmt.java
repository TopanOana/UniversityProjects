package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.Expressions.Expression;
import Model.Expressions.RelExp;
import Model.Expressions.VarExp;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;

public class ForStmt implements IStmt{

    String varName;
    Expression exp1,exp2,exp3;

    IStmt stmt;

    public ForStmt(String varName, Expression exp1, Expression exp2, Expression exp3, IStmt stmt) {
        this.varName = varName;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }

    @Override
    public String toString() {
        return "for("+varName+"="+exp1.toString()+";"+varName+"<"+exp2.toString()+";"+varName+"="+exp3.toString()+")"+stmt.toString();
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        IStmt imibag = new CompStmt(new AssignStmt(varName,exp1),new WhileStmt(new RelExp("<",new VarExp(varName),exp2),new CompStmt(stmt,new AssignStmt(varName,exp3))));

        prgState.getStack().push(imibag);

        return null;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        Type typeExp1 = exp1.typeCheck(typeEnv);
        Type typeExp2 = exp2.typeCheck(typeEnv);
        Type typeExp3 = exp3.typeCheck(typeEnv);
        if(typeExp1.equals(new IntType()) && typeExp2.equals(new IntType()) && typeExp3.equals(new IntType())){
            stmt.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new InterpreterException("expressions in for no good");
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
