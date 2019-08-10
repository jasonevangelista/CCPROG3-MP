package JohnnyMoves;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.io.IOException;

/**
 * The Controller_MainMenu class is a controller for the MainMenu view.
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Controller_MainMenu {

    @FXML private ImageView title;
    @FXML private Text txtDate;
    @FXML private Text txtTime;
    @FXML private Text enterPassword;

    private final Stage thisStage;
    private int seqNum;
    private Calendar cal;
    private String prevMonth, prevDay, prevYear;


    /**
     * This constructor takes in a Stage object, Calendar object, and sequence number for the day.
     *
     * @param stage - Stage object
     * @param cal - Calendar object
     * @param seqNum - daily sequence number
     */
    public Controller_MainMenu(Stage stage, Calendar cal, int seqNum){
        thisStage = stage;
        this.cal = cal;
        this.seqNum = seqNum;

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

    /**
     * This constructor takes in a Stage object, Calendar object,sequence number for the day, and day, month, and year of
     * previous transaction.
     *
     * @param stage - Stage object
     * @param cal - Calendar object
     * @param seqNum - daily sequence number
     * @param prevMonth - month of last transaction
     * @param prevDay - day of last transaction
     * @param prevYear - year of last transaction
     */
    public Controller_MainMenu(Stage stage, Calendar cal, int seqNum, String prevMonth, String prevDay, String prevYear){
        thisStage = stage;
        this.cal = cal;
        this.seqNum = seqNum;

        this.prevMonth = prevMonth;
        this.prevDay = prevDay;
        this.prevYear = prevYear;

        // resets sequence number if day has changed
        if(determineDateDifference() > 0){
            this.seqNum = 1;
            System.out.println("seqnum reset to - " + this.seqNum);

        }
        else{
            this.seqNum++;
            System.out.println("seqnum incremented - " + this.seqNum);
        }

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

    /**
     * This method shows the stage object.
     */
    public void showStage() {
        displayTime();
        thisStage.show();
    }

    /**
     * This fxml method switches the current stage to the exit menu view.
     *
     * @param event - ActionEvent object
     */
    @FXML private void switchToExitMenu(ActionEvent event){
        Controller_ExitMenu controller_exitMenu = new Controller_ExitMenu(thisStage, cal);
        controller_exitMenu.showStage();
    }

    /**
     * This fxml method switches the current stage to the tracker view.
     *
     * @param event - ActionEvent object
     */
    @FXML private void switchToTracker(ActionEvent event){
        Controller_Track controller_track = new Controller_Track(cal, thisStage);
        controller_track.showStage();
    }

    /**
     * This fxml method switches the current stage to the transaction menu view.
     *
     * @param event - ActionEvent object
     */
    @FXML private void startTransaction(ActionEvent event){
        Controller_Transaction controller_transaction = new Controller_Transaction(cal, thisStage, seqNum);
        controller_transaction.showStage();
    }

    /**
     * This fxml method switches the current stage to the report display view.
     *
     * @param event - ActionEvent object
     */
    @FXML private void switchToReportDisplay(ActionEvent event){
        Controller_Report controller_report = new Controller_Report(cal, thisStage);
        controller_report.showStage();
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
                    if(hour == 0)
                        hour = 12;
                    txtTime.setText("Time: " + String.format("%02d", hour) + ":" + minute + ":" + second);

                        try{
                            sleep(1000);
                        } catch (InterruptedException e){

                        }
                }
            }
        };
        clock.start();
    }

    /**
     * This method determines the difference of days.
     *
     * @return date difference
     */
    private long determineDateDifference(){
        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
        String datePrev = prevMonth + "/" + prevDay + "/" + prevYear;
        String dateCurr = String.format("%02d", cal.get(Calendar.MONTH)) + "/" + String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d", cal.get(Calendar.YEAR));

        long diff = 0;
        try {
            Date date1 = myFormat.parse(datePrev);
            Date date2 = myFormat.parse(dateCurr);
            diff = date2.getTime() - date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
