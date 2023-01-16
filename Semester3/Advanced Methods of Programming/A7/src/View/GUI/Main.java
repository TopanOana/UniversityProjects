package View.GUI;

import View.GUI.List.StatementListController;
import View.GUI.Program.ProgramController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader programLoader = new FXMLLoader();
        programLoader.setLocation(getClass().getResource("Program/program.fxml"));
        Parent programWindow = programLoader.load();

        ProgramController programController = programLoader.getController();
        primaryStage.setTitle("Program Window");
        primaryStage.setScene(new Scene(programWindow,900,570));
        primaryStage.show();

        FXMLLoader secondaryLoader = new FXMLLoader();
        secondaryLoader.setLocation(getClass().getResource("List/listStmt.fxml"));
        Parent secondaryWindow = secondaryLoader.load();
        StatementListController statementListController = secondaryLoader.getController();
        statementListController.setProgramController(programController);

        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("List programs");
        secondaryStage.setScene(new Scene(secondaryWindow,600,400));
        secondaryStage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
}
