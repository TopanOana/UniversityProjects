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
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary<String, Value> tbl = prgState.getSymTable();
        if (tbl.lookUp(name)==null){
            Value aux ;
            if (type.toString().equals("int"))
                aux = new IntValue();
            else aux = new BoolValue();
            tbl.add(name, aux);
        }
        else
            throw new InterpreterException("variable is already declared.");
        return prgState;
    }
}
