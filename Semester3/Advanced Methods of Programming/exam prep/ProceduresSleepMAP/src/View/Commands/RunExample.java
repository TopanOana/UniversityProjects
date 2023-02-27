package View.Commands;

import Controller.Controller;
import Exceptions.InterpreterException;
import View.Commands.Command;

public class RunExample extends Command {
    private Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try{
            this.controller.allStep();
        }
        catch(InterpreterException ie){
            System.out.println(ie.getMessage());
        }
    }
}
