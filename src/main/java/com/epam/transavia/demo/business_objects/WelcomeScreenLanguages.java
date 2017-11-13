package com.epam.transavia.demo.business_objects;

import com.epam.transavia.demo.core.exceptions.UnknownWelcomeLanguageException;

import java.util.HashMap;

public enum WelcomeScreenLanguages {
    OTHER_COUNTRY,
    BELGIË,
    NETHERLANDS,
    DEUTSCHLAND,
    NEDERLAND,
    ESPAÑA,
    FRANCE,
    ITALIA,
    BELGIQUE,
    PORTUGAL,
    UNITED_KINGDOM;

    public static boolean isEnumLangPresentInMap(WelcomeScreenLanguages language, HashMap hashMap) {
        if (hashMap.containsKey(language.toString())) {
            return true;
        } else {
            throw new UnknownWelcomeLanguageException("No such language is present in the HashMap: " + language.toString());
        }
    }
}
