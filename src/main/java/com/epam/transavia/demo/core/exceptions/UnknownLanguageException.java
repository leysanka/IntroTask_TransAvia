package com.epam.transavia.demo.core.exceptions;

public class UnknownLanguageException extends RuntimeException {
    private  String localError;

    public UnknownLanguageException(String s) {
        this.localError = s;
    }

    @Override
    public String getMessage() {
        return this.localError + "System error: " + super.getMessage();
    }
}
