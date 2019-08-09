package JohnnyMoves;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;


public class Controller_ExitMenu {

    @FXML private PasswordField pfPassword;

    private Stage thisStage;

    private Calendar cal;


    public Controller_ExitMenu(Stage stage, Calendar cal){
        thisStage = stage;

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ExitMenu.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Johnny Moves - Exit");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.show();
    }

    @FXML
    private void switchToMainMenu(ActionEvent event){
        Controller_MainMenu controller_mainMenu = new Controller_MainMenu(thisStage, cal, 1);
        controller_mainMenu.showStage();
    }

    @FXML
    public void checkPassword(){
        if(pfPassword.getText().equals("password"))
            thisStage.close();
    }

    private void closeProgram(){

    }
}
