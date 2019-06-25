import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver
 */
public class Driver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int seqNum;
        String name;
        int dRegion;
        String sRegion;
        int numItem;
        int dInsurance;
        boolean bInsurance;

        ArrayList<Item> listItem;

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
        dRegion = sc.nextInt();
        sc.nextLine();
        if(dRegion == 1)
            sRegion = "Metro Manila";
        else if(dRegion == 2)
            sRegion = "Provincial Luzon";
        else if(dRegion == 3)
            sRegion = "Visayas";
        else if(dRegion == 4)
            sRegion = "Mindanao";
        else
            System.out.println("error"); // error

        // number of items to place
        System.out.print("Enter number of items to place in parcel: ");
        numItem = sc.nextInt();
        sc.nextLine();

        // all the info of each item
        for(int i = 0; i < numItem; i++){
            //input info of each item
        }
        
        // apply insurance or not
        System.out.print("Do you want to insure the parcel for a small fee?");
        System.out.println("[1] YES");
        System.out.println("[2] NO");
        dInsurance = sc.nextInt();
        sc.nextLine();
        if(dInsurance == 1)
            bInsurance = true;
        else if(dInsurance == 2)
            bInsurance = false;
        else{
            bInsurance = false;
            System.out.println("error"); // error
        }


        /* program determines the type of parcels that can be used */
        Parcel parcel = new Parcel(name, sRegion, numItem, listItem, seqNum, bInsurance);

        /* user chooses the type of parcel he wants */

        /* program shows breakdown of the fee and total fee and generates tracking info */

        /* After transaction, program should allow user to choose to have another 
        parcel delivered, track the parcel/s, or exit program */

    }
}