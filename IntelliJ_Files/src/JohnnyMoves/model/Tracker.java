package JohnnyMoves.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The class Tracker represents a Tracker object with a tracking number, month, day, year, delivery region, and delivery status.
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Tracker {

    private String trackingNum;

    private String month;
    private String day;
    private String year;

    private String delRegion;
    private String status;

    /**
     * This constructor takes in the tracking number, month, day, year, and delivery region.
     *
     * @param trackingNum
     * @param month
     * @param day
     * @param year
     * @param delRegion
     */
    public Tracker(String trackingNum, String month, String day, String year, String delRegion){
        this.trackingNum = trackingNum;
        this.month = month;
        this.day = day;
        this.year = year;
        this.delRegion = delRegion;
    }

    /**
     * This method computes the difference in days of two dates.
     *
     * @param currCal - Calendar object
     * @return difference in days
     */
    public long determineDateDifference(Calendar currCal){
        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
        String dateTrans = this.month + "/" + this.day + "/" + this.year;
        String dateCurr = String.format("%02d", currCal.get(Calendar.MONTH)) + "/" + String.format("%02d", currCal.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d", currCal.get(Calendar.YEAR));

        long diff = 0;
        try {
            Date date1 = myFormat.parse(dateTrans);
            Date date2 = myFormat.parse(dateCurr);
            diff = date2.getTime() - date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * This method sets the status of the tracker based on the current date.
     *
     * @param currCal - Calendar object
     */
    public void setStatus(Calendar currCal){
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

    /**
     * This method gets the tracking number.
     * @return tracking number
     */
    public String getTrackingNum() {
        return trackingNum;
    }

    /**
     * This method gets the tracking status.
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method gets the delivery region.
     *
     * @return delivery region
     */
    public String getDelRegion() {
        return delRegion;
    }
}
