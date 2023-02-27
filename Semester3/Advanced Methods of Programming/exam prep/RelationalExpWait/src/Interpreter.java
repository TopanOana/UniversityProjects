import Controller.Controller;
import Model.ADT.DictionaryClass;
import Model.ADT.HeapClass;
import Model.ADT.ListClass;
import Model.ADT.StackClass;
import Model.Expressions.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.RefType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.InterfaceRepository;
import Repository.Repository;
import View.Commands.ExitCommand;
import View.Commands.RunExample;
import View.TextMenu;

import java.io.BufferedReader;
import java.util.ArrayList;

public class Interpreter {
    public static void main(String[] args){
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))), new CompStmt(
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new NewStmt("a", new VarExp("v")),new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a"))))
        )));
        StackClass<IStmt> exeStack1 = new StackClass<>();
        exeStack1.push(ex1);
        PrgState prg1 = new PrgState(exeStack1, new ListClass<>(), new DictionaryClass<String, Value>(), new DictionaryClass<StringValue, BufferedReader>(), new HeapClass());
        ArrayList<PrgState> list1 = new ArrayList<>();
        list1.add(prg1);
        InterfaceRepository repository1 = new Repository(list1,"log11.txt");
        Controller controller1 = new Controller(repository1);


        IStmt ex2 = new CompStmt( new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))), new CompStmt(
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new NewStmt("a", new VarExp("v")), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                new PrintStmt(new ArithExp("+", new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),new ValueExp(new IntValue(5))))))
        )));

        StackClass<IStmt> exeStack2 = new StackClass<>();
        exeStack2.push(ex2);
        PrgState prg2 = new PrgState(exeStack2, new ListClass<>(), new DictionaryClass<String, Value>(), new DictionaryClass<StringValue, BufferedReader>(), new HeapClass());
        ArrayList<PrgState> list2 = new ArrayList<>();
        list2.add(prg2);
        InterfaceRepository repository2 = new Repository(list2,"log22.txt");
        Controller controller2 = new Controller(repository2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))), new CompStmt(
                new PrintStmt(new ReadHeapExp(new VarExp("v"))), new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new ArithExp("+",
                new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)))))
        )));

        StackClass<IStmt> exeStack3 = new StackClass<>();
        exeStack3.push(ex3);
        PrgState prg3 = new PrgState(exeStack3, new ListClass<>(), new DictionaryClass<String, Value>(), new DictionaryClass<StringValue, BufferedReader>(), new HeapClass());
        ArrayList<PrgState> list3 = new ArrayList<>();
        list3.add(prg3);
        InterfaceRepository repository3 = new Repository(list3,"log33.txt");
        Controller controller3 = new Controller(repository3);

        IStmt ex4 = new CompStmt(new VarDeclStmt("v",new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))), new CompStmt(
                new WhileStmt(new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))), new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp("-", new VarExp("v"),new ValueExp(new IntValue(1)))))),
                new PrintStmt(new VarExp("v"))
        )));
        StackClass<IStmt> exeStack4 = new StackClass<>();
        exeStack4.push(ex4);
        PrgState prg4 = new PrgState(exeStack4, new ListClass<>(), new DictionaryClass<String, Value>(), new DictionaryClass<StringValue, BufferedReader>(), new HeapClass());
        ArrayList<PrgState> list4 = new ArrayList<>();
        list4.add(prg4);
        InterfaceRepository repository4 = new Repository(list4,"log44.txt");
        Controller controller4 = new Controller(repository4);



        IStmt ex5 = new CompStmt(new VarDeclStmt("v",new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))), new CompStmt(
                        new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(
                                new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a")))
                        )))), new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a"))))
                )))));
        StackClass<IStmt> exeStack5 = new StackClass<>();
        exeStack5.push(ex5);
        PrgState prg5 = new PrgState(exeStack5, new ListClass<>(), new DictionaryClass<String, Value>(), new DictionaryClass<StringValue, BufferedReader>(), new HeapClass());
        ArrayList<PrgState> list5 = new ArrayList<>();
        list5.add(prg5);
        InterfaceRepository repository5 = new Repository(list5,"log5.txt");
        Controller controller5 = new Controller(repository5);

        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0","exit"));
        textMenu.addCommand(new RunExample("1",ex1.toString(),controller1));
        textMenu.addCommand(new RunExample("2",ex2.toString(),controller2));
        textMenu.addCommand(new RunExample("3", ex3.toString(),controller3));
        textMenu.addCommand(new RunExample("4", ex4.toString(),controller4));
        textMenu.addCommand(new RunExample("5", ex5.toString(),controller5));
        textMenu.show();

    }
}
