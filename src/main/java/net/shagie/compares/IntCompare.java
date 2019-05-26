package net.shagie.compares;

import java.util.Comparator;

public class IntCompare implements Comparator<Integer> {
    public int compare(Integer o1, Integer o2) {
        return o1.compareTo(o2);
    }
}
