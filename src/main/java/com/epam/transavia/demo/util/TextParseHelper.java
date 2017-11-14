package com.epam.transavia.demo.util;


import com.epam.transavia.demo.core.exceptions.TextParseHelperException;

public class TextParseHelper {

    private static final String nonDigitsRegex = "\\D+";

    public static Double retrieveDigitsFromText(String text) throws TextParseHelperException {
        String digits = "";
        if (!text.isEmpty()) {
            /*Remove decimal part .00 in text if present*/
            if (text.contains(".")) {
                text = text.substring(0, text.lastIndexOf("."));
            }
            digits = text.replaceAll(nonDigitsRegex, "");
            if (!digits.isEmpty()) {
                return Double.valueOf(digits);
            } else {
                throw new TextParseHelperException("Text is empty after non-digits characters replacement.");
            }
        }
        throw new TextParseHelperException("Tried to parse empty text: " + text);
    }
}
