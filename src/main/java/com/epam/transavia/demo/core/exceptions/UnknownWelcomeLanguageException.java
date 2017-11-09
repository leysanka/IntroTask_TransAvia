package com.epam.transavia.demo.core.exceptions;

public class UnknownWelcomeLanguageException extends RuntimeException {
    private String localError;

    public UnknownWelcomeLanguageException(String s) {
        this.localError = s;
    }

    @Override
    public String getMessage() {
        return this.localError + "System error: " + super.getMessage();
    }
}
