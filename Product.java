public class Product extends Item{
    private     boolean     regularShape;

    public Product(double length, double width, double height, double weight, boolean regularShape){
        super("Product", length, width);
        this.regularShape = regularShape;
        super.setHeight(height);
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