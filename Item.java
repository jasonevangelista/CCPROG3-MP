/**
 * The class Item represents an item object with a length, width, height, weight,
 * number of pages, type, and shape.
 * 
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public abstract class Item {
    protected     double         length;
    protected     double         width;
    protected     double         weight;
    protected     double         height;
    private       String         type;

    /**
     * This constructor takes in the item's type, length, and width.
     *   @param type The type of parcel. Either flat or box.
     *   @param length The length of the page of the document.
     *   @param width The width of the page of the document.
     */
    public Item(String type, double length, double width){
        this.type = type;
        this.length = length;
        this.width = width;
    }

     /**
     * This method gets the length of the item (in inches).
     *  
     * @return the length
     */
    public double getLength() {
        return length;
    }

    /**
     * This method gets the width of the item (in inches).
     *  
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * This method gets the length of the item (in kilo).
     *  
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * This method gets the height of the item (in inches).
     *  
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * This method gets the type of the item (Document/Product).
     *  
    *   @return item type (Document / product)
    */
    public String getItemType(){
        return type;
    }
}