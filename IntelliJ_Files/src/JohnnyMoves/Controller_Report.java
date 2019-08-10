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

/**
 * The Controller_Report class is a controller for the ReportDisplay view.
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Controller_Report {

    @FXML private Text txtCurrDate;
    @FXML private ListView<String> listPreparing = new ListView<>();
    @FXML private ListView<String> listShipping = new ListView<>();
    @FXML private ListView<String> listDelivered = new ListView<>();

    private Calendar cal;
    private Stage thisStage;

    /**
     * This constructor takes in a Calendar object and a Stage object.
     *
     * @param cal - Calendar object
     * @param stage - Stage object
     */
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

    /**
     * This method shows the stage object.
     */
    public void showStage() {
        txtCurrDate.setText("As of: " + cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH));

        ReadWholeFile();
        thisStage.show();
    }

    /**
     * This method reads the whole file and  separates each status of each transaction.
     */
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

    /**
     * This method switches the stage to the main menu.
     *
     * @param event - ActionEvent object
     */
    @FXML private void switchToMainMenu(ActionEvent event){
        // load main menu scene
        Controller_MainMenu controller_mainMenu = new Controller_MainMenu(thisStage, cal, 1);

        controller_mainMenu.showStage();
    }
}
