package com.atm.myException;

public class BalanceException extends ATMException{
    BalanceException() {

    }

    public BalanceException(String message){
        super(message);
    }
}
