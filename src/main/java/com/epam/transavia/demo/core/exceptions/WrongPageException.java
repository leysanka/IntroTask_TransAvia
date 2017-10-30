package com.epam.transavia.demo.core.exceptions;

public class WrongPageException extends Throwable {
    private String pageMessage;


    public WrongPageException(String pageMessage) {
        this.pageMessage = pageMessage;
    }

    public WrongPageException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return this.pageMessage + "Exception cause: " + super.getCause();
    }
}
