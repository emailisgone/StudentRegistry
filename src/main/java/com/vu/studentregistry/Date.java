package com.vu.studentregistry;

import java.util.Hashtable;

public class Date {
    public final static Hashtable<Month, Integer> daysInMonths = new Hashtable<>() {{
        put(Month.January, 31);
        put(Month.February, 28);
        put(Month.March, 31);
        put(Month.April, 30);
        put(Month.May, 31);
        put(Month.June, 30);
        put(Month.July, 31);
        put(Month.August, 31);
        put(Month.September, 30);
        put(Month.October, 31);
        put(Month.November, 30);
        put(Month.December, 31);
    }};
}
