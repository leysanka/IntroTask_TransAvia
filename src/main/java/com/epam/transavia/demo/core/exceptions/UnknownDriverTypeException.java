package com.epam.transavia.demo.core.exceptions;

public class UnknownDriverTypeException extends RuntimeException {
    private String localMsg;

    public UnknownDriverTypeException(String s) {
        super(s);
    }

}
