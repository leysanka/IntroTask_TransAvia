package com.epam.transavia.demo.util;


import com.epam.transavia.demo.core.exceptions.TextParseHelperException;

public class TextParseHelper {

    private static final String nonDigitsRegex = "\\D+";

    public static Double takePriceFromText(String text) throws TextParseHelperException {
        String formattedText = "";
        formattedText = removeNonDigitsCharacters(trimDecimalPartIfPresent(text));

        return covertTextToDouble(formattedText);
    }

    private static String trimDecimalPartIfPresent(String text) {
        if (text.length() > 1 && text.contains(".")) {
            return text.substring(0, text.lastIndexOf("."));
        }
        return text;
    }

    private static String removeNonDigitsCharacters(String text) throws TextParseHelperException {
        if (!text.isEmpty()) {
            return text.replaceAll(nonDigitsRegex, "");
        }
        throw new TextParseHelperException("Tried to parse empty text: " + text);
    }

    private static Double covertTextToDouble(String text) throws TextParseHelperException {
        if (!text.isEmpty()) {
            return Double.valueOf(text);
        }
        throw new TextParseHelperException("Tried to covert empty text: " + text);
    }


}
