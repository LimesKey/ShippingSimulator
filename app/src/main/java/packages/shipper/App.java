package packages.shipper;

import java.util.Scanner;
import java.util.Vector;

public class App {
    public static void main(String[] args) {
        Scanner words = new Scanner(System.in);

        System.out.println("Welcome to the Shipping Calculator. \nHere we "
                + "calculate the pricing of packages depending on the "
                + "attributes of the packages you give us. "
                + "\nAll attributes will "
                + "be in meters and in kilograms."
                + "\n\n");

        Vector<Package> packages = new Vector<>();

        // Package pkg = RectangularPackage.create(Quantities.getQuantity(0.10, Units.METRE),
        //         Quantities.getQuantity(0.10, Units.METRE), Quantities.getQuantity(0.10, Units.METRE),
        //         Quantities.getQuantity(10, Units.KILOGRAM), Destination.DOMESTIC, 1.0, 0.1);

        do {
            Package pkg = handlePackages();
            packages.add(pkg);
            System.out.println("Would you like to add another package? (Yes or No)");
            String response = words.nextLine();
            if (response.equalsIgnoreCase("no")) {
                break;
            }

        } while (true);

        System.out.println("The total cost of shipping is: " + packages.get(0).cost());
    }

    /**
     * Name: setData
     * 
     * @param count - the package number
     * @return - Packages attributes
     *         Description: Asks the user what the attributes of the packages are.
     */
    public static Package handlePackages() {
        Scanner scanS = new Scanner(System.in);

        System.out.println("What kind of package is being shipped?"
                + "\nPlease select a number: "
                + "\n1) Box"
                + "\n2) Bubble Wrapped"
                + "\n3) Envelope ");
        int choice = Integer.parseInt(scanS.nextLine());

        switch (choice) {
            case 3:
                Package sdsf = PaddedEnvelope.create();       
                return sdsf;
            default: 
                return null;
        }
    }
}
