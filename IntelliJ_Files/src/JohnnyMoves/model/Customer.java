package JohnnyMoves.model;

/**
 * The class Customer represents a Customer object with a name, delivery region, and parcel object.
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Customer {

    private String recipientName;
    private String delRegion;
    private Parcel parcel;

    /**
     * This constructor takes in the customer's name, delivery region, and parcel.
     *
     * @param recipientName - name
     * @param delRegion - delivery region
     * @param parcel - Parcel object
     */
    public Customer(String recipientName, String delRegion, Parcel parcel){
        this.recipientName = recipientName;
        this.delRegion = delRegion;
        this.parcel = parcel;
    }

    /**
     * This method gets the name.
     *
     * @return the recipientName
     */
    public String getRecipientName() {
        return recipientName;
    }

    /**
     * This method gets the Customer's delivery region.
     *
     * @return the delRegion
     */
    public String getDelRegion() {
        return delRegion;
    }

    /**
     * This method gets the parcel object.
     *
     * @return the parcel
     */
    public Parcel getParcel() {
        return parcel;
    }
}