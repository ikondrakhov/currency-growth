package com.currency.exceptions;

public class NoSuchCurrencyException extends Exception {
    public NoSuchCurrencyException(String errorMessage) {
        super(errorMessage);
    }
}
