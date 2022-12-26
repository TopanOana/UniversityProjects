import Controller.Controller;
import Model.ADT.DictionaryClass;
import Model.ADT.ListClass;
import Model.ADT.StackClass;
import Model.Expressions.ArithExp;
import Model.Expressions.ValueExp;
import Model.Expressions.VarExp;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
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
        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        StackClass<IStmt> exeStack1 = new StackClass<>();
        exeStack1.push(ex1);
        PrgState prg1 = new PrgState(exeStack1, new ListClass<>(), new DictionaryClass<String, Value>(), new DictionaryClass<StringValue, BufferedReader>());
        ArrayList<PrgState> list1 = new ArrayList<>();
        list1.add(prg1);
        InterfaceRepository repository1 = new Repository(list1,"log1.txt");
        Controller controller1 = new Controller(repository1);


        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp("+",new ValueExp(new IntValue(2)),
                                new ArithExp("*"
                                        ,new ValueExp(new IntValue(3)), new ValueExp(new IntValue(0))))),
                                new CompStmt(new AssignStmt("b",new ArithExp("+",
                                        new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        StackClass<IStmt> exeStack2 = new StackClass<>();
        exeStack2.push(ex2);
        PrgState prg2 = new PrgState(exeStack2, new ListClass<>(), new DictionaryClass<String, Value>(), new DictionaryClass<StringValue, BufferedReader>());
        ArrayList<PrgState> list2 = new ArrayList<>();
        list2.add(prg2);
        InterfaceRepository repository2 = new Repository(list2,"log2.txt");
        Controller controller2 = new Controller(repository2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        StackClass<IStmt> exeStack3 = new StackClass<>();
        exeStack3.push(ex3);
        PrgState prg3 = new PrgState(exeStack3, new ListClass<>(), new DictionaryClass<String, Value>(), new DictionaryClass<StringValue, BufferedReader>());
        ArrayList<PrgState> list3 = new ArrayList<>();
        list3.add(prg3);
        InterfaceRepository repository3 = new Repository(list3,"log3.txt");
        Controller controller3 = new Controller(repository3);

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",new ValueExp(new StringValue("test.in"))),
                new CompStmt(new OpenRFile(new VarExp("varf")), new CompStmt( new VarDeclStmt("varc", new IntType()),new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                        new CompStmt(new PrintStmt(new VarExp("varc")),new CompStmt(new ReadFile(new VarExp("varf"),"varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                new CloseRFile(new VarExp("varf"))))))))));
        StackClass<IStmt> exeStack4 = new StackClass<>();
        exeStack4.push(ex4);
        PrgState prg4 = new PrgState(exeStack3, new ListClass<>(), new DictionaryClass<String, Value>(), new DictionaryClass<StringValue, BufferedReader>());
        ArrayList<PrgState> list4 = new ArrayList<>();
        list4.add(prg4);
        InterfaceRepository repository4 = new Repository(list3,"log4.txt");
        Controller controller4 = new Controller(repository4);


        TextMenu textMenu = new TextMenu();
        textMenu.addCommand(new ExitCommand("0","exit"));
        textMenu.addCommand(new RunExample("1",ex1.toString(),controller1));
        textMenu.addCommand(new RunExample("2",ex2.toString(),controller2));
        textMenu.addCommand(new RunExample("3", ex3.toString(),controller3));
        textMenu.addCommand(new RunExample("4", ex4.toString(),controller4));
        textMenu.show();

    }
}
