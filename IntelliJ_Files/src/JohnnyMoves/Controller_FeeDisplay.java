package JohnnyMoves;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;

import javafx.scene.text.Text;

import JohnnyMoves.model.*;

/**
 * The Controller_FeeDisplay class is a controller for the FeeDisplay view.
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Controller_FeeDisplay {

    private Stage thisStage;

    @FXML private Text txtBase;
    @FXML private Text txtService;
    @FXML private Text txtInsurance;
    @FXML private Text txtTotal;
    @FXML private TextField txtTrackingNum;
    @FXML private Text txtDate;
    @FXML private Text txtTime;

    private Transaction transaction;
    private String trackingNum;
    private Calendar cal;
    private int seqNum;
    private String prevMonth;
    private String prevDay;
    private String prevYear;

    /**
     * This constructor takes in a Calendar object, Stage object, Transaction object, and a sequence number.
     *
     * @param cal - Calendar object
     * @param stage - Stage object
     * @param transaction - Transaction object
     * @param seqNum - Sequence number
     */
    public Controller_FeeDisplay(Calendar cal, Stage stage, Transaction transaction, int seqNum){
        this.transaction = transaction;
        thisStage = stage;
        this.cal = cal;
        this.seqNum = seqNum;


        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FeeDisplay.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Johnny Moves - Transaction");

        } catch (IOException e) {
            e.printStackTrace();
        }

        prevMonth = String.format("%02d", cal.get(Calendar.MONTH));
        prevDay = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));
        prevYear = String.format("%02d", cal.get(Calendar.YEAR));

    }

    /**
     * This method shows the stage object.
     */
    public void showStage() {
        displayFees();
        displayTime();
        displayTrackingNum();
        writeDataToFile();
        thisStage.show();
    }

    /**
     * This method writes the tracking information in a text file "trackinfo.txt".
     */
    private void writeDataToFile(){
        WriteFile file = new WriteFile();

        file.openFile();
        file.addData(trackingNum, String.format("%02d",cal.get(Calendar.MONTH)), String.format("%02d",cal.get(Calendar.DAY_OF_MONTH)), String.format("%02d",cal.get(Calendar.YEAR)), transaction.getRecipient().getDelRegion());
        file.closeFile();
    }

    /**
     * This method displays the different sub fees and total fee.
     */
    private void displayFees(){

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);

        Parcel parcel = transaction.getRecipient().getParcel();
        parcel.addRegionDetails(transaction.getRecipient(), transaction);

        double baseFee = parcel.computeBaseFee(parcel.getType(), parcel.getListItem());
        double serviceFee = parcel.getServiceFee();
        double insuranceFee = parcel.computeInsuranceFee(parcel.getInsurance());

        String base = "PHP " + df.format(baseFee);
        String service = "PHP " + df.format(serviceFee);
        String insurance = "PHP " + df.format(insuranceFee);
        String total = "PHP " + df.format(parcel.computeTotalFee(baseFee, serviceFee, insuranceFee));

        txtBase.setText(base);
        txtService.setText(service);
        txtInsurance.setText(insurance);
        txtTotal.setText(total);

    }

    /**
     * This method displays the tracking number.
     */
    private void displayTrackingNum(){
        trackingNum = transaction.generateTrackingNum(transaction.getRecipient(), seqNum, cal);
        txtTrackingNum.setText(trackingNum);
    }

    /**
     * This method constantly displays the current date and time.
     */
    private void displayTime(){
        Thread clock = new Thread(){
            public void run(){
                for(;;){
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

    /**
     * This method opens the MainMenu view.
     *
     * @param event - ActionEvent object
     */
    @FXML private void returnToMainMenu(ActionEvent event){

        // load main menu scene
        Controller_MainMenu controller_mainMenu = new Controller_MainMenu(thisStage, cal, seqNum, prevMonth, prevDay, prevYear);

        controller_mainMenu.showStage();
    }

}
