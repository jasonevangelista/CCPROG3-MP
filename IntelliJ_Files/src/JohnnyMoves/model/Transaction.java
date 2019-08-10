package JohnnyMoves.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * The Transaction class represents a Transaction object with a transaction date, number of delivery days, tracking number,
 * Date object, Customer object, and Calendar object.
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Transaction {

    private     static int      MAX_FLAT_WEIGHT = 3; // in kg 
    private     static int      FLAT[][] = {{-1, 9, 14, 1},{0, 12, 18, 3}}; // format: flat #, length, width, max thickness
    private     static int      BOX[][] = {{1, 12, 10, 5}, {2, 14, 11, 7}, 
                                           {3, 18, 12, 9}, {4, 20, 16, 12}}; // format: box #, length, width, height
    // Symbol of index 0 of arrays FLAT[] and BOX[]: 
    // -1 == FLT1, 0 == FLT2, 1  == BOX1, 2  == BOX2, 3  == BOX3, 4  == BOX4

    private String dateOfTransaction;
    private int deliveryDays;
    private String trackingNumber;
    private Date date;
    private Customer recipient;
    private Calendar calendarDate;

    /**
     * This method generates all possible combinations of the dimensions of an item.
     * 
     * @param length The length of the item
     * @param width The width of the item
     * @param height The height of the item
     * @return The 2D-arrays of all possible rotation combinations
     */
    public int[][] generateRotations(int length, int width, int height){
        int rotations[][] = new int[][]
        {
            {length, width, height},
            {length, height, width},
            {width, length, height},
            {width, height, length},
            {height, length, width},
            {height, width, length}
         };

         return rotations;
    }

    /**
     * This method computes the total volume of the list of items.
     *
     * @param listItem - ArrayList of items
     * @return total volume
     */
    public int computeTotalVolume(ArrayList<Item> listItem){
        int volume = 0;
        for(int i = 0; i < listItem.size(); i++){
            volume += listItem.get(i).getVolume();
        }
        return volume;
    }

    /**
     * This method computes the total weight of the list of items.
     *
     * @param listItem - ArrayList of items
     * @return total weight
     */
    public double computeTotalWeight(ArrayList<Item> listItem){
        int weight = 0;
        for(int i = 0; i < listItem.size(); i++){
            weight += listItem.get(i).getWeight();
        }
        return weight;
    }


    /**
     * This method determines if the list of items will fit the specified dimensions.
     *
     * @param listItem - ArrayList of Item objects
     * @param parcelLength - length
     * @param parcelWidth - width
     * @param parcelHeight - height
     * @return true if fits parcel, false if doesn't fit parcel
     */
    public boolean fitsInParcel(ArrayList<Item> listItem, int parcelLength, int parcelWidth, int parcelHeight){
        if(listItem.size() == 0){
            return true;
        }

        else{
            int boxVolume = parcelLength * parcelWidth * parcelHeight;
            int[][] rotations = generateRotations((int)Math.ceil(listItem.get(0).getLength()), (int)Math.ceil(listItem.get(0).getWidth()), (int)Math.ceil(listItem.get(0).getHeight()));
            boolean isValid = false;


            if(computeTotalVolume(listItem) > boxVolume)
                return false;

            else{
                 // check if any of the 6 possible rotations of the item will fit the parcel, if found, break from loop and use those dimensions in checking space for next item (recursion)
                 int i;
                 for(i = 0; i < 6; i++){
                     if(rotations[i][0] <= parcelLength && rotations[i][1] <= parcelWidth && rotations[i][2] <= parcelHeight){
                        Item prevItem = listItem.get(0);
                        listItem.remove(0);

                        //partial parcel #1
                        if(fitsInParcel(listItem, parcelLength - (int)Math.ceil(prevItem.getLength()), parcelWidth, (int)Math.ceil(prevItem.getHeight())))
                            return true;

                        //partial parcel #2
                        else if(fitsInParcel(listItem, (int)Math.ceil(prevItem.getLength()), parcelWidth - (int)Math.ceil(prevItem.getWidth()), (int)Math.ceil(prevItem.getHeight())))
                            return true;

                        //partial parcel #3
                        else if(fitsInParcel(listItem, parcelLength, parcelWidth, parcelHeight - (int)Math.ceil(prevItem.getHeight())))
                            return true;

                        else
                            listItem.add(0, prevItem);
                     }
                 }
                 return false;
            }
        }
    }

    /**
     * This method returns the list of parcel types that are valid based on the list of items.
     *
     * @param listItem - ArrayList of Item objects
     * @return ArrayList of Strings of valid parcels
     */
    public ArrayList<String> determineValidTypes(ArrayList<Item> listItem){
        ArrayList<String> validTypes = new ArrayList<>();

        Collections.sort(listItem, new Comparator<Item>(){
            public int compare(Item i1, Item i2){
                return Integer.valueOf((int)Math.ceil(i2.getVolume())).compareTo((int)Math.ceil(i1.getVolume()));
            }
        });

        if(computeTotalWeight(listItem) <= 3) {
            for (int i = 0; i < 2; i++) {
                if (fitsInParcel(listItem, FLAT[i][1], FLAT[i][2], FLAT[i][3])) {
                    if (FLAT[i][0] == -1)
                        validTypes.add("FLT1");
                    else
                        validTypes.add("FLT2");
                }
            }
        }

        for(int i = 0; i < 4; i++){
            if(fitsInParcel(listItem, BOX[i][1], BOX[i][2], BOX[i][3])){
                if(BOX[i][0] == 1)
                    validTypes.add("BOX1");
                else if(BOX[i][0] == 2)
                    validTypes.add("BOX2");
                else if(BOX[i][0] == 3)
                    validTypes.add("BOX3");
                else 
                    validTypes.add("BOX4");
            }
        }
        return validTypes;
    }

    /**
     * This method generates the tracking number of the parcel composed of its
     * type, date of transaction, destination region, number of items, amd sequence number.
     *
     * @param customer the Customer object
     * @param seqNum The sequence number of the parcel 
     * @param cal the Calendar object
     * @return The tracking number
     */
    public String generateTrackingNum(Customer customer, int seqNum, Calendar cal){
        this.calendarDate = cal;
        String track = "";
        Parcel parcel = customer.getParcel();

        if(parcel.getType().equals("FLT1") || parcel.getType().equalsIgnoreCase("FLT2"))
            track += "FLT";
        else
            track += "BOX";

        track += String.format("%02d",cal.get(Calendar.MONTH)) + String.format("%02d",cal.get(Calendar.DAY_OF_MONTH));
        track += customer.getDelRegion();
        track += String.format("%02d", parcel.getListItem().size());
        track += String.format("%03d",seqNum);

        this.trackingNumber = track;
        return track;
    }

    /**
     * This method gets the tracking number.
     *
     * @return the trackingNumber
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /**
     * This method gets the transaction date.
     *
     * @return the dateOfTransaction
     */
    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    /**
     * This method gets the date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method gets the delivery days.
     *
     * @return the deliveryDays
     */
    public int getDeliveryDays() {
        return deliveryDays;
    }

    public Calendar getCalendarDate(){
        return calendarDate;
    }


    /**
     * This method sets the tracking number.
     *
     * @param trackingNumber the trackingNumber to set
     */
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    /**
     * This method sets the calendar date to the format MM/dd/yyyy.
     *
     * @param cal - Calendar object
     */
    public void setCalendarDate(Calendar cal){
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        this.date = cal.getTime();
        this.dateOfTransaction = dateFormat.format(cal.getTime());
    }

    /**
     * This method sets the customer object.
     *
     * @param recipient - Customer object
     */
    public void setRecipient(Customer recipient) {
        this.recipient = recipient;
    }

    /**
     * This method gets the customer object.
     *
     * @return customer object
     */
    public Customer getRecipient() {
        return recipient;
    }

    /**
     * This method sets the delivery days.
     *
     * @param deliveryDays - number of delivery days
     */
    public void setDeliveryDays(int deliveryDays) {
        this.deliveryDays = deliveryDays;
    }
}