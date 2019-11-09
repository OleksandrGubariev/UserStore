package com.gubarev.usersstore.exception;

public class ConnectDbException extends RuntimeException {
    
    public ConnectDbException(String msg, Throwable cause){
        super(msg, cause);
    }
}
