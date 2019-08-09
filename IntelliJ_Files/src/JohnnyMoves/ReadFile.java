package JohnnyMoves;

import java.util.Scanner;
import java.io.*;

public class ReadFile {

//    private String trackingNum;
    private String month;
    private String day;
    private String year;
    private String delRegion;

    private Scanner sc;

    private String temp;

    public void openFile(){
        try {
            sc = new Scanner(new File("trackinfo.txt"));
        }catch (Exception e){
            System.out.println("error cant find file");
        }
    }

    public void readFile(String trackingNum){
        while(sc.hasNext()){
            temp = sc.next();

            if(temp.equalsIgnoreCase(trackingNum)){
                month = sc.next();
                day = sc.next();
                year = sc.next();
                delRegion = sc.next();
                break;
            }
        }

    }

    public void closeFile(){
        sc.close();
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getYear() {
        return year;
    }

    public String getDelRegion() {
        return delRegion;
    }
}
