package com.atm.service;

import com.atm.dao.ArrayDao;
import com.atm.imp.Loanable;
import com.atm.model.*;
import com.atm.myException.BalanceException;
import com.atm.myException.LoanException;
import com.atm.myException.RegisterException;

import javax.security.auth.login.LoginException;


public class Bank {


    private static class singleton{
        private static final Bank instance = new Bank();
    }

    private Bank() {
    }

    public static Bank getInstanceDao(){
        return Bank.singleton.instance;
    }
    //开户

    /**
     *      开户
     * @param password      密码
     * @param repassword    重复密码
     * @param name          用户姓名
     * @param personID      身份证号码
     * @param email         邮箱
     * @param type          账户类型
     * @return              注册的账户
     */
    public Account register(String password, String repassword, String name, String personID, String email, int type) throws RuntimeException, RegisterException {
        Account acc = null;
        ArrayDao arrayDao = ArrayDao.getInstanceDao();
        long id = arrayDao.createId();
        if (password.equals(repassword)){
            switch (type){
                case 0:
                    acc = new SavingAccount(id,password,name,personID,email,0);
                    break;
                case 1 :
                    acc = new CreditAccount(id,password,name,personID,email,0,0);
                    break;
                case 2 :
                    acc = new LoanSavingAccount(id,password,name,personID,email,0,50000);
                    break;
                case 3 :
                    acc = new LoanCreditAccount(id,password,name,personID,email,0,0,0);
                    break;
            }
            if (!arrayDao.insert(acc)){
                throw new RegisterException("注册失败");
            }
        }else {
            throw new RegisterException("两次密码不相同");
        }

        return acc;
    }

    /**
     *  登录
     * @param id        账户id
     * @param password  密码
     * @return          登录账户
     */
    public Account login(Long id, String password) throws LoginException {
        ArrayDao  arrayDao = ArrayDao.getInstanceDao();
        Account account = arrayDao.selectOne(id,password);
        if (account == null) {
            throw new LoginException("");
        }
        return account;
    }

    /**
     *  存款
     * @param id        账户id
     * @param money     存钱数额
     * @return          存钱账户
     */
    public Account deposit(Long id, double money){

        ArrayDao arrayDao = ArrayDao.getInstanceDao();
        if (arrayDao.selectOne(id) != null){
            return arrayDao.selectOne(id).deposition(money);
        }else {
            return null;
        }

    }

    /**
     *  取款
     * @param id        账户id
     * @param password  密码
     * @param money     取款额
     * @return          取款账户
     */
    public Account withDraw(Long id, String password, double money){
        ArrayDao arrayDao = ArrayDao.getInstanceDao();
        if (arrayDao.selectOne(id) != null){
            if (arrayDao.selectOne(id).checkPassword(password)) {
                try {
                    return arrayDao.selectOne(id).withDraw(money);
                } catch (BalanceException e) {
                    e.printStackTrace();
                }
            }
        }else {
            return null;
        }
        return null;
    }

    /**
     * 设置透支额度
     * @param id        账户id
     * @param money     透支额度
     * @return          账户
     */
    public Account updateCeiling(Long id, double money) throws BalanceException {
        ArrayDao arrayDao = ArrayDao.getInstanceDao();
        if ( arrayDao.selectOne(id) != null&&arrayDao.selectOne(id) instanceof CreditAccount){
            if (((CreditAccount) arrayDao.selectOne(id)).getCeiling() < money) {
                ((CreditAccount) arrayDao.selectOne(id)).setCeiling(money);
            }else {
                throw new BalanceException("额度已低于透支额");
            }
        }else {
            throw new IllegalArgumentException("账户出错");
        }
        return arrayDao.selectOne(id);
    }
    //转账
    public boolean transfer(Long from, Long to, double money){
        ArrayDao arrayDao = ArrayDao.getInstanceDao();
        Account fromA = arrayDao.selectOne(from);
        Account fromB = arrayDao.selectOne(to);
        if (fromA != null && fromB != null) {
            try {
                fromA.withDraw(money);
                fromB.deposition(money);
            }catch (Exception e) {
                throw new RuntimeException("转账失败");
            }
            return true;
        }else {
            return false;
        }
    }
    public int total_balance(){
        ArrayDao arrayDao = ArrayDao.getInstanceDao();
        Account[] accounts = arrayDao.selectAll();
        int total = 0;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }

    public int total_ceil(){
        ArrayDao arrayDao = ArrayDao.getInstanceDao();
        Account[] accounts = arrayDao.selectAll();
        int total = 0;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }
    //贷款总数
    public double total_loan(){
        ArrayDao arrayDao = ArrayDao.getInstanceDao();
        Account[] accounts = arrayDao.selectAll();
        int total = 0;
        for (Account account : accounts) {
            if (account instanceof LoanSavingAccount){
                total += ((LoanSavingAccount) account).getLoanAmount();
            }
            if (account instanceof LoanCreditAccount){
                total += ((LoanCreditAccount) account).getLoanAmount();
            }
        }
        return total;
    }
    //贷款
    public Account requestLoan(Long id , double money){
        ArrayDao arrayDao = ArrayDao.getInstanceDao();
        Account account = arrayDao.selectOne(id);
        Loanable acc = (Loanable) account;
        try {
            acc.requestLoan(money);
        } catch (LoanException e) {
            e.printStackTrace();
        }
        return account;
    }
    //偿还贷款
    public Account payLoan(Long id , double money){
        ArrayDao arrayDao = ArrayDao.getInstanceDao();
        Account account = arrayDao.selectOne(id);
        if (account instanceof LoanSavingAccount){
            LoanSavingAccount loanSavingAccount = (LoanSavingAccount) account;
            if (money < loanSavingAccount.getLoanAmount() && money > loanSavingAccount.getBalance()) {
                loanSavingAccount.setLoanAmount(loanSavingAccount.getLoanAmount()-money);
                loanSavingAccount.setBalance(loanSavingAccount.getBalance()-money);
            }
        }
        if (account instanceof LoanCreditAccount){
            LoanCreditAccount loanCreditAccount = (LoanCreditAccount) account;
            if (money < loanCreditAccount.getLoanAmount() && money > loanCreditAccount.getBalance()) {
                loanCreditAccount.setLoanAmount(loanCreditAccount.getLoanAmount()-money);
                loanCreditAccount.setBalance(loanCreditAccount.getBalance()-money);
            }
        }
        return account;
    }

}
