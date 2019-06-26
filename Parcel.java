import java.util.ArrayList;

/**
 * Parcel
 */
public class Parcel {

    private     String          recipientName;
    private     String          delRegion;
    private     int             quantity;
    private     ArrayList<Item> listItem;
    private     String          type;
    private     int             seqNum;
    private     double          totalPrice = 0;
    private     double          serviceFee;
    private     double          insuranceFee;
    private     double          totalWeight = 0;
    private     double          totalVolume = 0;
    private     int             deliveryDays;
    private     boolean         insurance;

    private     static int      FLAT[][] = {{1, 9, 14, 1},{2, 12, 18, 3}};
                                // format: flat #, length, width, max thickness
    private     static int      MAX_FLAT_WEIGHT = 3; // in kg 
    
    private     static int      BOX[][] = {{1, 12, 10, 5}, {2, 14, 11, 7}, {3, 18, 12, 9}, {4, 20, 16, 12}};
                                // format: box #, length, width, height

    /**
     * This constructor takes in the parcel's recipient name, destination region,
     * quantity of items, list of items, sequence number, and insurance status.
     * 
     * @param name - recipient's name
     * @param region - parcel's destination region
     * @param numItem - number of items in parcel
     * @param listItem - list of Item objects
     * @param seqNum - parcel's sequence number
     * @param insurance - true/false whether parcel will apply insurance
     */
    public Parcel(String name, String region, int numItem, ArrayList<Item> listItem,
                  int seqNum, boolean insurance){
        this.recipientName = name;
        this.delRegion = region;
        this.quantity = numItem;
        this.listItem = listItem;
        this.seqNum = seqNum;
        this.insurance = insurance;

        this.addRegionDetails();
        this.computeInsuranceFee();
    }

    /**
     * This method determines the number of delivery days of the parcel and
     * the service fee depending on the region.
     */
    public void addRegionDetails(){
        if(this.delRegion.equalsIgnoreCase("Metro Manila")){
            this.deliveryDays = 2;
            this.serviceFee = 50;
        }
        else if(this.delRegion.equalsIgnoreCase("Provincial Luzon")){
            this.deliveryDays = 3;
            this.serviceFee = 100;
        }
        else if(this.delRegion.equalsIgnoreCase("Visayas")){
            this.deliveryDays = 5;
            if(this.totalVolume > this.totalWeight){
                if(this.totalVolume * 0.10 > 1000)
                    this.serviceFee = this.totalVolume * 0.10;
                else
                    this.serviceFee = 1000;
            }
            else{
                if(this.totalWeight * 0.10 > 1000)
                    this.serviceFee = this.totalWeight * 0.10;
                else
                    this.serviceFee = 1000;
            }
        }
        else if(this.delRegion.equalsIgnoreCase("Mindanao")){
            this.deliveryDays = 8;
            if(this.totalVolume > this.totalWeight){
                if(this.totalVolume * 0.25 > 3000)
                    this.serviceFee = this.totalVolume * 0.25;
                else
                    this.serviceFee = 3000;
            }
            else{
                if(this.totalWeight * 0.25 > 3000)
                    this.serviceFee = this.totalWeight * 0.25;
                else
                    this.serviceFee = 3000;
            }
        }
    }

    /**
     * This method determines the amount added to the price if insurance is applied
     * to the parcel.
     */
    public void computeInsuranceFee(){
        if(this.insurance)
            this.insuranceFee = 5 * this.quantity;
    }

    /** 
    * This method determines the type of parcel to be used based on the items to be placed.
    */
    public void determineType(){

    }

    /**
     * This method computes the total amount needed for the transaction to be complete.
     */
    public void computeTotalPrice(){

    }

    // getters and setters




}