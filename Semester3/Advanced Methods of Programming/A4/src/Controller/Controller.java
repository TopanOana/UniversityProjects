package Controller;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceStack;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Types.RefType;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.InterfaceRepository;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    InterfaceRepository repository ;

    public Controller(InterfaceRepository repository) {
        this.repository = repository;
    }

    Map<Integer, Value> garbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream().filter(e->symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Map<Integer,Value> heap){
        Set<Integer> toReturn = new HashSet<>();
        symTableValues.stream().filter(v->v instanceof RefValue).forEach(v->
        {
            while ( v instanceof RefValue){
                toReturn.add(((RefValue) v).getAddr());
                v = heap.get(((RefValue) v).getAddr());
            }
        });
        return toReturn.stream().collect(Collectors.toList());
    }
    public void addProgram(PrgState prgState){
        repository.pushPrg(prgState);
    }

    public PrgState oneStep(PrgState state) throws InterpreterException{
        InterfaceStack<IStmt> stack = state.getStack();
        if (stack.isEmpty())
            throw new InterpreterException("prgstate is empty");
        IStmt currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allStep() throws InterpreterException{
        PrgState program = repository.getCurrentProgram();
        while (!program.getStack().isEmpty()){
            oneStep(program);
            repository.logPrgStateExec();
            program.getHeap().setContent(garbageCollector(getAddrFromSymTable(program.getSymTable().getDictionary().values(),program.getHeap().getHeap()), program.getHeap().getHeap()));
//            repository.logPrgStateExec();
        }

    }

    public String showCurrentState(){
        PrgState program = repository.getCurrentProgram();
        return program.toString();
    }
}
