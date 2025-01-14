package packages.shipper;

import javax.measure.Quantity;
import javax.measure.quantity.Volume;

public interface Package {  // similar to a trait in rust
    Quantity<Volume> volume();
    double cost();
    static double inflationAdjustment = 5;
    static double tax = 1.02;
} // every object package must have these methods attached to it with the static variales
