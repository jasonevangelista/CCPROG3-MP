package JohnnyMoves;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;

import JohnnyMoves.model.*;

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

    @FXML private CheckBox cbInsurance;

    @FXML private Text txtDate;
    @FXML private Text txtTime;

    private ArrayList<Item> listItem  = new ArrayList<>();
    private ArrayList<String> parcelTypes;

    private Transaction transaction;

    private Calendar cal;

    private int seqNum;

    private String display = "";


    public Controller_Transaction(Calendar cal, Stage stage, int seqNum){
        thisStage = stage;
        this.cal = cal;
        this.seqNum = seqNum;

        docuListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        prodListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

         transaction = new Transaction();

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

    // show current stage
    public void showStage() {
        displayTime();
        thisStage.show();
    }

    // adds document object to docu list view and item list
    public void addDocuToListView(Document document){
        display = document.getLength() + "in x " + document.getWidth()+ "in\t\t\t" + document.getNumPage();

        listItem.add(document);

        docuListView.getItems().add(display);
    }

    // adds product object to prod list and item list
    public void addProdToListView(Item item){
        display = item.getLength() + "in x " + item.getWidth()+ "in x " + item.getHeight() + "in\t" + item.getWeight() + "kg";

        listItem.add(item);

        prodListView.getItems().add(display);
    }

    // removes docu from docu list view and item list
    @FXML public void deleteDocu(ActionEvent event){
        String selectedItem = docuListView.getSelectionModel().getSelectedItem();

        for(int i = 0; i < listItem.size(); i++){
            if((listItem.get(i).getLength() + "in x " + listItem.get(i).getWidth()+ "in\t\t\t" + ((Document)listItem.get(i)).getNumPage()).equalsIgnoreCase(selectedItem))
                listItem.remove(i);
        }

        for(int i = 0; i < docuListView.getItems().size(); i++){
            if(docuListView.getItems().get(i).equalsIgnoreCase(selectedItem))
                docuListView.getItems().remove(i);
        }
    }

    // removes product from prod list view and item list
    @FXML public void deleteProd(ActionEvent event){
        String selectedItem = prodListView.getSelectionModel().getSelectedItem();

        for(int i = 0; i < listItem.size(); i++){
            if((listItem.get(i).getLength() + "in x " + listItem.get(i).getWidth()+ "in x " + listItem.get(i).getHeight() + "in\t" + listItem.get(i).getWeight() + "kg").equalsIgnoreCase(selectedItem))
                listItem.remove(i);
        }

        for(int i = 0; i < prodListView.getItems().size(); i++){
            if(prodListView.getItems().get(i).equalsIgnoreCase(selectedItem))
                prodListView.getItems().remove(i);
        }
    }

    // opens document input window
    @FXML private void addDocument(ActionEvent event){
        Controller_Document controller_Document = new Controller_Document(this);

        controller_Document.showStage();
    }

    // opens product input window
    @FXML private void addProduct(ActionEvent event){
        Controller_Product controller_Product = new Controller_Product(this);

        controller_Product.showStage();
    }


    @FXML private void checkValidTypes(ActionEvent event){
        parcelTypes = new ArrayList<>();
        validParcelListView.getItems().clear();
        ArrayList<Item> items = new ArrayList<>();

        if(listItem.isEmpty()){
            // error message
            Controller_ErrorMsg errorMsg = new Controller_ErrorMsg("No items indicated!");
            errorMsg.showStage();
        }
        else{

            for(int i = 0; i < listItem.size(); i++){
                items.add(listItem.get(i));
            }

            parcelTypes = transaction.determineValidTypes(items);

            if(parcelTypes.isEmpty()){
                Controller_ErrorMsg errorMsg = new Controller_ErrorMsg("Too much items!");
                errorMsg.showStage();
            }
            else{
                for(int i = 0; i < parcelTypes.size(); i++)
                    validParcelListView.getItems().add(parcelTypes.get(i));
            }
        }

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
            delRegion = null;

        // type of parcel
        type = validParcelListView.getSelectionModel().getSelectedItem();

        // insurance
        if(cbInsurance.isSelected())
            insurance = true;
        else
            insurance = false;

        if(recipientName == null || delRegion == null || listItem.size() == 0 || type == null){
            Controller_ErrorMsg errorWindow = new Controller_ErrorMsg("INSUFFICIENT INPUTS!");
            errorWindow.showStage();

        }
        else{
            Parcel parcel = new Parcel(listItem, type, insurance);
            Customer customer = new Customer(recipientName, delRegion, parcel);
            transaction.setRecipient(customer);

            // open new scene
            Controller_FeeDisplay displayController = new Controller_FeeDisplay(cal, thisStage, transaction, seqNum);
            displayController.showStage();
        }

    }

    public void displayTime(){
        Thread clock = new Thread(){
            public void run(){
                for(;;){

                    // speed of date per second

                    int month = cal.get(Calendar.MONTH);
                    int year = cal.get(Calendar.YEAR);
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    txtDate.setText("Date: " + year + "/" + month + "/" + day);

                    int second = cal.get(Calendar.SECOND);
                    int minute = cal.get(Calendar.MINUTE);
                    int hour = cal.get(Calendar.HOUR);
                    txtTime.setText("Time: " + hour + ":" + minute + ":" + second);
                    try{
                        sleep(1000);
                    } catch (InterruptedException ex){

                    }
                }
            }
        };
        clock.start();
    }


}
