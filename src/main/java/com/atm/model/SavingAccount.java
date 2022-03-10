package com.atm.model;

import com.atm.myException.BalanceException;

public class SavingAccount extends Account{
    public SavingAccount() {
    }

    public SavingAccount(Long id, String password, String name, String personId, String email, double balance) {
        super(id, password, name, personId, email, balance);
    }

    @Override
    public Account withDraw(double amount) throws BalanceException {
        if (super.getBalance() >= amount){
            super.setBalance(super.getBalance()-amount);;
        }else {
            throw new BalanceException("余额不足");
        }
        return this;
    }
}
