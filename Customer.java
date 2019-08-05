/**
 * Customer
 */
public class Customer {

    private String recipientName;
    private String delRegion;
    private Parcel parcel;

    public Customer(String recipientName, String delRegion, Parcel parcel){
        this.recipientName = recipientName;
        this.delRegion = delRegion;
        this.parcel = parcel;
    }

     // /**
    //  * This method determines the number of delivery days of the parcel and
    //  * the service fee depending on the region.
    //  */
    // public void addRegionDetails(){
    //     if(this.delRegion.equalsIgnoreCase("MML")){
    //         this.deliveryDays = 2;
    //         this.serviceFee = 50;
    //     }
    //     else if(this.delRegion.equalsIgnoreCase("LUZ")){
    //         this.deliveryDays = 3;
    //         this.serviceFee = 100;
    //     }
    //     else if(this.delRegion.equalsIgnoreCase("VIS")){
    //         this.deliveryDays = 5;
    //         if(this.totalVolume > this.totalWeight){
    //             if(this.totalVolume * 0.10 > 1000)
    //                 this.serviceFee = this.totalVolume * 0.10;
    //             else
    //                 this.serviceFee = 1000;
    //         }
    //         else{
    //             if(this.totalWeight * 0.10 > 1000)
    //                 this.serviceFee = this.totalWeight * 0.10;
    //             else
    //                 this.serviceFee = 1000;
    //         }
    //     }
    //     else if(this.delRegion.equalsIgnoreCase("MIN")){
    //         this.deliveryDays = 8;
    //         if(this.totalVolume > this.totalWeight){
    //             if(this.totalVolume * 0.25 > 3000)
    //                 this.serviceFee = this.totalVolume * 0.25;
    //             else
    //                 this.serviceFee = 3000;
    //         }
    //         else{
    //             if(this.totalWeight * 0.25 > 3000)
    //                 this.serviceFee = this.totalWeight * 0.25;
    //             else
    //                 this.serviceFee = 3000;
    //         }
    //     }
    // }

    /**
     * @return the recipientName
     */
    public String getRecipientName() {
        return recipientName;
    }

    /**
     * @return the delRegion
     */
    public String getDelRegion() {
        return delRegion;
    }

    /**
     * @return the parcel
     */
    public Parcel getParcel() {
        return parcel;
    }
}