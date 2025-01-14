package packages.shipper;

import java.util.Scanner;

import javax.measure.MetricPrefix;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

public class RectangularPackage implements Package {
    private Quantity<Length> length;
    private Quantity<Length> width;
    private Quantity<Length> height;
    private Quantity<Mass> weight;
    private Destination destination;

    public RectangularPackage(Quantity<Length> length, Quantity<Length> width, Quantity<Length> height,
            Quantity<Mass> weight, Destination destination) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.destination = destination;
    }

    @Override
    public Quantity<Volume> volume() {
        double lengthCm = length.to(MetricPrefix.CENTI(Units.METRE)).getValue().doubleValue();
        double heightCm = height.to(MetricPrefix.CENTI(Units.METRE)).getValue().doubleValue();
        double widthCm = width.to(MetricPrefix.CENTI(Units.METRE)).getValue().doubleValue();
        return Quantities.getQuantity(lengthCm * heightCm * widthCm, Units.CUBIC_METRE);
    }

    @Override
    public double cost() {
        double volumeCm3 = volume().to(Units.CUBIC_METRE).getValue().doubleValue();
        double baseCost = volumeCm3 * 0.15 * weight.to(Units.GRAM).getValue().doubleValue();

        if (destination == Destination.DOMESTIC) {
            return baseCost * tax;
        } else {
            return baseCost + inflationAdjustment * tax;
        }
    }

    public static Package create(Scanner scanS) {
        System.out.print("\nIs this box going internationally?"
                + " (Yes or No): ");

        String response = scanS.nextLine();

        Destination dest = switch (response.toLowerCase().strip()) {
            case "yes" -> Destination.INTERNATIONAL;
            case "no" -> Destination.DOMESTIC;
            default -> throw new IllegalArgumentException("Invalid response: " + response);
        };

        System.out.print("\nWhat is the length of the box in centimeters: ");

        Quantity<Length> length = Quantities.getQuantity(Double.parseDouble(scanS.nextLine()),
                MetricPrefix.CENTI(Units.METRE));

        //

        System.out.print("\nWhat is the height of the box in centimeters: ");

        Quantity<Length> height = Quantities.getQuantity(Double.parseDouble(scanS.nextLine()),
                MetricPrefix.CENTI(Units.METRE));

        System.out.print("\nWhat is the width of the box in centimeters: ");


        Quantity<Length> width = Quantities.getQuantity(Double.parseDouble(scanS.nextLine()),
                MetricPrefix.CENTI(Units.METRE));

        System.out.print("\nWhat is the weight of the box in grams: ");
        Quantity<Mass> weight = Quantities.getQuantity(Double.parseDouble(scanS.nextLine()), Units.GRAM);    

        return new RectangularPackage(length, width, height, weight, dest);
    }
}