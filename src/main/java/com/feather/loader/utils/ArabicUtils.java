package com.feather.loader.utils;

public class ArabicUtils {
    public static String fixArabic(String input) {
        if (input == null || input.isEmpty()) return input;
        
        if (containsArabic(input)) {
            return reverseArabic(input);
        }
        return input;
    }

    private static boolean containsArabic(String s) {
        return s.chars().anyMatch(c -> c >= 0x0600 && c <= 0x06FF);
    }

    private static String reverseArabic(String s) {
        return new StringBuilder(s).reverse().toString();
    }
}
