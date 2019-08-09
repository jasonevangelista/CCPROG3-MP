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

public class Controller_MainMenu {

    @FXML private Text title;
    @FXML private Text txtDate;
    @FXML private Text txtTime;
    @FXML private Text enterPassword;

    private final Stage thisStage;

    private int seqNum;

    private Calendar cal;

    private String prevMonth, prevDay, prevYear;


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

    public void showStage() {
        displayTime();
        thisStage.show();
    }

    @FXML private void switchToExitMenu(ActionEvent event){
        Controller_ExitMenu controller_exitMenu = new Controller_ExitMenu(thisStage, cal);
        controller_exitMenu.showStage();
    }

    @FXML private void switchToTracker(ActionEvent event){
        Controller_Track controller_track = new Controller_Track(cal, thisStage);
        controller_track.showStage();
    }

    @FXML private void startTransaction(ActionEvent event){
        Controller_Transaction controller_transaction = new Controller_Transaction(cal, thisStage, seqNum);
        controller_transaction.showStage();
    }

    public void displayTime(){
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
                        } catch (InterruptedException ex){

                        }
                }
            }
        };
        clock.start();
    }

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
