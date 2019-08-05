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