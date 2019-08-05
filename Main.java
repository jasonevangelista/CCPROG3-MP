package JohnnyMoves;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controller;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
//        window.setTitle("Johnny Moves");
//        window.setScene(new Scene(root, 700, 500));
//        window.show();

        Controller_MainMenu controller_mainMenu = new Controller_MainMenu();

        controller_mainMenu.showStage();
    }
}
