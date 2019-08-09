package JohnnyMoves.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Tracker {

    private String trackingNum;

    private String month;
    private String day;
    private String year;

    private String delRegion;

    private String status;

    public Tracker(String trackingNum, String month, String day, String year, String delRegion){
        this.trackingNum = trackingNum;
        this.month = month;
        this.day = day;
        this.year = year;
        this.delRegion = delRegion;
    }

    private long determineDateDifference(Calendar currCal){
        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateTrans = this.month + "/" + this.day + "/" + this.year;
        String dateCurr = String.format("%02d", currCal.get(Calendar.MONTH)) + "/" + String.format("%02d", currCal.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d", currCal.get(Calendar.YEAR));

        long diff = 0;
        try {
            Date date1 = myFormat.parse(dateTrans);
            Date date2 = myFormat.parse(dateCurr);
            diff = date2.getTime() - date1.getTime();
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    private void setStatus(Calendar currCal){
        long dateDiff = determineDateDifference(currCal) + 1;

        if(this.delRegion.equalsIgnoreCase("MML")){
            if(dateDiff == 1)
                this.status = "Preparing";
            else
                this.status = "Delivered";
        }
        else if(this.delRegion.equalsIgnoreCase("LUZ")){
            if(dateDiff == 1)
                this.status = "Preparing";
            else if(dateDiff == 2)
                this.status = "Shipping";
            else
                this.status = "Delivered";
        }
        else if(this.delRegion.equalsIgnoreCase("VIS")){
            if(dateDiff == 1)
                this.status = "Preparing";
            else if(dateDiff > 1 & dateDiff < 5)
                this.status = "Shipping";
            else
                this.status = "Delivered";

        }
        else if(this.delRegion.equalsIgnoreCase("MIN")){
            if(dateDiff == 1)
                this.status = "Preparing";
            else if(dateDiff > 1 && dateDiff < 8)
                this.status = "Shipping";
            else
                this.status = "Delivered";
        }
    }

    public String getTrackingNum() {
        return trackingNum;
    }

    public String getStatus() {
        return status;
    }
}
