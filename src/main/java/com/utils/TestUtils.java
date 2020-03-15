package com.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class TestUtils {

    public static Double getDoubleFromString(String text){
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            Number number = format.parse(text.replaceAll("\\s+", ""));
            return number.doubleValue();
        }catch (Exception e){
            throw new AssertionError("Can't convert " + text + " to Double");
        }
    }
}
