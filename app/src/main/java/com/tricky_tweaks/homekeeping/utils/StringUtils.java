package com.tricky_tweaks.homekeeping.utils;

public class StringUtils {
    public static String toTitleCase(String input) {
        if (input != null) {
            StringBuilder titleCase = new StringBuilder(input.length());
            boolean nextTitleCase = true;

            for (char c : input.toCharArray()) {
                if (Character.isSpaceChar(c)) {
                    nextTitleCase = true;
                } else if (nextTitleCase) {
                    c = Character.toTitleCase(c);
                    nextTitleCase = false;
                }

                titleCase.append(c);
            }
            return titleCase.toString();
        } else {
         return input;
        }
    }
}
