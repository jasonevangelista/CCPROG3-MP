/**
 * Item
 */
public class Item {
    private     double      length;
    private     double      width;
    private     double      height;
    private     double      weight;
    private     int         numPage;
    private     String      type;


    public Item(String type, double length, double width){
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
    public Item(String type, double length, double width, int numPage){
        this(type, length, width);
        this.numPage = numPage;

        // compute height/thickness
        this.height = computeThickness(this.numPage);
        // compute weight
        this.weight = computeWeight(this.numPage);
    }

    /**
    *   This is the constructor for product-type and irregularly-sized items.
    *   @param type The type of parcel. Either flat or box.
    *   @param length The length of the product.
    *   @param width The width of the product.
    *   @param height The height of the product.
    *   @param weight The weight of the product.
    */
    public Item(String type, double length, double width, double height, double weight){
        this(type, length, width);
        this.height = height;
        this.weight = weight;
    }

    /**
    *   This function is used to compute for the thickness of 
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
    *   This function is used to compute for the weight of 
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
    *   Getter function for the item type; Document, product
    */
    public String getItemType(){
        return type;
    }
}