import java.util.ArrayList;

/**
 * Parcel
 */
public class Parcel {

    private     static int      MAX_FLAT_WEIGHT = 3; // in kg 

    private     static int      FLAT[][] = {{-1, 9, 14, 1},{0, 12, 18, 3}};
                                // format: flat #, length, width, max thickness
    
    private     static int      BOX[][] = {{1, 12, 10, 5}, {2, 14, 11, 7}, {3, 18, 12, 9}, {4, 20, 16, 12}};
                                // format: box #, length, width, height
    /*
        Symbol of index 0 of arrays FLAT[] and BOX[]:
        -1 -> FLT1
        0  -> FLT2
        1  -> BOX1
        2  -> BOX2
        3  -> BOX3
        4  -> BOX4
    */

    private     String          recipientName;
    private     String          delRegion;
    private     int             quantity;
    private     ArrayList<Item> listItem;
    private     String          type;
    private     int             seqNum;
    private     double          baseFee;
    private     double          totalFee = 0;
    private     double          serviceFee;
    private     double          insuranceFee;
    private     double          totalWeight = 0;
    private     double          totalVolume = 0;
    private     int             deliveryDays;
    private     boolean         insurance;

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

        addRegionDetails();
        computeInsuranceFee();
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
    * This method determines the valid types of parcel to be used based on the items to be placed.
    */
    public ArrayList<int[]> determineValidTypes(Item item){
        ArrayList<int[]> finalTypes = new ArrayList<int[]>();

        int length = item.getLength();
        int width = item.getWidth();
        int height = item.getHeight();

        int i, j;

        int itemVolume = computeVolume(length, width, height);
        int[][] rotations = rotations(length, width, height);

        boolean foundFlat = false;
        boolean foundBox = false;

        for(i = 0; i < FLAT.length; i++){
            if(itemVolume <= computeVolume(FLAT[i][1], FLAT[i][2], FLAT[i][3]) && item.getWeight() <= 3){
                for(j = 0; j < 6; j++){
                    if(rotations[j][0] <= FLAT[i][1] && rotations[j][1] <= FLAT[i][2] && rotations[j][2] <= FLAT[i][3]){
                        foundFlat = true;
                        break;
                    }
                }
            }
            if(foundFlat)
                finalTypes.add(FLAT[i]);
        }

        for(i = 0; i < BOX.length; i++){
            if(foundFlat)
                finalTypes.add(BOX[i]);
            
            else{
                if(itemVolume <= computeVolume(BOX[i][1], BOX[i][2], BOX[i][3])){
                    for(j = 0; j < 6; j++){
                        if(rotations[j][0] <= BOX[i][1] && rotations[j][1] <= BOX[i][2] && rotations[j][2] <= BOX[i][3]){
                            foundBox = true;
                            break;
                        }
                    }
                }
                if(foundBox)
                    finalTypes.add(BOX[i]);
            }
        }
        return finalTypes;
    }

    public int[][] rotations(int length, int width, int height){
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

    public int computeVolume(int length, int width, int height){
        return length * width * height;
    }
    /**
     * This method computes the base fee based on the type of parcel.
     */
    public double computeBaseFee(String parcelType){
        if(parcelType.equalsIgnoreCase("FLT1"))
            return 30;
        else if(parcelType.equalsIgnoreCase("FLT2"))
            return 50;
        else if(parcelType.equalsIgnoreCase("BOX1") || parcelType.equalsIgnoreCase("BOX2") ||
                parcelType.equalsIgnoreCase("BOX3") || parcelType.equalsIgnoreCase("BOX4")){
                /*
                loop through each item, if regular docu or product, php40/kilo
                
                if irregularly shaped, either php40/kilo of actual weight or
                php30/kilo of volumetric weight (whichever is higher)

                volumetric weight (in kilo) = (length x width x height) / 305
                */
                return 0;
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

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the baseFee
     */
    public double getBaseFee() {
        return baseFee;
    }
    
    /**
     * @return the serviceFee
     */
    public double getServiceFee() {
        return serviceFee;
    }

    /**
     * @return the insuranceFee
     */
    public double getInsuranceFee() {
        return insuranceFee;
    }


}