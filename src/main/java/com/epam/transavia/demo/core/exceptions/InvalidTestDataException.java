package com.epam.transavia.demo.core.exceptions;

public class InvalidTestDataException extends RuntimeException {
    private String testDataError;

    public InvalidTestDataException(String s) {
        this.testDataError = s;
    }

    @Override
    public String getMessage() {
        return this.testDataError + "\n" + "System error: " + super.getMessage();
    }
}
