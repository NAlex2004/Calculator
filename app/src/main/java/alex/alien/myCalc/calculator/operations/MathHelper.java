package alex.alien.myCalc.calculator.operations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathHelper {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
