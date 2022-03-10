package com.atm.model;

import com.atm.imp.Loanable;

public class LoanCreditAccount extends CreditAccount implements Loanable {
    private double loanAmount;
    @Override
    public Account requestLoan(double money) {
        loanAmount = money;
        super.setBalance(super.getBalance()+money);
        return this;
    }

    @Override
    public Account payLoan(double money) {
        if (money < loanAmount){
            loanAmount -= money;
            super.setBalance(super.getBalance() - money);
        }else {
            throw new IllegalArgumentException("金额错误");
        }
        return this;
    }

    public LoanCreditAccount() {
    }

    public LoanCreditAccount(Long id, String password, String name, String personId, String email, double balance, double ceiling, double loanAmount) {
        super(id, password, name, personId, email, balance, ceiling);
        this.loanAmount = loanAmount;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }
}
