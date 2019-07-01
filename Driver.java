import java.util.*;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;

/**
 * Driver
 */
public class Driver {

    public static void main(String[] args) {
        Driver driver = new Driver();
        Scanner sc = new Scanner(System.in);

        int seqNum = 000;
        int numItem = 1; // Phase 1 condition (only 1 item per parcel)
        int actionInt;

        String name;
        String region;
        String parcelType;
        String trackingNum;
        String adminPassword = "password";

        boolean insurance;
        boolean run = true;

        ArrayList<Item> listItem;
        ArrayList<int[]> types;
        ArrayList<String> choices;
        ArrayList<Parcel> parcels = new ArrayList<Parcel>();

        Calendar cal = Calendar.getInstance();

        while(run){
            
            if(seqNum == 10){ // every 10 transactions == 1 day
                cal.add(Calendar.DATE, 1);
                seqNum = 0;
            }

            actionInt = driver.mainMenu(sc);

            int actionInt2 = actionInt;
			if(actionInt2 == 1){
                listItem = new ArrayList<Item>();
                types = new ArrayList<int[]>();
                choices = new ArrayList<String>();

                seqNum++;
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
                Parcel parcel = new Parcel(name, region, numItem, listItem, insurance);

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

                parcelType = driver.inputParcelType(sc, choices);

                if(choices.contains(parcelType))
                    parcel.setType(parcelType);
                else
                    System.out.println("error invalid parcel type input!!!");

                System.out.println("TYPE OF PARCEL: " + parcel.getType());

                /* program shows breakdown of the fee and total fee and generates tracking info */
                parcel.setInsuranceFee(parcel.computeInsuranceFee(parcel.getInsurance()));
                parcel.setBaseFee(parcel.computeBaseFee(parcel.getType(), parcel.getListItem().get(0)));
                parcel.displayFeeBreakdown(parcel.getBaseFee(), parcel.getServiceFee(), parcel.getInsuranceFee());

                // generate tracking number
                parcel.setTrackingNum(parcel.generateTrackingNum(seqNum, driver.formatDate(cal)));
                System.out.println("\nTRACKING NUMBER: " + parcel.getTrackingNumber());

                parcels.add(parcel);

            }
            else if(actionInt2 == 2){
                // track parcel
                driver.trackParcel(sc, parcels, cal, driver);
                
            }
            else if(actionInt2 == 3){
                if(driver.exitAuthorized(sc, adminPassword))
                    run = false;
            }
            else{
                System.out.println("ERROR");
            }
        }
        sc.close();
    }

    /**
     * This method asks for the recipient's name.
     * 
     * @param sc Scanner object
     * @return name
     */
    public String inputName(Scanner sc){
        System.out.println();
        System.out.print("Enter recipient's name: ");
        return sc.nextLine();
    }

    /**
     * This method asks for the delivery region of the parcel.
     * 
     * @param sc Scanner object
     * @return region in destination code format
     */
    public String inputRegion(Scanner sc){
        int inputInt;
        String retStr = new String(); //returned String
        boolean isInvalid = true;
        //Error catcher for user input
        do{
            System.out.println();
            System.out.println("Select region of delivery:");
            System.out.println("[1] Metro Manila");
            System.out.println("[2] Provincial Luzon");
            System.out.println("[3] Visayas");
            System.out.println("[4] Mindanao");
            System.out.print("> ");
            if (sc.hasNextInt()){
                inputInt = sc.nextInt();
                switch(inputInt){
                    case 1: 
                        retStr = "MML";
                        isInvalid = false;
                        break;
                    case 2:
                        retStr =  "LUZ";
                        isInvalid = false;
                        break;
                    case 3:
                        retStr =  "VIS";
                        isInvalid = false;
                        break;
                    case 4:
                        retStr =  "MIN";
                        isInvalid = false;
                        break;
                    default:
                        System.out.println("Invalid input, try again.");
                        isInvalid = true;
                        break;
                }
            }
            sc.nextLine(); 
        } while(isInvalid);
        
        return retStr;
    }

    public String inputParcelType(Scanner sc, ArrayList<String> choices){
        boolean isInvalid = true;
        String parcelChoice;
        do {
            System.out.println();
            System.out.print("Enter parcel of choice: ");
            parcelChoice = sc.nextLine();

            for(int i = 0; i < choices.size(); i++){
                if(parcelChoice.equals(choices.get(i)))
                    isInvalid = false;
            }
            if(isInvalid)
                System.out.println("Invalid input, try again.");


        } while (isInvalid);
        
        
        return parcelChoice;
    }

    public boolean inputInsurance(Scanner sc){
        int inputInt;
        boolean ins = true, isInvalid = true;
        System.out.println();
        System.out.println("Do you want to insure the parcel for a small fee?");
        System.out.println("[1] YES");
        System.out.println("[2] NO");

        do{
            System.out.print("> ");
            if (sc.hasNextInt()){
                inputInt = sc.nextInt();
                switch(inputInt){
                    case 1:
                        ins = true;
                        isInvalid = false;
                        break;
                    case 2:
                        ins = false;
                        isInvalid = false;
                        break;
                    default:
                        System.out.println("Invalid input.");
                        isInvalid = true;
                        break;
                }
            }
            sc.nextLine();
        } while(isInvalid);
        
        return ins;
    }

    public Item inputItemInfo(Scanner sc, int i){
        //Sabi ng compiler "might not be initialized" eh so ayan xD
        int inputInt = 0;
        int itemLength = 0;
        int itemWidth = 0;
 
        boolean isInvalid = true;

        System.out.println();
        System.out.println("PRODUCT #" + (i + 1) );
    
        // product or document
        do{
            System.out.println("\nType of Product:");
            System.out.println("[1] Document");
            System.out.println("[2] Product");
            System.out.print("\n> ");
            if(sc.hasNextInt()){
                inputInt = sc.nextInt();
                if (inputInt == 1 || inputInt == 2){
                    isInvalid = false;
                }
            }
            sc.nextLine();
        } while(isInvalid);
        isInvalid = true;
        
        // length
        do {
            System.out.print("Enter Length (inches): \n> ");
            if(sc.hasNextDouble())
                itemLength = (int)Math.ceil(sc.nextDouble());
            else if(sc.hasNextInt())
                itemLength = sc.nextInt();
                
            isInvalid = (itemLength > 0)? false : true;
            sc.nextLine();
        } while(isInvalid);
        
        System.out.println("\nLength: " + itemLength);      //D E B U G
        
        isInvalid = true;
        
        // width
        do{
            System.out.print("Enter Width (inches): \n> ");
            if(sc.hasNextDouble())
                itemWidth = (int)Math.ceil(sc.nextDouble());
            else if(sc.hasNextInt())
                itemWidth = sc.nextInt();

            isInvalid = (itemWidth > 0)? false : true;
            sc.nextLine();
        } while (isInvalid);

        System.out.println("\nWidth: " + itemWidth);        //D E B U G
        
        return (inputInt == 1)? itemIsDocument(sc, itemLength, itemWidth) : itemIsProduct(sc, itemLength, itemWidth);
        //                      ^^Executes this if user input is one        ^^else execute this
    }

    public Item itemIsDocument(Scanner sc, int itemLength, int itemWidth){
        int itemNumPage = 0;
        boolean isInvalid = true;

        do{
            System.out.print("\nEnter number of Pages: \n> ");
            if (sc.hasNextInt()){
                itemNumPage = sc.nextInt();
                if (itemNumPage > 0)
                    isInvalid = false;
            }
            sc.nextLine();
        } while(isInvalid);
        
        Item item = new Item("document", itemLength, itemWidth, itemNumPage);
        return item;
        // listItem.add(item);
    }

    public Item itemIsProduct(Scanner sc, int itemLength, int itemWidth){
        int inputInt = 0;
        int itemHeight = 0;
        double itemWeight = 0;
        boolean itemShape = true;
        boolean isInvalid = true;

        do{
            System.out.print("\nEnter Height (inches): \n> ");
            if(sc.hasNextDouble())
                itemHeight = (int)Math.ceil(sc.nextDouble());
            else if(sc.hasNextInt())
                itemHeight = sc.nextInt();

            isInvalid = (itemHeight > 0)? false : true;
            sc.nextLine();
        } while (isInvalid);

        isInvalid = true;

        do{
            System.out.print("\nEnter Weight (kilo): \n> ");
            if(sc.hasNextDouble())
                itemWeight = sc.nextDouble();
            else if(sc.hasNextInt())
                itemWeight = (double)sc.nextInt();

            isInvalid = (itemWidth > 0.0)? false : true;
            sc.nextLine();
        } while (isInvalid);

        isInvalid = true;

        do{
            System.out.println("\nShape of Product:");
            System.out.println("[1] Regular");
            System.out.println("[2] Irregular");
            System.out.print("\n> ");
            if(sc.hasNextInt()){
                inputInt = sc.nextInt();
                if (inputInt == 1 || inputInt == 2){
                    isInvalid = false;
                }
            }
            sc.nextLine();
        } while(isInvalid);
        itemShape = (inputInt == 1)? true: false;
        Item item = new Item("product", itemLength, itemWidth, itemHeight, itemWeight, itemShape);
        return item;
        // listItem.add(item);
    }

    public void trackParcel(Scanner sc, ArrayList<Parcel> parcels, Calendar cal, Driver d){
        boolean trackingNumValid = false;
        String trackingNum;
        System.out.println("Enter Tracking Number: ");
        trackingNum = sc.nextLine();

        System.out.println("\n=======================================");
        System.out.println("TRACKING INFO:\n");
        for(int i = 0; i < parcels.size(); i++){
            if(parcels.get(i).getTrackingNumber().equals(trackingNum)){
                // start date (date when transaction is made)
                System.out.print("Date of Transaction: \t");
                System.out.println(parcels.get(i).getDateOfTransaction());
                // generated tracking number
                System.out.print("Tracking Number: \t");
                System.out.println(parcels.get(i).getTrackingNumber());
                // current status
                System.out.print("Current Status: \t");
                // System.out.println("DIFFERENCE IN DAYS: " + d.getDifferenceDays(parcels.get(i).getDate(), cal) );
                parcels.get(i).displayDeliveryStatus(parcels.get(i).getDeliveryDays(), 
                d.getDifferenceDays(cal,parcels.get(i).getCalendarDate()) );
                
                // update date
                System.out.print("Current Date: \t\t");
                System.out.println(d.setDateFormat(cal));
                trackingNumValid = true;
            }
        }
        if(!trackingNumValid)
            System.out.println("Invalid Tracking Number!\n");
        System.out.println("=======================================\n");
    }

    public boolean exitAuthorized(Scanner sc, String pass){
        String p;
        System.out.print("Enter password: ");
        p = sc.nextLine();
        if(p.equals(pass)){
            System.out.println("System shutting off...");
            return true;
        }
            
        else{
            System.out.println("Invalid Password!");
            return false;
        }
    }

    public int mainMenu(Scanner sc){
        int inputInt;
        System.out.println("Johnny Moves - The Moving Company\n================================");
        System.out.println("What do you want to do?");
        System.out.println("[1] Make A Transaction");
        System.out.println("[2] Track Parcel");
        System.out.println("[3] Exit Program");
        System.out.print("> ");
        inputInt = sc.nextInt();
        sc.nextLine();

        return inputInt;
    }

    public String formatDate(Calendar cal){
        String sPattern = "MMdd";

        SimpleDateFormat DateFormat = new SimpleDateFormat(sPattern);
        String CurrentDate = DateFormat.format(cal.getTime());

        return CurrentDate;
    }
    
    public int getDifferenceDays(Calendar startDate, Calendar latestDate){
        long diff = latestDate.getTime().getTime() - startDate.getTime().getTime();
        return (int)TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
    }

    public String setDateFormat(Calendar cal){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(cal.getTime());
    }
    
}
