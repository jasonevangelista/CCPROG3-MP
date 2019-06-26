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

    // constructor for document type
    public Item(String type, double length, double width, int numPage){
        this(type, length, width);
        this.numPage = numPage;

        // compute height/thickness
        this.height = computeThickness(this.numPage);
        // compute weight
        this.weight = computeWeight(this.numPage);
    }

    // constructor for product type
    public Item(String type, double length, double width, double height, double weight){
        this(type, length, width);
        this.height = height;
        this.weight = weight;
    }

    public double computeThickness(int numPage){
        return 0;
    }

    public double computeWeight(int numPage){
        return 0;
    }

    
}