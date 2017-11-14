package com.epam.transavia.demo.core.exceptions;

public class NotInstanceOfJavascriptExecutorException extends RuntimeException {
    private String localErr;

    public NotInstanceOfJavascriptExecutorException(String s) {
        this.localErr = s;
    }

    @Override
    public String getMessage() {
        return this.localErr + "\nSystem error: " + super.getMessage();
    }
}
