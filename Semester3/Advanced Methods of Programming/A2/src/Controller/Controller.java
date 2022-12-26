package Controller;

import Exceptions.InterpreterException;
import Model.ADT.InterfaceStack;
import Model.PrgState;
import Model.Statements.IStmt;
import Repository.InterfaceRepository;

public class Controller {
    InterfaceRepository repository ;

    public Controller(InterfaceRepository repository) {
        this.repository = repository;
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

    public String allStep(){
        String execution = "Execution of program:\n";
        PrgState program = repository.getCurrentProgram();
        execution=execution+program.toString()+"\n";
        while (!program.getStack().isEmpty()){
            oneStep(program);
            execution=execution+program.toString()+"\n";
        }

        return execution;
    }

    public String showCurrentState(){
        PrgState program = repository.getCurrentProgram();
        return program.toString();
    }
}
