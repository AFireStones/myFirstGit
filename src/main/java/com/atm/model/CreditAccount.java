package com.atm.model;

import com.atm.myException.BalanceException;

public class CreditAccount extends Account{
    private double ceiling;

    @Override
    public CreditAccount withDraw(double amount) throws BalanceException {
        if (amount <= super.getBalance()){
            super.setBalance(super.getBalance()-amount);
        }else if (amount <= super.getBalance()+ceiling){
            super.setBalance(0);
        }else {
            throw new BalanceException("信用额度不足");
        }
        return this;
    }


    public CreditAccount() {
    }

    public CreditAccount(Long id, String password, String name, String personId, String email, double balance, double ceiling) {
        super(id, password, name, personId, email, balance);
        this.ceiling = ceiling;
    }

    public double getCeiling() {
        return ceiling;
    }

    public void setCeiling(double ceiling) {
        this.ceiling = ceiling;
    }
}
