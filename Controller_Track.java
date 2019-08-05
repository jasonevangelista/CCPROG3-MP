package JohnnyMoves;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.text.Text;

import java.io.IOException;

public class Controller_Track {

    @FXML
    private Text tTrackNum;

    @FXML
    private void switchToMainMenu(ActionEvent event) throws IOException {

        Scene exitMenu = new Scene(FXMLLoader.load(getClass().getResource("MainMenu.fxml")));

        Stage window = (Stage)tTrackNum.getScene().getWindow();

        window.setScene(exitMenu);
        window.show();
    }

}
