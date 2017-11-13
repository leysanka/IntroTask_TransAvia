package com.epam.transavia.demo.business_objects;

import com.epam.transavia.demo.core.exceptions.UnknownPassengerTypeException;

public enum PassengersTypes {
    ADULTS,
    CHILDREN,
    BABIES;

    public static PassengersTypes getPassengerOfType(String type) {
        for (PassengersTypes enumType :
                PassengersTypes.values()) {
            if (type.equalsIgnoreCase(enumType.toString())) {
                return enumType;
            }
        }
        throw new UnknownPassengerTypeException(type + " is undefined passenger type within the PassengersTypes enum.");
    }
}
