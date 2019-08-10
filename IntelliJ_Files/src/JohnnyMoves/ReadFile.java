package JohnnyMoves;

import java.util.Scanner;
import java.io.*;

/**
 * The ReadFile class represents a ReadFile object which reads the text file "trackinfo.txt"
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class ReadFile {

    private String trackingNum;
    private String month;
    private String day;
    private String year;
    private String delRegion;

    private Scanner sc;

    private String temp;

    /**
     * This method opens the text file "trackinfo.txt".
     */
    public void openFile(){
        try {
            sc = new Scanner(new File("trackinfo.txt"));
        }catch (Exception e){
            System.out.println("error cant find file");
        }
    }

    /**
     * This method reads the text file and stores the data in strings.
     *
     * @param trackingNum - Tracking number
     */
    public void readFile(String trackingNum){
        while(sc.hasNext()){
            temp = sc.next();

            if(temp.equalsIgnoreCase(trackingNum)){
                this.trackingNum = temp;
                month = sc.next();
                day = sc.next();
                year = sc.next();
                delRegion = sc.next();
                break;
            }
        }

    }

    /**
     * This method closes the Scanner object.
     */
    public void closeFile(){
        sc.close();
    }

    /**
     * This method gets the tracking number.
     *
     * @return tracking number
     */
    public String getTrackingNum() {
        return trackingNum;
    }

    /**
     * This method gets the month from the file.
     *
     * @return month
     */
    public String getMonth() {
        return month;
    }

    /**
     * This method gets the day from the file.
     *
     * @return day
     */
    public String getDay() {
        return day;
    }

    /**
     * This method gets the year from the file.
     *
     * @return year
     */
    public String getYear() {
        return year;
    }

    /**
     * This method gets the delivery region from the file.
     *
     * @return delivery region
     */
    public String getDelRegion() {
        return delRegion;
    }
}
