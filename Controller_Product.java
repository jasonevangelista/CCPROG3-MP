package JohnnyMoves;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller_Product {

    private Stage thisStage;

    private Controller_Transaction mainController;


    @FXML private TextField txtLength;
    @FXML private TextField txtWidth;
    @FXML private TextField txtHeight;
    @FXML private TextField txtWeight;
    @FXML private RadioButton rbRegular;
    @FXML private RadioButton rbIrregular;


    public Controller_Product(Controller_Transaction controller){
        mainController = controller;
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InputProduct.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Johnny Moves - Product");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.show();
    }

    @FXML
    private void createProduct(ActionEvent event){
        Item item = null;
        boolean shape = true;
        if(rbRegular.isSelected())
            shape = true;
        else if(rbIrregular.isSelected())
            shape = false;

        try{
            item = new Item("Product", Integer.parseInt(txtLength.getText()), Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtHeight.getText()), Integer.parseInt(txtWeight.getText()), shape);
            exit(event);
            mainController.addProdToListView(item);
        }
        catch (Exception e){
            System.out.println("invalid");
        }
//        finally {
//            mainController.setItem(item);
//        }
    }

    @FXML private void exit(ActionEvent event){
        Stage popupWindow = (Stage) txtLength.getScene().getWindow();
        popupWindow.close();
    }
}

