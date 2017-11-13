package com.epam.transavia.demo.core.exceptions;

public class UnknownPassengerTypeException extends RuntimeException {
    private String localError;

    public UnknownPassengerTypeException(String s) {
        this.localError = s;
    }

    @Override
    public String getMessage() {
        return this.localError + "\n" + "System error: " + super.getMessage();
    }
}
