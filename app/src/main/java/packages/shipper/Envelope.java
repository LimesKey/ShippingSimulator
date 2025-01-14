package packages.shipper;

import java.util.Optional;
import java.util.Scanner;

import javax.measure.MetricPrefix;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Volume;

import tech.units.indriya.ComparableQuantity;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

public class Envelope implements Package {
    private Quantity<Length> length;
    private Quantity<Length> height;
    private static Optional<Quantity<Length>> width;
    private Destination destination;

    public Envelope(Quantity<Length> length, Quantity<Length> height, Optional<Quantity<Length>> width,
            Destination destination) {
        this.length = length;
        this.height = height;
        Envelope.width = width;
        this.destination = destination;
    }

    @Override
    public Quantity<Volume> volume() {
        double lengthCm = length.to(MetricPrefix.CENTI(Units.METRE)).getValue().doubleValue();
        double heightCm = height.to(MetricPrefix.CENTI(Units.METRE)).getValue().doubleValue();

        if (width.isPresent()) {
            double widthCm = width.get().to(MetricPrefix.CENTI(Units.METRE)).getValue().doubleValue();
            return Quantities.getQuantity(lengthCm * heightCm * widthCm, Units.CUBIC_METRE);
        }
        return null;
    }

    public ComparableQuantity<javax.measure.quantity.Area> Area () {
        double lengthCm = length.to(MetricPrefix.CENTI(Units.METRE)).getValue().doubleValue();
        double heightCm = height.to(MetricPrefix.CENTI(Units.METRE)).getValue().doubleValue();

        if (width.isPresent()) {
            double widthCm = width.get().to(MetricPrefix.CENTI(Units.METRE)).getValue().doubleValue();
            return Quantities.getQuantity(lengthCm * heightCm * widthCm, Units.SQUARE_METRE);
        }
        return null;
    }

    @Override
    public double cost() {
        double volumeCm3 = volume().to(Units.CUBIC_METRE).getValue().doubleValue();
        double baseCost = volumeCm3 * 0.1;

        if (destination == Destination.DOMESTIC) {
            return baseCost * tax;
        } else {
            return baseCost + inflationAdjustment * tax;
        }
    }

    public static Package create(Scanner scanS) {
        System.out.println("Is this envelope a padded envelope with buble wrap or a standard one for papers?");
        System.out.println("\t1. Padded Envelope");
        System.out.println("\t2. Standard Envelope");

        String response = scanS.nextLine();
        Optional<Quantity<Length>> width1 = Optional.empty();

        if (response.equals("1")) {
            System.out.print("\nWhat is the width of the envelope in centimeters: ");
            width1 = Optional.of(Quantities.getQuantity(Double.parseDouble(scanS.nextLine()),
                    MetricPrefix.CENTI(Units.METRE)));
        } else {
            System.out.println("Standarresponsed Envelope");
            width1 = Optional.empty();
        }

        System.out.print("\nIs this envelope going internationally?"
                + " (Yes or No): ");

        Destination dest = switch (scanS.nextLine().toLowerCase().strip()) {
            case "yes" -> Destination.INTERNATIONAL;
            case "no" -> Destination.DOMESTIC;
            default -> throw new IllegalArgumentException("Invalid response: " + response);
        };

        System.out.print("\nWhat is the length of the envelope in centimeters: ");

        Quantity<Length> length = Quantities.getQuantity(Double.parseDouble(scanS.nextLine()),
                MetricPrefix.CENTI(Units.METRE));

        System.out.print("\nWhat is the height of the envelope in centimeters: ");

        Quantity<Length> height = Quantities.getQuantity(Double.parseDouble(scanS.nextLine()),
                MetricPrefix.CENTI(Units.METRE));

        return new Envelope(length, height, width1, dest);
    }
}