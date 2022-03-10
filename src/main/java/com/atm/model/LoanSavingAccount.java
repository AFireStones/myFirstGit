package com.atm.model;

import com.atm.imp.Loanable;
import com.atm.myException.LoanException;

public class LoanSavingAccount extends SavingAccount implements Loanable {
    private double loanAmount;


    @Override
    public Account requestLoan(double money) throws LoanException {
        if (money<0) {
            throw new LoanException("贷款金额出错");
        }
        loanAmount = money;
        super.setBalance(super.getBalance()+money);
        return this;
    }

    @Override
    public Account payLoan(double money){
        if (money < loanAmount){
            loanAmount -= money;
            super.setBalance(super.getBalance() - money);
        }else {
            throw new IllegalArgumentException("金额错误");
        }
        return this;
    }

    public LoanSavingAccount() {
    }

    public LoanSavingAccount(Long id, String password, String name, String personId, String email, double balance, double loanAmount) {
        super(id, password, name, personId, email, balance);
        this.loanAmount = loanAmount;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }
}
