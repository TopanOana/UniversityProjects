package View;

import Controller.Controller;
import Exceptions.InterpreterException;
import Model.ADT.DictionaryClass;
import Model.ADT.ListClass;
import Model.ADT.StackClass;
import Model.Expressions.ValueExp;
import Model.Expressions.VarExp;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.util.Scanner;


public class View {
    Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Input a program");
        System.out.println("2. Execute a program");
        System.out.println("3. Exit");
    }

    public void chooseMainMenu(){
        printMenu();
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch(choice){
            case 1:
                inputProgram();
                break;
            case 2:
                executeProgram();
                break;
            case 3:
                return;
            default:
                System.out.println("Not a valid command");
                break;
        }

    }

    private void executeProgram() {
    }

    private void inputProgram() {
        try {
//        Scanner scanner1 = new Scanner(System.in);
//        System.out.println("Input program:");
//        String programString = "";
        /*IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));


        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp("+",new ValueExp(new IntValue(2)),
                                new ArithExp("*"
                                ,new ValueExp(new IntValue(3)), new ValueExp(new IntValue(0))))),
                                new CompStmt(new AssignStmt("b",new ArithExp("+",
                                        new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

            IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                    new CompStmt(new VarDeclStmt("v", new IntType()),
                            new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                    new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                            VarExp("v"))))));
            */

            IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",new ValueExp(new StringValue("test.in"))),
                    new CompStmt(new OpenRFile(new VarExp("varf")), new CompStmt( new VarDeclStmt("varc", new IntType()),new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                            new CompStmt(new PrintStmt(new VarExp("varc")),new CompStmt(new ReadFile(new VarExp("varf"),"varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                    new CloseRFile(new VarExp("varf"))))))))));
            StackClass<IStmt> exeStack = new StackClass<>();
            ListClass<Value> output = new ListClass<>();
            DictionaryClass<String, Value> symTable = new DictionaryClass<String, Value>();
            DictionaryClass<StringValue, BufferedReader> fileTable = new DictionaryClass<>();
            PrgState program = new PrgState(exeStack, output, symTable,fileTable);
            controller.addProgram(ex4.execute(program));
             this.controller.allStep();
            //System.out.println(result);

//        ex1.execute(program);
        }
        catch(InterpreterException interpreterException){
            System.out.println(interpreterException.getMessage());
        }

    }

}
