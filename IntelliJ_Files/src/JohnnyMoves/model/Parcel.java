package JohnnyMoves.model;

import java.util.ArrayList;


/**
 * The class Parcel represents a parcel object with a recipient's name, destination region,
 * type, quantity of items, list of items, related fees, total weight and volume,
 * days of delivery, status of insurance, date of transaction, and tracking number.
 * 
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Parcel {

    private     String          type;
    private     int             quantity;
    private     ArrayList<Item> listItem;
    
    private     double          baseFee;
    private     double          serviceFee;

    private     double          insuranceFee;
    private     boolean         insurance;

    private     double          totalWeight = 0;
    private     double          totalVolume = 0;

    /**
     * This constructor takes in the parcel's list of items, type, and insurance status.
     *
     * @param listItem - list of Item objects
     * @param type - type
     * @param insurance - true/false whether parcel will apply insurance
     */
    public Parcel(ArrayList<Item> listItem, String type, boolean insurance){
        this.listItem = listItem;
        this.type = type;
        this.insurance = insurance;

        this.quantity = this.listItem.size();

        for(int i = 0; i < listItem.size(); i++){
            this.totalWeight = listItem.get(i).getWeight();
            this.totalVolume = listItem.get(i).getLength() * listItem.get(i).getWidth()
                    * listItem.get(i).getHeight() / 305;
        }

    }

     /**
      * This method determines the service fee of the parcel based on the delivery region and sets the delivery days for
      * the transaction.
      *
      * @param customer - Customer object
      * @param transaction - Transaction object
      */
     public void addRegionDetails(Customer customer, Transaction transaction){
         if(customer.getDelRegion().equalsIgnoreCase("MML")){
             transaction.setDeliveryDays(2);
             this.serviceFee = 50;
         }
         else if(customer.getDelRegion().equalsIgnoreCase("LUZ")){
             transaction.setDeliveryDays(3);
             this.serviceFee = 100;
         }
         else if(customer.getDelRegion().equalsIgnoreCase("VIS")){
             transaction.setDeliveryDays(5);
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
         else if(customer.getDelRegion().equalsIgnoreCase("MIN")){
             transaction.setDeliveryDays(6);
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
     * This method computes the base fee based on the type of parcel and shape of item.
     * 
     * @param parcelType The type of parcel
     * @param items The list of items to be placed in the parcel
     * @return the base fee of the parcel
     */
    public double computeBaseFee(String parcelType, ArrayList<Item> items){
        double actualRate;
        double volumetricRate;
        double baseFee = 0;
        if(parcelType.equalsIgnoreCase("FLT1"))
            return 30;
        else if(parcelType.equalsIgnoreCase("FLT2"))
            return 50;
        else if(parcelType.equalsIgnoreCase("BOX1") || parcelType.equalsIgnoreCase("BOX2") ||
                parcelType.equalsIgnoreCase("BOX3") || parcelType.equalsIgnoreCase("BOX4")){

                for(int i = 0; i < items.size(); i++){
                    actualRate = items.get(i).getWeight() * 40;
                    volumetricRate = items.get(i).getLength() * items.get(i).getWidth() * items.get(i).getHeight() / 305 * 30;

                    if(items.get(i) instanceof Product)
                        if(((Product)items.get(i)).getShape())
                            baseFee += actualRate;
                        else
                        if(actualRate > volumetricRate)
                            baseFee += actualRate;
                        else
                            baseFee += volumetricRate;

                    else{
                        if(actualRate > volumetricRate)
                            baseFee += actualRate;
                        else
                            baseFee += volumetricRate;
                    }
                }
                return baseFee;
        }
        else{
            System.out.println("invalid parcel type");
            return 0; // error
        }
    }

    /**
     * This method determines the amount added to the price if insurance is applied
     * to the parcel.
     * 
     * @param insurance Whether insurance is applied or not
     * @return The parcel's insurance fee
     */
    public double computeInsuranceFee(boolean insurance){
        if(insurance)
            return 5 * this.quantity;
        else 
            return 0;
    }

    /**
     * This method computes the total amount needed for the transaction to be complete.
     * 
     * @param baseFee base fee from type of parcel and/or weight
     * @param serviceFee delivery fee based on region
     * @param insuranceFee additional fee for applying insurance (if applicable)
     * @return total fee amount
     */
    public double computeTotalFee(double baseFee, double serviceFee, double insuranceFee){
        return baseFee + serviceFee + insuranceFee;
    }

    /* Getters */

    /**
     * This method gets the parcel type (FLT1/FLT2/BOX1/BOX2/BOX3/BOX4).
     * 
     * @return Parcel Type
     */
    public String getType() {
        return type;
    }

    /**
     * This method gets the list of items in the parcel.
     * 
     * @return List of items in parcel
     */
    public ArrayList<Item> getListItem() {
        return listItem;
    }

    /**
     * This method gets the status of insurance on the parcel.
     * 
     * @return the status whether insurance will be applied or not
     */
    public boolean getInsurance(){
        return this.insurance;
    }

    /**
     * This method gets the base fee of the parcel.
     * 
     * @return Base Fee
     */
    public double getBaseFee() {
        return baseFee;
    }
    
    /**
     * This method gets the service fee of the parcel.
     * 
     * @return Service Fee
     */
    public double getServiceFee() {
        return serviceFee;
    }

    /**
     * This method gets the insurance fee of the parcel.
     * 
     * @return Insurance Fee
     */
    public double getInsuranceFee() {
        return insuranceFee;
    }

    /* Setters */

    /**
     * This method sets the parcel type.
     * 
     * @param type The parcel type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method sets the base fee of the parcel.
     * 
     * @param baseFee the baseFee to set
     */
    public void setBaseFee(double baseFee) {
        this.baseFee = baseFee;
    }

    /**
     * This method sets the insurance fee of the parcel.
     * 
     * @param insuranceFee the insuranceFee to set
     */
    public void setInsuranceFee(double insuranceFee) {
        this.insuranceFee = insuranceFee;
    } 
}