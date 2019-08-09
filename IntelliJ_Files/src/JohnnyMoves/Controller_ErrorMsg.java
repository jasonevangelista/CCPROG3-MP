package JohnnyMoves;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_ErrorMsg {

    private Stage thisStage;
    @FXML private Text txtErrorMsg;

    private String errorMessage;

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

    public void showStage() {
        setErrorMessage(errorMessage);
        thisStage.show();
    }

    public void setErrorMessage(String message){
        txtErrorMsg.setText(message);
    }

    @FXML public void closeWindow(){
        thisStage.close();
    }
}
