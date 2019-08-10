package JohnnyMoves;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Controller_ErrorMsg class is a controller for the ErrorMsg view.
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Controller_ErrorMsg {

    @FXML private Text txtErrorMsg;

    private Stage thisStage;
    private String errorMessage;

    /**
     * This constructor takes in the error message.
     *
     * @param message - error message
     */
    public Controller_ErrorMsg(String message){
        thisStage = new Stage();
        this.errorMessage = message;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ErrorMsg.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Johnny Moves - Error Message");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method shows the stage object.
     */
    public void showStage() {
        setErrorMessage(errorMessage);
        thisStage.show();
    }

    /**
     * This method sets the error message in the window.
     *
     * @param message - error message
     */
    public void setErrorMessage(String message){
        txtErrorMsg.setText(message);
    }

    /**
     * This fxml method closes the current window.
     */
    @FXML public void closeWindow(){
        thisStage.close();
    }
}
