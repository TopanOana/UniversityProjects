package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class VarDeclStmt implements IStmt{
    String name;
    Type type;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public InterfaceDictionary<String, Type> typeCheck(InterfaceDictionary<String, Type> typeEnv) throws InterpreterException {
        typeEnv.add(name,type);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name.toString(),type.deepCopy());
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary<String, Value> tbl = prgState.getSymTable();
        try {
            tbl.lookUp(name);
            throw new InterpreterException("variable is already declared.");
        }catch(InterpreterException ie){
            Value aux = type.defaultValue();

            tbl.add(name, aux);
        }

        return null;
    }
}
