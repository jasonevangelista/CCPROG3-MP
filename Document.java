public class Document extends Item{
    protected   int     numPage;

    public Document(double length, double width, int numPage){
        super("Document", length, width);
        this.numPage = numPage;
    }
}