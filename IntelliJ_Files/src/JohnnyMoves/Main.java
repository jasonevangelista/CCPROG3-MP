package JohnnyMoves;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main extends Application {

    Calendar cal = new GregorianCalendar();
    int seqNum = 1;
    Stage newStage = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception{

        simulateTime();
        Controller_MainMenu controller_mainMenu = new Controller_MainMenu(newStage, cal, seqNum);
        controller_mainMenu.showStage();

        window.setOnCloseRequest(windowEvent -> window.close());
    }

    public void simulateTime(){
        Thread clock = new Thread(){
            public void run(){
                for(;;){

                    // speed of date per second
                    cal.add(Calendar.HOUR, 1);

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
