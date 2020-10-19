package com.szalay.opencourtwebapp;

public class MuteURLException extends RuntimeException{

    public MuteURLException(String message, Throwable cause) {
        super(message, cause);
    }

    public MuteURLException(String message) {
        super(message);
    }
}
