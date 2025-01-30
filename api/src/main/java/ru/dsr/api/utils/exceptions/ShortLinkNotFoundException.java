package ru.dsr.api.utils.exceptions;

public class ShortLinkNotFoundException extends RuntimeException{
    public ShortLinkNotFoundException(String message) {
        super(message);
    }
}
