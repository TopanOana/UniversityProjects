import Controller.Controller;
import Repository.Repository;
import View.View;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello world!");
        Repository repository = new Repository(new ArrayList<>());
        Controller controller = new Controller(repository);
        View view = new View(controller);
        view.chooseMainMenu();
    }
}