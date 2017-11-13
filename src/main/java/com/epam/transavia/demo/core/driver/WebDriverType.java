package com.epam.transavia.demo.core.driver;

import com.epam.transavia.demo.core.exceptions.UnknownDriverTypeException;

public enum WebDriverType {
    CHROME,
    FIREFOX;

    public static WebDriverType getWebDriverType(String name) {
        for (WebDriverType type :
                WebDriverType.values()) {
            if (name.equalsIgnoreCase(type.toString())) {
                return type;
            }
        }
        throw new UnknownDriverTypeException("\"" + name + "\"" + " is not supported browser type.\n");
    }
}
