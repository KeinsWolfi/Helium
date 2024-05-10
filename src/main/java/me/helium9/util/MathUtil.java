package me.helium9.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class MathUtil {

    public final SecureRandom RANDOM = new SecureRandom();

    public static double getRandom(double min, double max) {
        if (min == max) {
            return min;
        } else if (min > max) {
            final double d = min;
            min = max;
            max = d;
        }
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

}
