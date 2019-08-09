package JohnnyMoves;

import JohnnyMoves.model.Tracker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.text.Text;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Controller_Track {

    @FXML
    private TextField txtTrackNum;

    @FXML private Text txtDateOfTransaction;
    @FXML private Text txtCurrDate;
    @FXML private Text txtDelRegion;

    @FXML private Text txtStatus;


    Calendar cal;
    Calendar transactionCal;
    Stage thisStage;
    ReadFile readFile;

    public Controller_Track(Calendar cal, Stage stage){
        thisStage = stage;
        this.cal = cal;

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrackMenu.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Johnny Moves - Transaction Tracker");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.show();
    }

    @FXML
    private void switchToMainMenu(ActionEvent event) throws IOException {
        // load main menu scene
        Controller_MainMenu controller_mainMenu = new Controller_MainMenu(thisStage, cal, 1);

        controller_mainMenu.showStage();
    }

    @FXML
    private void trackParcel(ActionEvent event){
        String trackNum = txtTrackNum.getText();

        readFile = new ReadFile();

        readFile.openFile();
        readFile.readFile(trackNum);
        readFile.closeFile();


        if(readFile.getTrackingNum() == null){
            Controller_ErrorMsg errorMsg = new Controller_ErrorMsg("Invalid Tracking Number!");
            errorMsg.showStage();
        }
        else{
            setData();
            setStatus();
        }

    }

    private void setData(){
        txtDateOfTransaction.setText(readFile.getMonth() + "/" + readFile.getDay() + "/" + readFile.getYear());
        txtCurrDate.setText(String.format("%02d", cal.get(Calendar.MONTH)) + "/" + String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d", cal.get(Calendar.YEAR)));
        txtDelRegion.setText(readFile.getDelRegion());

//        transactionCal.set(Integer.parseInt(readFile.getYear()),Integer.parseInt(readFile.getMonth()), Integer.parseInt(readFile.getDay()));

    }

    private long determineDateDifference(){
        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateTrans = readFile.getMonth() + "/" + readFile.getDay() + "/" + readFile.getYear();
        String dateCurr = String.format("%02d", cal.get(Calendar.MONTH)) + "/" + String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d", cal.get(Calendar.YEAR));

        long diff = 0;
        try {
            Date date1 = myFormat.parse(dateTrans);
            Date date2 = myFormat.parse(dateCurr);
            diff = date2.getTime() - date1.getTime();
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    private void setStatus(){
        long dateDiff = determineDateDifference() + 1;

        if(txtDelRegion.getText().equalsIgnoreCase("MML")){
            if(dateDiff == 1)
                txtStatus.setText("Preparing");
            else
                txtStatus.setText("Delivered");
        }
        else if(txtDelRegion.getText().equalsIgnoreCase("LUZ")){
            if(dateDiff == 1)
                txtStatus.setText("Preparing");
            else if(dateDiff == 2)
                txtStatus.setText("Shipping");
            else
                txtStatus.setText("Delivered");
        }
        else if(txtDelRegion.getText().equalsIgnoreCase("VIS")){
            if(dateDiff == 1)
                txtStatus.setText("Preparing");
            else if(dateDiff > 1 & dateDiff < 5)
                txtStatus.setText("Shipping");
            else
                txtStatus.setText("Delivered");

        }
        else if(txtDelRegion.getText().equalsIgnoreCase("MIN")){
            if(dateDiff == 1)
                txtStatus.setText("Preparing");
            else if(dateDiff > 1 && dateDiff < 8)
                txtStatus.setText("Shipping");
            else
                txtStatus.setText("Delivered");
        }
    }

}
