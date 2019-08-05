import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Transaction
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

    public Transaction(){

    }

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

    public int computeTotalVolume(ArrayList<Item> listItem){
        int volume = 0;
        for(int i = 0; i < listItem.size(); i++){
            volume += listItem.get(i).volume;
        }
        return volume;
    }

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

    public ArrayList<String> determineValidTypes(ArrayList<Item> listItem){
        ArrayList<String> validTypes = new ArrayList<>();

        Collections.sort(listItem, new Comparator<Item>()
        {
            public int compare(Item i1, Item i2){
                return Integer.valueOf((int)Math.ceil(i2.getVolume())).compareTo((int)Math.ceil(i1.getVolume()));
            }
        });
        
        for(int i = 0; i < 2; i++){
            if(fitsInParcel(listItem, FLAT[i][1], FLAT[i][2], FLAT[i][3])){
                if(FLAT[i][0] == -1)
                    validTypes.add("FLT1");
                else
                    validTypes.add("FLT2");
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
     * This method displays the delivery status of the parcel based on the delivery days
     * and the current date.
     * 
     * @param deliveryDays The number of days it takes the parcel to be delivered
     * @param diffDays The difference between the date of transaction and the current date
     */
    public void displayDeliveryStatus(int deliveryDays, int diffDays){
        if(diffDays == 1)
            System.out.println("Preparing");
        else{
            if(diffDays < deliveryDays)
                System.out.println("Shipping");
            else
                System.out.println("Delivered");
        }
    }

    /**
     * This method generates the tracking number of the parcel composed of its
     * type, date of transaction, destination region, number of items, amd sequence number.
     * 
     * @param seqNum The sequence number of the parcel 
     * @param date The date of transaction of the parcel
     * @return The tracking number
     */
    public String generateTrackingNum(Customer customer, int seqNum, String date){
        String track = "";
        Parcel parcel = customer.getParcel();
        if(parcel.getType().equals("FLT1") || parcel.getType().equalsIgnoreCase("FLT2"))
            track += "FLT";
        else
            track += "BOX";

        track += date;
        track += customer.getDelRegion();
        track += String.format("%02d", parcel.getListItem().size());
        track += String.format("%03d",seqNum);

        return track;
    }

    /**
     * @return the trackingNumber
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /**
     * @return the dateOfTransaction
     */
    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the deliveryDays
     */
    public int getDeliveryDays() {
        return deliveryDays;
    }

    public Calendar getCalendarDate(){
        return calendarDate;
    }


    /**
     * @param trackingNumber the trackingNumber to set
     */
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    /**
     * This method sets the calendar date to the format MM/dd/yyyy.
     */
    public void setCalendarDate(Calendar cal){
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        this.date = cal.getTime();
        this.dateOfTransaction = dateFormat.format(cal.getTime());
    }




}