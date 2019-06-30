/**
 * The class Item represents an item object with a length, width, height, weight,
 * number of pages, type, and shape.
 * 
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Item {
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
    public Item(String type, int length, int width){
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
    public Item(String type, int length, int width, int numPage){
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
    */
    public Item(String type, int length, int width, int height, double weight, boolean regularShape){
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
        int weight = 0;
        while (numPage > 0){
            if (numPage >= 25){
                numPage -= 25;
                weight += 200;
            }
            else if (numPage < 25 && numPage >= 0){
                numPage = 0;
                weight += 200;
            }
        }
        return weight;
    }

     /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
    *   @return item type (Document / product)
    */
    public String getItemType(){
        return type;
    }

    /**
     * @return shape of item (regular - true / irregular - false)
     */
    public boolean getShape(){
        return regularShape;
    }
}