package com.atm.imp;

import com.atm.model.Account;
import com.atm.myException.LoanException;

public interface Loanable {
    //贷款
    Account requestLoan(double money) throws LoanException;
    //还贷
    Account payLoan(double money);


}
