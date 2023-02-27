package View.GUI.Program;

import Controller.Controller;
import Exceptions.InterpreterException;
import Model.ADT.*;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;


public class ProgramController implements Initializable{
    private Controller controller;
    @FXML
    private TableView<Pair<Integer, Value>> heapTable;

    @FXML
    private TableColumn<Pair<Integer,Value>, Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer,Value>,String> valueColumn;

    @FXML
    private ListView<String> outputList;

    @FXML
    private ListView<String> fileList;

    @FXML
    private ListView<Integer> prgStateList;

    @FXML
    private TableView<Pair<String,Value>> symTable;

    @FXML
    private TableColumn<Pair<String,Value>,String> varNameColumn;

    @FXML
    private TableColumn<Pair<String,Value>,String> varValueColumn;

    @FXML
    private ListView<String> exeStackList;

    @FXML
    private TextField nrPrgStates;

    @FXML
    private Button runStepButton;

    @FXML
    private TableView<Triple<Integer, Integer,String>> semaphoreTable;

    @FXML
    private TableColumn<Triple<Integer, Integer,String>,Integer> indexColumn;

    @FXML
    private TableColumn<Triple<Integer, Integer,String>, Integer> valueSemaphoreColumn;

    @FXML
    private TableColumn<Triple<Integer, Integer,String>, String> threadColumn;


    public void setController(Controller controller) {
        this.controller = controller;
    }
    public Controller getController(){
        return controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        addressColumn.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        valueColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getValue().toString()));
        varNameColumn.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getKey()));
        varValueColumn.setCellValueFactory(p-> new SimpleStringProperty(p.getValue().getValue().toString()));
        indexColumn.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getFirst()).asObject());
        valueSemaphoreColumn.setCellValueFactory(p->new SimpleIntegerProperty(p.getValue().getSecond()).asObject());
        threadColumn.setCellValueFactory(p->new SimpleStringProperty(p.getValue().getThird().toString()));

        runStepButton.setOnAction(actionEvent -> oneStepHandler());
        prgStateList.setOnMouseClicked(mouseEvent -> changePrgStateHandler(getCurrentPrgState()));
    }

    private PrgState getCurrentPrgState(){
        if(controller.getPrgStates().size()==0)
            return null;
        int currentId = prgStateList.getSelectionModel().getSelectedIndex();
        if (currentId == -1)
            return controller.getPrgStates().get(0);
        return controller.getPrgStates().get(currentId);
    }

    private void changePrgStateHandler(PrgState currentPrgState){
        if(controller==null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Program not selected",ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try{
            populate();
        }
        catch(InterpreterException interpreterException){
            Alert alert = new Alert(Alert.AlertType.ERROR, interpreterException.getMessage(),ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void oneStepHandler(){
        if(controller==null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Program not selected",ButtonType.OK);
            alert.showAndWait();
            return;
        }
        PrgState prgState = getCurrentPrgState();
        if (prgState!=null && !prgState.isNotCompleted()){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Nothing left to execute",ButtonType.OK);
            alert.showAndWait();
            return;
        }
        try{
            controller.oneStepForAll();
            changePrgStateHandler(prgState);
            if(controller.getPrgStates().size()==0){
                runStepButton.setDisable(true);
            }
        }catch(InterpreterException interpreterException){
            Alert alert = new Alert(Alert.AlertType.ERROR, interpreterException.getMessage(),ButtonType.OK);
            alert.showAndWait();
            runStepButton.setDisable(true);
            return;
        }
    }

    private void populate(){
        populateHeap();
        populatePrgStateIdentifiers();
        populateFileTable();
        populateOutput();
        populateSymTable();
        populateExeStack();
        populateSemaphoreTable();
    }

    private void populateSemaphoreTable() {
        InterfaceTable<Integer, Triple<Integer, ArrayList<Integer>, Integer>> semaphoreT;
        if(controller.getPrgStates().size()>0)
            semaphoreT=controller.getPrgStates().get(0).getSemaphoreTable();
        else semaphoreT=new TableClass();
        List<Triple<Integer, Integer, String>> semaphoreTableList= new ArrayList<>();
        for (Map.Entry<Integer, Triple<Integer, ArrayList<Integer>, Integer>>entry: semaphoreT.getTable().entrySet())
            semaphoreTableList.add(new Triple<>(entry.getKey(), entry.getValue().getFirst()-entry.getValue().getThird(), entry.getValue().getSecond().toString()));
        semaphoreTable.setItems(FXCollections.observableList(semaphoreTableList));
        semaphoreTable.refresh();
    }

    private void populateHeap(){
        InterfaceHeap heap;
        if (controller.getPrgStates().size()>0)
            heap = controller.getPrgStates().get(0).getHeap();
        else heap= new HeapClass();
        List<Pair<Integer,Value>> heapTableList = new ArrayList<>();
        for (Map.Entry<Integer,Value>entry: heap.getHeap().entrySet())
            heapTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        heapTable.setItems(FXCollections.observableList(heapTableList));
        heapTable.refresh();
    }

    private void populatePrgStateIdentifiers(){
        List<PrgState> prgStates = controller.getPrgStates();
        var idList = prgStates.stream().map(ps->ps.getId_thread()).collect(Collectors.toList());
        prgStateList.setItems(FXCollections.observableList(idList));
        nrPrgStates.setText(""+prgStates.size());
        prgStateList.refresh();
    }

    private void populateFileTable(){
        ArrayList<StringValue> files;
        if (controller.getPrgStates().size()>0)
            files = new ArrayList<>(controller.getPrgStates().get(0).getFileTable().getDictionary().keySet());
        else files = new ArrayList<>();
        List<String> filesList = files.stream().map(p -> p.toString()).collect(Collectors.toList());
        fileList.setItems(FXCollections.observableList(filesList));
        fileList.refresh();
    }

    private void populateOutput(){
        InterfaceList<Value> output;
        if(controller.getPrgStates().size()>0)
            output = controller.getPrgStates().get(0).getOutput();
        else output = new ListClass<>();
        List<String> outputsList = output.getList().stream().map(p ->p.toString()).collect(Collectors.toList());
        outputList.setItems(FXCollections.observableList(outputsList));
        outputList.refresh();
    }

    private void populateSymTable(){
        PrgState prgState = getCurrentPrgState();
        List<Pair<String,Value>> symTableList = new ArrayList<>();
        if (prgState!=null)
            for (Map.Entry<String,Value>entry: prgState.getSymTable().getDictionary().entrySet())
                symTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
        symTable.setItems(FXCollections.observableList(symTableList));
        symTable.refresh();
    }

    private void populateExeStack(){
        PrgState prgState = getCurrentPrgState();
        List<String> exeStackString = new ArrayList<>();
        if (prgState!=null){
            for (IStmt stmt: prgState.getStack().getContent())
                exeStackString.add(stmt.toString());
        }
        exeStackList.setItems(FXCollections.observableList(exeStackString));
        exeStackList.refresh();
    }


}

