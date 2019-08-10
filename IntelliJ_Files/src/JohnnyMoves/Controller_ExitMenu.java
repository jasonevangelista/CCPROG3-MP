package JohnnyMoves;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;

/**
 * The Controller_ExitMenu class is a controller for the ExitMenu view.
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Controller_ExitMenu {

    @FXML private PasswordField pfPassword;

    private Stage thisStage;
    private Calendar cal;

    /**
     * This controller takes in a Stage object and Calendar object.
     *
     * @param stage - Stage object
     * @param cal
     */
    public Controller_ExitMenu(Stage stage, Calendar cal){
        thisStage = stage;
        this.cal = cal;

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

    /**
     * This method shows the stage object.
     */
    public void showStage() {
        thisStage.show();
    }

    /**
     * This method switches the stage to the main menu.
     *
     * @param event - ActionEvent object
     */
    @FXML private void switchToMainMenu(ActionEvent event){
        Controller_MainMenu controller_mainMenu = new Controller_MainMenu(thisStage, cal, 1);
        controller_mainMenu.showStage();
    }

    /**
     * This method checks the password input.
     */
    @FXML private void checkPassword(){
        if(pfPassword.getText().equals("password"))
            thisStage.close();
    }
}
