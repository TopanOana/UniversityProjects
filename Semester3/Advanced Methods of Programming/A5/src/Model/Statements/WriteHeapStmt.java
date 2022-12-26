package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceHeap;
import Model.Expressions.Expression;
import Model.PrgState;
import Model.Types.RefType;
import Model.Values.RefValue;
import Model.Values.Value;

import java.util.Map;

public class WriteHeapStmt implements IStmt{
    String varName;
    Expression expression;

    public WriteHeapStmt(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }


    @Override
    public String toString() {
        return "wH(" +
                 varName +
                 expression.toString() +
                ')';
    }

    @Override
    public PrgState execute(PrgState prgState) throws InterpreterException {
        InterfaceDictionary symTable = prgState.getSymTable();
        InterfaceHeap heap = prgState.getHeap();
        if (symTable.lookUp(varName) == null){
            throw new InterpreterException("variable not declared");
        }
        Value value = (Value) symTable.lookUp(varName);
        if (! (value.getType() instanceof RefType)){
            throw new InterpreterException("variable not of RefType");
        }
        RefValue refValue = (RefValue)value;
        Map heap_small = heap.getHeap();
        if (!heap_small.containsKey(refValue.getAddr())){
            throw new InterpreterException("address from RefValue from SymTable is not a key in Heap");
        }
        Value expressionEval = expression.eval(symTable, prgState.getHeap());
        if (! (expressionEval.getType().equals(refValue.getLocationType())) ){
            throw new InterpreterException("expression type not equal to the location type of the variable");
        }
        heap.update(refValue.getAddr(), expressionEval);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return null;
    }
}
