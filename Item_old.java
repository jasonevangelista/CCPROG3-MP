/**
 * The class Item represents an item object with a length, width, height, weight,
 * number of pages, type, and shape.
 * 
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public abstract class Item_old {
    private     int         length;
    private     int         width;
    private     int         height;
    private     int         weight;
    private     int         numPage;
    private     String      type;
    private     boolean     regularShape;

    /**
     * This constructor takes in the item's type, length, and width.
     *   @param type The type of parcel. Either flat or box.
     *   @param length The length of the page of the document.
     *   @param width The width of the page of the document.
     */
    public Item_old(String type, int length, int width){
        this.type = type;
        this.length = length;
        this.width = width;
    }

    /**
    *   This is the constructor for document-type items.
    *   @param type The type of parcel. Either flat or box.
    *   @param length The length of the page of the document.
    *   @param width The width of the page of the document.
    *   @param numPage The number of pages of the document.
    */
    public Item_old(String type, int length, int width, int numPage){
        this(type, length, width);
        this.numPage = numPage;
        this.height = computeThickness(this.numPage);
        this.weight = computeWeight(this.numPage);
        this.regularShape = true;
    }

    /**
    *   This is the constructor for product-type and irregularly-sized items.
    *   @param type The type of parcel. Either flat or box.
    *   @param length The length of the product.
    *   @param width The width of the product.
    *   @param height The height of the product.
    *   @param weight The weight of the product.
    *   @param regularShape Whether the product is regularly or irregularly shaped.
    */
    public Item_old(String type, int length, int width, int height, double weight, boolean regularShape){
        this(type, length, width);
        this.height = height;
        this.weight = (int) Math.ceil(weight);
        this.regularShape = regularShape;
    }

    /**
    *   This method is used to compute for the thickness of 
    *   document-type item. 25 pages = 1 inch, round up on excess.
    *   @param numPage Indicates the number of pages of the document.
    *   @return The thickness of the document in inches.
    */
    public int computeThickness(int numPage){
        int thickness = 0;
        while (numPage > 0){
            if (numPage >= 25){
                numPage -= 25;
                thickness++;
            }
            else if (numPage < 25 && numPage >= 0){
                numPage = 0;
                thickness++;
            }
        }
        return thickness;
    }

    /**
    *   This method is used to compute for the weight of 
    *   document-type item. 25 pages = 200g, round up on excess.
    *   @param numPage Indicates the number of pages of the document.
    *   @return The weight of the document in grams.
    */
    public int computeWeight(int numPage){
        double weight = 0;
        while (numPage > 0){
            if (numPage >= 25){
                numPage -= 25;
                weight += 0.200;
            }
            else if (numPage < 25 && numPage >= 0){
                numPage = 0;
                weight += 0.200;
            }
        }
        weight = Math.ceil(weight);

        return (int)weight;
    }

     /**
     * This method gets the length of the item (in inches).
     *  
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * This method gets the width of the item (in inches).
     *  
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * This method gets the height of the item (in inches).
     *  
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * This method gets the length of the item (in kilo).
     *  
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * This method gets the type of the item (Document/Product).
     *  
    *   @return item type (Document / product)
    */
    public String getItemType(){
        return type;
    }

    /**
     * This method gets the shape of the item (Regular/Irregular).
     *  
     * @return shape of item (regular - true / irregular - false)
     */
    public boolean getShape(){
        return regularShape;
    }
}