package net.shagie.compares;

import java.util.Comparator;

public class DoubleCompare implements Comparator<Double> {
    public int compare(Double o1, Double o2) {
        return o1.compareTo(o2);
    }
}
