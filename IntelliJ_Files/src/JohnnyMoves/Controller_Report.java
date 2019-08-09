package JohnnyMoves;

import JohnnyMoves.model.Tracker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

public class Controller_Report {

    @FXML private Text txtCurrDate;
    @FXML private ListView<String> listPreparing = new ListView<>();
    @FXML private ListView<String> listShipping = new ListView<>();
    @FXML private ListView<String> listDelivered = new ListView<>();

    private Calendar cal;
    private Stage thisStage;

    public Controller_Report(Calendar cal, Stage stage){
        this.cal = cal;
        thisStage = stage;

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReportDisplay.fxml"));

            // Set this class as the controller
            loader.setController(this);

            // Load the scene
            thisStage.setScene(new Scene(loader.load()));

            // Setup the window/stage
            thisStage.setTitle("Johnny Moves - Report");

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void showStage() {
        txtCurrDate.setText("As of: " + cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH));

        ReadWholeFile();
        thisStage.show();
    }

    private void ReadWholeFile(){
        Scanner sc = null;
        String trackingNum;
        String month;
        String day;
        String year;
        String delRegion;

        try {
            sc= new Scanner(new File("trackinfo.txt"));
        }catch (Exception e){

        }

        while(sc.hasNext()){
            trackingNum = sc.next();
            month = sc.next();
            day = sc.next();
            year = sc.next();
            delRegion = sc.next();

            Tracker tracker = new Tracker(trackingNum, month, day, year, delRegion);
            tracker.setStatus(cal);

            if(tracker.getStatus().equalsIgnoreCase("Preparing"))
                listPreparing.getItems().add(tracker.getTrackingNum() + " (" + tracker.getDelRegion() + ")");
            else if(tracker.getStatus().equalsIgnoreCase("Shipping"))
                listShipping.getItems().add(tracker.getTrackingNum() + " (" + tracker.getDelRegion() + ")");
            else
                listDelivered.getItems().add(tracker.getTrackingNum() + " (" + tracker.getDelRegion() + ")");
        }
    }

    @FXML
    private void switchToMainMenu(ActionEvent event) throws IOException {
        // load main menu scene
        Controller_MainMenu controller_mainMenu = new Controller_MainMenu(thisStage, cal, 1);

        controller_mainMenu.showStage();
    }





}
