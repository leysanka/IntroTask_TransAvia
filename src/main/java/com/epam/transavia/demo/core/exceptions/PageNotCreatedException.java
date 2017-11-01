package com.epam.transavia.demo.core.exceptions;

public class PageNotCreatedException extends RuntimeException {
    private String pageError;

    public PageNotCreatedException(String s) {
        this.pageError = s;
    }

    @Override
    public String getMessage() {
        return this.pageError + "System error: " + super.getMessage();
    }
}
