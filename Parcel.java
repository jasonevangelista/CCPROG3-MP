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
    private     double          totalFee = 0;
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
     * This method computes the base fee based on the type of parcel.
     */
    public double computeBaseFee(String parcelType){
        if(parcelType.equalsIgnoreCase("Flat1"))
            return 30;
        else if(parcelType.equalsIgnoreCase("Flat2"))
            return 50;
        else if(parcelType.equalsIgnoreCase("Box1") || parcelType.equalsIgnoreCase("Box2") ||
                parcelType.equalsIgnoreCase("Box3") || parcelType.equalsIgnoreCase("Box4")){
                /*
                loop through each item, if regular docu or product, php40/kilo
                
                if irregularly shaped, either php40/kilo of actual weight or
                php30/kilo of volumetric weight (whichever is higher)

                volumetric weight (in kilo) = (length x width x height) / 305
                */
                }
        else
            return 0;
    }

    /**
     * This method computes the total amount needed for the transaction to be complete.
     * 
     * @param baseFee - base fee from type of parcel and/or weight
     * @param serviceFee - delivery fee based on region
     * @param insuranceFee - additional fee for applying insurance (if applicable)
     * @return total fee amount
     */
    public double computeTotalFee(double baseFee, double serviceFee, double insuranceFee){
        return baseFee + serviceFee + insuranceFee;
    }

    /**
     * This method displays the breakdown of the total fee.
     * 
     * @param baseFee - base fee from type of parcel and/or weight
     * @param serviceFee - delivery fee based on region
     * @param insuranceFee - additional fee for applying insurance (if applicable)
     */
    public void displayFeeBreakdown(double baseFee, double serviceFee, double insuranceFee){
        System.out.println("==BREAKDOWN OF FEES==");
        System.out.println("BASE FEE -      Php" + baseFee);
        System.out.println("SERVICE FEE -   Php" + serviceFee);
        System.out.println("INSURANCE FEE - Php" + insuranceFee);
        System.out.println("----------------------------");
        System.out.println("TOTAL FEE -     Php" + computeTotalFee(baseFee, serviceFee, insuranceFee));
    }
    // getters and setters
    //




}