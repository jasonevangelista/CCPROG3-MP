package JohnnyMoves;

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

/**
 * The Controller_Track class is a controller for the TrackMenu view.
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Controller_Track {

    @FXML private TextField txtTrackNum;
    @FXML private Text txtDateOfTransaction;
    @FXML private Text txtCurrDate;
    @FXML private Text txtDelRegion;
    @FXML private Text txtStatus;

    private Calendar cal;
    private Stage thisStage;
    private ReadFile readFile;

    /**
     * This controller takes in a Calendar object and Stage object.
     *
     * @param cal - Calendar object
     * @param stage - Stage object
     */
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

    /**
     * This method shows the stage object.
     */
    public void showStage() {
        thisStage.show();
    }

    /**
     * This method switched the current stage to the main menu.
     *
     * @param event - ActionEvent object
     * @throws IOException
     */
    @FXML private void switchToMainMenu(ActionEvent event) throws IOException {
        Controller_MainMenu controller_mainMenu = new Controller_MainMenu(thisStage, cal, 1);

        controller_mainMenu.showStage();
    }

    /**
     * This method searches the tracking number in the text file to check its status.
     *
     * @param event - ActionEvent object
     */
    @FXML private void trackParcel(ActionEvent event){
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

    /**
     * This method sets the calendar dates in the Text displays.
     */
    private void setData(){
        txtDateOfTransaction.setText(readFile.getMonth() + "/" + readFile.getDay() + "/" + readFile.getYear());
        txtCurrDate.setText(String.format("%02d", cal.get(Calendar.MONTH)) + "/" + String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d", cal.get(Calendar.YEAR)));
        txtDelRegion.setText(readFile.getDelRegion());
    }

    /**
     * This method computes the difference in days of two dates.
     *
     * @return day difference
     */
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

    /**
     * This method sets the status of the parcel.
     */
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
