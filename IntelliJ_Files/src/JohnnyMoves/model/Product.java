package JohnnyMoves.model;

/**
 * The class Product represents a Product object which extends class Item, which contains a shape status.
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Product extends Item{
    private     boolean     regularShape;

    /**
     * This constructor takes in the Product's length, width, height, weight, and shape status.
     *
     * @param length - the length of the product
     * @param width - the width of the product
     * @param height - the height of the product
     * @param weight - the weight of the product
     * @param regularShape - the status of the shape (true - regular/ false - irregular)
     */
    public Product(double length, double width, double height, double weight, boolean regularShape){
        super("Product", length, width);
        this.regularShape = regularShape;
        super.setHeight(height);
        super.setVolume(length * width * height);
        super.setWeight(weight);
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