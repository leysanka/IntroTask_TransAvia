package com.epam.transavia.demo.core.exceptions;

public class TextParseHelperException extends Throwable {
    private String localErr;

    public TextParseHelperException(String s) {
        this.localErr = s;
    }

    @Override
    public String getMessage() {
        return this.localErr + "\nSystem error: " + super.getMessage();
    }
}
