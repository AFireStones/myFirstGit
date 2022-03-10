package com.atm.myException;

public class LoginException extends ATMException{
    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }
}
