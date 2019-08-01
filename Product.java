public class Product extends Item{
    private     boolean     regularShape;

    public Product(double length, double width, double weight, boolean regularShape){
        super("Product", length, width);
        this.regularShape = regularShape;
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