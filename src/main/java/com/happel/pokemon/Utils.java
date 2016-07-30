package com.happel.pokemon;

public class Utils {

    public static void println(String string) {
        System.out.println(string);
    }

    public static String toTitleCase(final String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

}
