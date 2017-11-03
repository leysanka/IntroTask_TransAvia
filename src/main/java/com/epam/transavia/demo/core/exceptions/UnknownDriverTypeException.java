package com.epam.transavia.demo.core.exceptions;

public class UnknownDriverTypeException extends IllegalArgumentException {
    private String localMsg;

    public UnknownDriverTypeException(String s) {
        this.localMsg = s;
    }

    @Override
    public String getMessage() {
        return this.localMsg + "System error: " + super.getMessage();
    }

}
