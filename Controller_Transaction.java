package JohnnyMoves;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Controller_Transaction {

    private Stage thisStage;

    @FXML private TextField txtRecipientName;

    @FXML private RadioButton rbMML;
    @FXML private RadioButton rbLUZ;
    @FXML private RadioButton rbVIS;
    @FXML private RadioButton rbMIN;

    @FXML private ListView<String> docuListView = new ListView<>();
    @FXML private ListView<String> prodListView = new ListView<>();
    @FXML private ListView<String> validParcelListView = new ListView<>();

    private ArrayList<Item> listItem  = new ArrayList<>();

    @FXML private CheckBox cbInsurance;

    private String display = "";


    public Controller_Transaction(Stage stage){
        thisStage = stage;
        docuListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        prodListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Transaction transaction = new Transaction();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("InputMenu.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Johnny Moves - Transaction");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.show();
    }

    public void addDocuToListView(Item item){
        display = item.getLength() + " " + item.getWidth()+ " " + item.getNumPage();

        listItem.add(item);

        docuListView.getItems().addAll(display);
    }

    public void addProdToListView(Item item){
        display = item.getLength() + " " + item.getWidth()+ " " + item.getHeight() + " " + item.getWeight();

        listItem.add(item);

        prodListView.getItems().addAll(display);
    }


    @FXML public void deleteDocu(ActionEvent event){
        String selectedItem = docuListView.getSelectionModel().getSelectedItem();

        for(int i = 0; i < listItem.size(); i++){
            if((listItem.get(i).getLength() + " " + listItem.get(i).getWidth()+ " " + listItem.get(i).getNumPage()).equalsIgnoreCase(selectedItem)){
                listItem.remove(i);
                docuListView.getItems().remove(i);
            }
        }
    }

    @FXML public void deleteProd(ActionEvent event){
        String selectedItem = prodListView.getSelectionModel().getSelectedItem();

        for(int i = 0; i < listItem.size(); i++){
            if((listItem.get(i).getLength() + " " + listItem.get(i).getWidth()+ " " + listItem.get(i).getHeight() + " " + listItem.get(i).getWeight()).equalsIgnoreCase(selectedItem)){
                listItem.remove(i);
                prodListView.getItems().remove(i);
            }
        }
    }


    @FXML private void addDocument(ActionEvent event) throws IOException {
        Controller_Document controller_Document = new Controller_Document(this);

        controller_Document.showStage();
    }

    @FXML private void addProduct(ActionEvent event) throws IOException {
        Controller_Product controller_Product = new Controller_Product(this);

        controller_Product.showStage();
    }

//    @FXML private void displayDocu(ActionEvent event){
//        System.out.println("Length: " + item.getLength());
//        System.out.println("Width: " + item.getWidth());
//        System.out.println("No. of Pages: " + item.getNumPage());
//    }
//
//    @FXML private void displayProduct(ActionEvent event){
//        System.out.println("Length: " + item.getLength());
//        System.out.println("Width: " + item.getWidth());
//        System.out.println("Height: " + item.getHeight());
//        System.out.println("Weight: " + item.getWeight());
//        System.out.println("Shape(true - regular/ false - irregular): " + item.getShape());
//    }

    @FXML private void checkValidTypes(ActionEvent event){
        ArrayList<Item> parcelTypeList = new ArrayList<>();

        /*
        parcelTypeList = transaction.determineValidTypes(listItem);
        for(int i = 0; i < parcelTypeList.size(); i++){
            validParcelListView.getItems().addAll(parcelTypeList.get(i));
            }
        }

         */
    }

    @FXML private void finishTransaction(ActionEvent event){
        String recipientName;
        String delRegion;
        String type;
        boolean insurance;

        // recipient's name
        recipientName = txtRecipientName.getText();

        // delivery region
        if(rbMML.isSelected())
            delRegion = "MML";
        else if(rbLUZ.isSelected())
            delRegion = "LUZ";
        else if(rbVIS.isSelected())
            delRegion = "VIS";
        else if(rbMIN.isSelected())
            delRegion = "MIN";
        else
            delRegion = "";

        // type of parcel
        type = validParcelListView.getSelectionModel().getSelectedItem();

        // insurance
        if(cbInsurance.isSelected())
            insurance = true;
        else
            insurance = false;

        /*
        Parcel package = new Parcel(listItem, type, insurance);
        Customer customer = new Customer(recipientName, package);
        transaction.setRecipient(customer);

        // open new scene
         */


    }


}
