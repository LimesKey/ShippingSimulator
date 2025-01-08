package packages.shipper;

import java.util.Scanner;

import javax.measure.MetricPrefix;
import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.measure.quantity.Volume;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

public class PaddedEnvelope implements Package {
    private Quantity<Length> length;
    private Quantity<Length> height;
    private static Quantity<Length> width = Quantities.getQuantity(0.01, Units.METRE);
    private Destination destination;

    public PaddedEnvelope(Quantity<Length> length, Quantity<Length> height, Quantity<Length> width,
            Destination destination) {
        this.length = length;
        this.height = height;
        PaddedEnvelope.width = width;
        this.destination = destination;
    }

    @Override
    public Quantity<Volume> volume() {
        double lengthCm = length.to(MetricPrefix.CENTI(Units.METRE)).getValue().doubleValue();
        double heightCm = height.to(MetricPrefix.CENTI(Units.METRE)).getValue().doubleValue();
        double widthCm = PaddedEnvelope.width.to(MetricPrefix.CENTI(Units.METRE)).getValue().doubleValue();
        return Quantities.getQuantity(lengthCm * heightCm * widthCm, Units.CUBIC_METRE);
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
        System.out.print("\nIs this envelope going internationally?"
                + " (Yes or No): ");

        String response = scanS.nextLine();

        Destination dest = switch (response.toLowerCase().strip()) {
            case "yes" -> Destination.INTERNATIONAL;
            case "no" -> Destination.DOMESTIC;
            default -> throw new IllegalArgumentException("Invalid response: " + response);
        };

        System.out.print("\nWhat is the length of the envelope in centimeters: ");

        Quantity<Length> length = Quantities.getQuantity(scanS.nextDouble(), MetricPrefix.CENTI(Units.METRE));

        //

        System.out.print("\nWhat is the height of the envelope in centimeters: ");

        Quantity<Length> height = Quantities.getQuantity(scanS.nextDouble(), MetricPrefix.CENTI(Units.METRE));

        return new PaddedEnvelope(length, height, PaddedEnvelope.width, dest);
    }
}