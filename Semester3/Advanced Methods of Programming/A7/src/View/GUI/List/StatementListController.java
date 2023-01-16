package View.GUI.List;

import Controller.Controller;
import Exceptions.InterpreterException;
import Model.ADT.DictionaryClass;
import Model.Expressions.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.InterfaceRepository;
import Repository.Repository;
import View.GUI.Program.ProgramController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class StatementListController implements Initializable{
    private ProgramController programController;


    public void setProgramController(ProgramController programController){
        this.programController=programController;
    }

    @FXML
    private ListView<IStmt> statementList;

    @FXML
    private Button executeButton;

    private List<IStmt> buildProgramStatements(){
        IStmt ex1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp("+",new ValueExp(new IntValue(2)),
                                new ArithExp("*",new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp("+",new VarExp("a"),
                                        new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(new AssignStmt("varf",new ValueExp(new StringValue("test.in"))),
                new CompStmt(new OpenRFile(new VarExp("varf")), new CompStmt( new VarDeclStmt("varc", new IntType()),new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                        new CompStmt(new PrintStmt(new VarExp("varc")),new CompStmt(new ReadFile(new VarExp("varf"),"varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                new CloseRFile(new VarExp("varf"))))))))));

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))), new CompStmt(
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new NewStmt("a", new VarExp("v")),new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a"))))
        )));

        IStmt ex6 = new CompStmt( new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v",new ValueExp(new IntValue(20))), new CompStmt(
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new NewStmt("a", new VarExp("v")), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                new PrintStmt(new ArithExp("+", new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),new ValueExp(new IntValue(5))))))
        )));

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))), new CompStmt(
                new PrintStmt(new ReadHeapExp(new VarExp("v"))), new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new ArithExp("+",
                new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)))))
        )));

        IStmt ex8 = new CompStmt(new VarDeclStmt("v",new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))), new CompStmt(
                new WhileStmt(new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))), new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp("-", new VarExp("v"),new ValueExp(new IntValue(1)))))),
                new PrintStmt(new VarExp("v"))
        )));

        IStmt ex9 = new CompStmt(new VarDeclStmt("v",new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))), new CompStmt(
                        new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(
                                new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a")))
                        )))), new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a"))))
                )))));
        List<IStmt> prgStatements = new ArrayList<>();
        prgStatements.add(ex1);
        prgStatements.add(ex2);
        prgStatements.add(ex3);
        prgStatements.add(ex4);

        prgStatements.add(ex5);
        prgStatements.add(ex6);
        prgStatements.add(ex7);
        prgStatements.add(ex8);
        prgStatements.add(ex9);
        return prgStatements;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        List<IStmt> prgStatements = buildProgramStatements();

        this.statementList.setItems(FXCollections.observableList(prgStatements));

        executeButton.setOnAction(actionEvent -> {
            int index = statementList.getSelectionModel().getSelectedIndex();
            if (index<0)
                return;
            IStmt initialStatement = statementList.getItems().get(index);
            PrgState initialPrgState = new PrgState(initialStatement);
            InterfaceRepository repository = new Repository("log"+index+".txt");
            Controller controller = new Controller(repository);
            controller.addProgram(initialPrgState);
            try{
                controller.runTypeChecker();
                programController.setController(controller);
            }catch(InterpreterException interpreterException){
                Alert alert = new Alert(Alert.AlertType.ERROR,interpreterException.getMessage());
                alert.showAndWait();
            }
        });
    }


}
