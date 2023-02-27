package Controller;

import Exceptions.InterpreterException;
import Model.ADT.DictionaryClass;
import Model.ADT.InterfaceDictionary;
import Model.ADT.InterfaceStack;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;
import Repository.InterfaceRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    InterfaceRepository repository ;
    ExecutorService executor;

    public Controller(InterfaceRepository repository) {
        this.repository = repository;
    }

    public List<PrgState> getPrgStates(){
        return repository.getPrgList();
    }

    Map<Integer, Value> garbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream().filter(e->symTableAddr.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    List<Integer> getAddrFromSymTable(List<Collection<Value>> symTableValues, Map<Integer,Value> heap){
        Set<Integer> toReturn = new HashSet<>();
        symTableValues.forEach(symTable->symTable.stream().filter(v->v instanceof RefValue).forEach(v->
        {
            while ( v instanceof RefValue){
                toReturn.add(((RefValue) v).getAddr());
                v = heap.get(((RefValue) v).getAddr());
            }
        }));

        return toReturn.stream().collect(Collectors.toList());
    }
    public void addProgram(PrgState prgState){
        repository.pushPrg(prgState);
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream().filter(p->p.isNotCompleted()).collect(Collectors.toList());
    }

    public void runTypeChecker() throws InterpreterException{
        for(PrgState state: repository.getPrgList()) {
            InterfaceDictionary<String, Type> typeEnv = new DictionaryClass<>();
            state.getStack().getContent().peek().typeCheck(typeEnv);
        }
    }
    public void allStep() throws InterpreterException{
        runTypeChecker();
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());
        while(prgList.size()>0){
            PrgState state = prgList.get(0);
            state.getHeap().setContent(garbageCollector(
                    getAddrFromSymTable(
                            prgList.stream().map(prg->prg.getSymTable().getDictionary().values()).collect(Collectors.toList()),
                            state.getHeap().getHeap()),
                    state.getHeap().getHeap()
            ));
            oneStepForAllPrg(prgList);
            prgList=removeCompletedPrg(prgList);
        }
        executor.shutdownNow();
        repository.setPrgList(prgList);


    }

    public void oneStepForAll() throws InterpreterException{
        executor = Executors.newFixedThreadPool(2);
        repository.setPrgList(removeCompletedPrg(repository.getPrgList()));
        List<PrgState> prgStates = repository.getPrgList();
        if(prgStates.size()>0){
            oneStepForAllPrg(prgStates);
        }
        repository.setPrgList(removeCompletedPrg(repository.getPrgList()));
        executor.shutdownNow();
        garbageCollector(
                getAddrFromSymTable(
                        prgStates.stream().map(prg->prg.getSymTable().getDictionary().values()).collect(Collectors.toList()),
                        prgStates.get(0).getHeap().getHeap()),
                prgStates.get(0).getHeap().getHeap()
        );
        repository.setPrgList(prgStates);
    }


    public void oneStepForAllPrg(List<PrgState> prgList){
        prgList.forEach(prg->repository.logPrgStateExec(prg));
        List<Callable<PrgState>> callList = prgList.stream().map((PrgState p)->(Callable<PrgState>)(()->{return p.oneStep();})).collect(Collectors.toList());

        List<PrgState> newPrgList = null;
        try {
            newPrgList = executor.invokeAll(callList).stream().map(future->{try{return future.get();}catch(
                            InterpreterException ie){throw new InterpreterException("shit doesn't work");} catch (
                            ExecutionException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    })
                    .filter(p->p!=null).collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        prgList.addAll(newPrgList);

        prgList.forEach(prg->repository.logPrgStateExec(prg));
        repository.setPrgList(prgList);
    }

    public String showCurrentState(){
//        PrgState program = repository.getCurrentProgram();
//        return program.toString();
        return "";
    }
}
