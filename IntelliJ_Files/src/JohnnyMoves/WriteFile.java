package JohnnyMoves;

import java.io.FileWriter;
import java.util.Formatter;

/**
 * The WriteFile class represents a WriteFile object which appends the text file "trackinfo.txt"
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class WriteFile {

    private Formatter file;

    /**
     * This method opens the text file "trackinfo.txt".
     */
    public void openFile(){
        try {
            FileWriter f = new FileWriter("trackinfo.txt", true);
            file = new Formatter(f);

        } catch(Exception e) {
            System.out.println("error cant write file");
        }
    }

    /**
     * This method appends new data to the text file.
     *
     * @param trackingNum - Tracking number
     * @param month
     * @param day
     * @param year
     * @param delRegion - delivery region
     */
    public void addData(String trackingNum, String month, String day, String year, String delRegion){
        file.format("%s%s%s%s%s\n", trackingNum + " ", month + " ", day + " ", year + " ", delRegion);
    }

    /**
     * This method closes the Scanner object.
     */
    public void closeFile(){
        file.close();
    }
}
