package JohnnyMoves;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.io.IOException;


public class Controller_MainMenu {

    @FXML private Text title;

    @FXML private Text enterPassword;

    private final Stage thisStage;

    public Controller_MainMenu(){
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Johnny Moves");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.show();
    }

    @FXML
    private void switchToExitMenu(ActionEvent event) throws IOException {

        Scene exitMenu = new Scene(FXMLLoader.load(getClass().getResource("ExitMenu.fxml")));

        Stage window = (Stage)title.getScene().getWindow();

        window.setScene(exitMenu);
        window.show();
    }

    @FXML
    private void switchToMainMenu(ActionEvent event) throws IOException {

        Scene MainMenu = new Scene(FXMLLoader.load(getClass().getResource("MainMenu.fxml")));

        Stage window = (Stage)enterPassword.getScene().getWindow();

        window.setScene(MainMenu);
        window.show();
    }

    @FXML
    private void switchToTracker(ActionEvent event) throws IOException {

        Scene MainMenu = new Scene(FXMLLoader.load(getClass().getResource("Track_Input.fxml")));

        Stage window = (Stage)title.getScene().getWindow();

        window.setScene(MainMenu);
        window.show();
    }

    @FXML
    private void startTransaction(ActionEvent event) throws IOException {
//        Scene MainMenu = new Scene(FXMLLoader.load(getClass().getResource("InputMenu.fxml")));
//
//        Stage window = (Stage)title.getScene().getWindow();
//
//        window.setScene(MainMenu);
//        window.show();

        Controller_Transaction controller_transaction = new Controller_Transaction(thisStage);
        controller_transaction.showStage();
    }
}
