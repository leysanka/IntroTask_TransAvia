package com.epam.transavia.demo.core.exceptions;

public class ScreenshotHelperException extends Throwable {
    private String localErr;

    public ScreenshotHelperException(String s) {
        this.localErr = s;
    }

    @Override
    public String getMessage() {
        return this.localErr + "\nSystem error: " + super.getMessage();
    }
}
