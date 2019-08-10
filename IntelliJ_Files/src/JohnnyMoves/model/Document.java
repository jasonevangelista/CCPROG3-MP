package JohnnyMoves.model;

/**
 * The class Product represents a Document object which extends class Item, which contains the number of pages.
 *
 * @author Jason Evangelista
 * @author John Henry Cagaoan
 * @version 1.0
 */
public class Document extends Item{
    private   int     numPage;

    /**
     * This constructor takes in the item's length, width, and number of pages.
     *
     * @param length - the length of the document
     * @param width - the width of the document
     * @param numPage - the number of pages of the document
     */
    public Document(double length, double width, int numPage){
        super("Document", length, width);
        this.numPage = numPage;
        double height = (double)computeThickness(numPage);
        super.setHeight(height);
        super.setVolume(length * width * height);
        super.setWeight(computeWeight(numPage));
    }

     /**
     *   This method is used to compute for the thickness of
     *   document-type item. 25 pages = 1 inch, round up on excess.
     *
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
     * This method returns the number of pages of the document.
     *
     * @return the number of pages of the document.
     */
    public int getNumPage(){
        return this.numPage;
    }
}