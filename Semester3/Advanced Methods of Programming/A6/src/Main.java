import Controller.Controller;
import Repository.Repository;
import View.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Name of text file:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String file = bufferedReader.readLine();
        Repository repository = new Repository(new ArrayList<>(),file);
        Controller controller = new Controller(repository);
        View view = new View(controller);
        view.chooseMainMenu();
    }
}