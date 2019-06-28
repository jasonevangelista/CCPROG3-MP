import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver
 */
public class Driver {

    public static void main(String[] args) {
        Driver driver = new Driver();
        Scanner sc = new Scanner(System.in);

        int seqNum = 000;
        int numItem = 1; // Phase 1 condition (only 1 item per parcel)
        String name;
        String region;
        String parcelType;
        boolean insurance;

        ArrayList<Item> listItem = new ArrayList<Item>();
        ArrayList<int[]> types = new ArrayList<int[]>();
        ArrayList<String> choices = new ArrayList<String>();

        /* INPUT INFO */
        name = driver.inputName(sc);
        region = driver.inputRegion(sc);

        // all the info of each item
        for(int i = 0; i < numItem; i++){
            Item item = driver.inputItemInfo(sc, i);
            listItem.add(item);
        }

        insurance = driver.inputInsurance(sc);
        
        /* program determines the type of parcels that can be used */
        Parcel parcel = new Parcel(name, region, numItem, listItem, seqNum, insurance);

        types = parcel.determineValidTypes(listItem.get(0));

        for(int i = 0; i < types.size(); i++)
            System.out.println(types.get(i)[0]);
        
        /* user chooses the type of parcel he wants */
        System.out.println("Valid Parcels to use:");
        for (int i = 0; i < types.size(); i++) {
            if(types.get(i)[0] == -1)
                choices.add("FLT1");
            else if(types.get(i)[0] == 0)
                choices.add("FLT2");
            else if(types.get(i)[0] == 1)
                choices.add("BOX1");
            else if(types.get(i)[0] == 2)
                choices.add("BOX2");
            else if(types.get(i)[0] == 3)
                choices.add("BOX3");
            else if(types.get(i)[0] == 4)
                choices.add("BOX4");
        }

        for (int i = 0; i < choices.size(); i++) {
            System.out.print("--> "+ choices.get(i) + " - ");
            parcel.displayDimensions(choices.get(i));
        }

        parcelType = driver.inputParcelType(sc);

        if(choices.contains(parcelType))
            parcel.setType(parcelType);
        else
            System.out.println("error invalid parcel type input!!!");

        System.out.println("TYPE OF PARCEL: " + parcel.getType());

        /* program shows breakdown of the fee and total fee and generates tracking info */
        parcel.setInsuranceFee(parcel.computeInsuranceFee(parcel.getInsurance()));
        parcel.setBaseFee(parcel.computeBaseFee(parcel.getType(), parcel.getListItem().get(0)));
        parcel.displayFeeBreakdown(parcel.getBaseFee(), parcel.getServiceFee(), parcel.getInsuranceFee());

        /* After transaction, program should allow user to choose to have another 
        parcel delivered, track the parcel/s, or exit program */

        /*
        Options:
        - Track
        - Start New Transaction
        - Exit (staff only)
        */
        sc.close();
    }

    public String inputName(Scanner sc){
        System.out.print("Enter recipient's name: ");
        return sc.nextLine();
    }

    public String inputRegion(Scanner sc){
        int inputInt;

        System.out.println("Select region of delivery:");
        System.out.println("[1] Metro Manila");
        System.out.println("[2] Provincial Luzon");
        System.out.println("[3] Visayas");
        System.out.println("[4] Mindanao");
        System.out.print("Input: ");

        inputInt = sc.nextInt();
        sc.nextLine();
        
        if(inputInt == 1)
            return "MML";
        else if(inputInt == 2)
            return "LUZ";
        else if(inputInt == 3)
            return "VIS";
        else if(inputInt == 4)
            return "MIN";
        else{
            System.out.println("error"); // error
            return "";
        }
        
    }

    public String inputParcelType(Scanner sc){
        System.out.print("Enter parcel of choice: ");
        return sc.nextLine();
    }

    public boolean inputInsurance(Scanner sc){
        int inputInt;
        boolean ins;
        System.out.println("Do you want to insure the parcel for a small fee?");
        System.out.println("[1] YES");
        System.out.println("[2] NO");
        inputInt = sc.nextInt();
        sc.nextLine();
        if(inputInt == 1)
            ins = true;
        else if(inputInt == 2)
            ins = false;
        else{
            ins = false;
            System.out.println("error"); // error
        }
        return ins;
    }

    public Item inputItemInfo(Scanner sc, int i){
    
        int inputInt;
        int itemLength;
        int itemWidth;
        int itemHeight;
        int itemNumPage;

        double itemWeight;
    
        boolean itemShape;
        
        String itemType;
       
        System.out.println("PRODUCT #" + (i + 1) );
    
        // product or document
        System.out.println("Type of Product:");
        System.out.println("[1] Document");
        System.out.println("[2] Product");
        System.out.print("Input: ");
        inputInt = sc.nextInt();
        sc.nextLine();
    
        // length
        System.out.print("Enter Length (inches): ");
        itemLength = sc.nextInt();
        sc.nextLine();
    
        // width
        System.out.print("Enter Width (inches): ");
        itemWidth = sc.nextInt();
        sc.nextLine();
        
        //if docu, ask number of pages
        if(inputInt == 1){
            itemType = "document";
            System.out.print("Enter number of Pages: ");
            itemNumPage = sc.nextInt();
            sc.nextLine();
    
            Item item = new Item(itemType, itemLength, itemWidth, itemNumPage);
            return item;
            // listItem.add(item);
        }
    
        // if product, ask height and weight
        else if(inputInt == 2){
            itemType = "product";
            System.out.print("Enter Height (inches): ");
            itemHeight = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Weight (kilo): ");
            itemWeight = sc.nextDouble();
            sc.nextLine();
            System.out.println("Shape of Product:");
            System.out.println("[1] Regular");
            System.out.println("[2] Irregular");
            System.out.print("Enter input: ");
            if(sc.nextInt() == 1)
                itemShape = true;
            else
                itemShape = false;
    
            Item item = new Item(itemType, itemLength, itemWidth, itemHeight, itemWeight, itemShape);
            return item;
            // listItem.add(item);
        }
        else{
            System.out.println("error");
            return null;
        }
    
    }
}