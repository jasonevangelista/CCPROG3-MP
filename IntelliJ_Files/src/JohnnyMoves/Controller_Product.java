package JohnnyMoves;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import JohnnyMoves.model.*;

/**
 * The Controller_Product class is a controller for the InputProduct view.
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Controller_Product {

    private Stage thisStage;
    private Controller_Transaction mainController;

    @FXML private TextField txtLength;
    @FXML private TextField txtWidth;
    @FXML private TextField txtHeight;
    @FXML private TextField txtWeight;
    @FXML private RadioButton rbRegular;
    @FXML private RadioButton rbIrregular;

    /**
     * This constructor takes in a Controller_Transaction object.
     *
     * @param controller - Controller_Transaction object
     */
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

    /**
     * This method shows the stage object.
     */
    public void showStage() {
        thisStage.show();
    }

    /**
     * This fxml method initializes a Product object based on the inputs.
     *
     * @param event - ActionEvent event
     */
    @FXML private void createProduct(ActionEvent event){
        Product item = null;
        boolean shape = true;
        boolean noShapeChosen = false;
        if(rbRegular.isSelected())
            shape = true;
        else if(rbIrregular.isSelected())
            shape = false;
        else
            noShapeChosen = true;


        try{
            item = new Product(Double.parseDouble(txtLength.getText()), Double.parseDouble(txtWidth.getText()), Double.parseDouble(txtHeight.getText()), Double.parseDouble(txtWeight.getText()), shape);

            if(Double.parseDouble(txtLength.getText()) <= 0 || Double.parseDouble(txtWidth.getText()) <= 0 || Double.parseDouble(txtHeight.getText()) <= 0 || Double.parseDouble(txtWeight.getText()) <= 0 || noShapeChosen){
                Controller_ErrorMsg errorWindow = new Controller_ErrorMsg("INVALID PRODUCT INPUTS");
                errorWindow.showStage();
            }

            else{
                exit(event);
                mainController.addProdToListView(item);
            }
        }
        catch (Exception e){
            Controller_ErrorMsg errorWindow = new Controller_ErrorMsg("INVALID PRODUCT INPUTS");
            errorWindow.showStage();
        }
    }

    /**
     * This fxml method closes the input window.
     *
     * @param event - ActionEvent object
     */
    @FXML private void exit(ActionEvent event){
        Stage popupWindow = (Stage) txtLength.getScene().getWindow();
        popupWindow.close();
    }
}

