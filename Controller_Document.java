package JohnnyMoves;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_Document {

    private Stage thisStage;

    private Controller_Transaction mainController;


    @FXML private TextField txtLength;

    @FXML private TextField txtWidth;

    @FXML private TextField txtNumPage;

    public Controller_Document(Controller_Transaction controller){
        mainController = controller;
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InputDocu.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Johnny Moves - Document");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.show();
    }

    @FXML
    private void createDocu(ActionEvent event){
        Item item = null;
//        try{
            item = new Item("Document", Integer.parseInt(txtLength.getText()), Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtNumPage.getText()));
            mainController.addDocuToListView(item);
            exit(event);
//        }
//        catch (Exception e){
//            System.out.println("invalid");
//        }
//        finally {
//            mainController.setItem(item);
//        }
    }

    @FXML private void exit(ActionEvent event){
        Stage popupWindow = (Stage) txtLength.getScene().getWindow();
        popupWindow.close();
    }
}
