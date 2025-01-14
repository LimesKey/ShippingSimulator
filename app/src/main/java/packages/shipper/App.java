package packages.shipper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/*
 * Program: Packages Shipping / Shipping Simulator
 * Programmer: Ryan Di Lorenzo // Miguel Afara
 * Date: Janurary 13th, 2025
 * Description: Simulate the process of ordering packages from popular carriers like Canada Post or UPS, but in your terminal! 
 */

import java.util.Scanner;
import java.util.Vector;

public class App {
    public static void main(String[] args) {
        Scanner scanS = new Scanner(System.in);

        System.out.println("Welcome to the Shipping Calculator. \nHere we "
                + "calculate the pricing of packages depending on the "
                + "attributes of the packages you give us. "
                + "\nAll attributes will "
                + "be in meters and in kilograms."
                + "\n\n");

        Vector<Package> packages = new Vector<>();

        // Package pkg = RectangularPackage.create(Quantities.getQuantity(0.10,
        // Units.METRE),
        // Quantities.getQuantity(0.10, Units.METRE), Quantities.getQuantity(0.10,
        // Units.METRE),
        // Quantities.getQuantity(10, Units.KILOGRAM), Destination.DOMESTIC, 1.0, 0.1);

        do {
            Package pkg = handlePackages(scanS); // Create a new Package object
            packages.add(pkg); // Add the Package object to the vector if it's not null

            System.out.println("Package Value is : " + pkg.cost());
            System.out.println("Would you like to add another package? (Yes or No)");
            String response = scanS.nextLine();
            if (response.equalsIgnoreCase("no")) {
                break; // Exit the loop if the user says "no"
            }
            scanS.reset(); // Reset the scanner for the next input
        } while (true);


        System.out.println("\n\nWould you like to have an invoice? "
                + "(Yes or No): ");
        String responseString = scanS.nextLine();
        DecimalFormat money = new DecimalFormat("#.00");

        Package[] packagesArray = new Package[packages.size()];
        packages.toArray(packagesArray);

        double total_cost = 0;
        for(Package onePackage : packagesArray ){
            total_cost += onePackage.cost();
        }

        if (responseString.equalsIgnoreCase("yes")) {
            PrintWriter writer; // todo: change to bufferedWriter in future
            try {
                writer = new PrintWriter(new File("Invoice.txt"));

                writer.println("Invoice for Package Shipment\n");
                for (int j = 0; j < packagesArray.length; j++) {
                    writer.println("Package #" + (j + 1) + ": $"
                            + money.format(packagesArray[j].cost()));
                }
                writer.println("Total: $" + money.format(total_cost));
                System.out.println("\nInvoice has been generated: Invoice.txt");
                System.out.println("Thank you for using our shipping services.");
                writer.close();
            } catch (FileNotFoundException ex) {
                System.out.println("File Not Found Error.");
            }
        } else if (responseString.equalsIgnoreCase("no")) {
            System.out.println("Thank you for using our shipping services.");
        }

        scanS.close();
    }

    /**
     * Name: handlePackages
     * 
     * @param scanS - the scanner to use
     * @return - Packages attributes
     *         Description: Asks the user what the attributes of the packages are.
     */
    public static Package handlePackages(Scanner scanS) {
        System.out.println("What kind of package is being shipped?"
                + "\nPlease select a number: "
                + "\n1) Box"
                + "\n2) Envelope ");
        int choice = Integer.parseInt(scanS.nextLine());

        switch (choice) {
            case 1:
                Package box = RectangularPackage.create(scanS);
                return box;
            case 2:
                Package foo = Envelope.create(scanS);
                return foo;
            default:
                return null;
        }
    }
}
