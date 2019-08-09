package JohnnyMoves;

import java.io.FileWriter;
import java.util.Formatter;

public class WriteFile {

    private Formatter file;
//
    public void openFile(){
        try {
            FileWriter f = new FileWriter("trackinfo.txt", true);
            file = new Formatter(f);

        } catch(Exception e) {
            System.out.println("error cant write file");
        }
    }



    public void addData(String trackingNum, String month, String day, String year, String delRegion){
        file.format("%s%s%s%s%s\n", trackingNum + " ", month + " ", day + " ", year + " ", delRegion);
    }

    public void closeFile(){
        file.close();
    }
}
