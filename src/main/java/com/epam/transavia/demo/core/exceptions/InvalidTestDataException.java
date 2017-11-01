package com.epam.transavia.demo.core.exceptions;

public class InvalidTestDataException extends Throwable {
    private String testDataError;

    public InvalidTestDataException(String s) {
        this.testDataError = s;
    }

    @Override
    public String getMessage() {
        return this.testDataError + super.getMessage();
    }
}
