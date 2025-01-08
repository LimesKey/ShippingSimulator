package packages.shipper;

import javax.measure.Quantity;
import javax.measure.quantity.Volume;

public interface Package {
    Quantity<Volume> volume();
    double cost();
    static double inflationAdjustment = 5;
    static double tax = 1.02;
}
