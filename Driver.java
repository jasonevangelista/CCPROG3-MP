import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver
 */
public class Driver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int seqNum = 000;
        String name;
        String region;
        int numItem;
        int inputInt;
        double inputDouble;
        boolean insurance;

        ArrayList<Item> listItem = new ArrayList<Item>();

        String itemType;
        double itemLength;
        double itemWidth;
        double itemHeight;
        double itemWeight;
        int itemNumPage;

        /* input info */

        // recipient name
        System.out.print("Enter recipient's name: ");
        name = sc.nextLine();

        // delivery region
        System.out.println("Select region of delivery:");
        System.out.println("[1] Metro Manila");
        System.out.println("[2] Provincial Luzon");
        System.out.println("[3] Visayas");
        System.out.println("[4] Mindanao");
        System.out.print("Input: ");
        inputInt = sc.nextInt();
        sc.nextLine();
        if(inputInt == 1)
            region = "Metro Manila";
        else if(inputInt == 2)
            region = "Provincial Luzon";
        else if(inputInt == 3)
            region = "Visayas";
        else if(inputInt == 4)
            region = "Mindanao";
        else
            System.out.println("error"); // error

        // number of items to place
        System.out.print("Enter number of items to place in parcel: ");
        numItem = sc.nextInt();
        sc.nextLine();

        // all the info of each item
        for(int i = 0; i < numItem; i++){
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
            itemLength = sc.nextDouble();
            sc.nextLine();

            // width
            System.out.print("Enter Width (inches): ");
            itemWidth = sc.nextDouble();
            sc.nextLine();
            
            //if docu, ask number of pages
            if(inputInt == 1){
                itemType = "document";
                System.out.print("Enter number of Pages: ");
                itemNumPage = sc.nextInt();
                sc.nextLine();

                Item item = new Item(itemType, itemLength, itemWidth, itemNumPage);
                listItem.add(item);
            }

            // if product, ask height and weight
            else if(inputInt == 2){
                itemType = "product";
                System.out.print("Enter Height (inches): ");
                itemHeight = sc.nextDouble();
                sc.nextLine();
                System.out.print("Enter Weight (kilo): ");
                itemWeight = sc.nextDouble();
                sc.nextLine();

                Item item = new Item(itemType, itemLength, itemWidth, itemHeight, itemWeight);
                listItem.add(item);
            }

            else{
                System.out.println("error");
            }

        }
        
        // apply insurance or not
        System.out.println("Do you want to insure the parcel for a small fee?");
        System.out.println("[1] YES");
        System.out.println("[2] NO");
        inputInt = sc.nextInt();
        sc.nextLine();
        if(inputInt == 1)
            insurance = true;
        else if(inputInt == 2)
            insurance = false;
        else{
            insurance = false;
            System.out.println("error"); // error
        }


        /* program determines the type of parcels that can be used */
        // Parcel parcel = new Parcel(name, sRegion, numItem, listItem, seqNum, bInsurance);

        /* user chooses the type of parcel he wants */

        /* program shows breakdown of the fee and total fee and generates tracking info */

        /* After transaction, program should allow user to choose to have another 
        parcel delivered, track the parcel/s, or exit program */

    }
}