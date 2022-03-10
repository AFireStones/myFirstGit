package com.atm.myException;

public class LoanException extends ATMException{
    public LoanException() {
        super();
    }

    public LoanException(String message) {
        super(message);
    }
}
