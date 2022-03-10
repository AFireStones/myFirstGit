package com.atm.myException;

public class RegisterException extends ATMException{
    public RegisterException() {
        super();
    }

    public RegisterException(String message) {
        super(message);
    }
}
