package com.atm.model;

import com.atm.myException.BalanceException;

public abstract class Account {
    private Long id;//账户id
    private String password;//密码
    private String name;//姓名
    private String personId;//身份证号
    private String email;//邮箱
    private double balance;//余额

    //存款
    public final Account deposition(double amount){
        if (amount >= 0 ) {
            this.balance += amount;
        }else {
            throw new IllegalArgumentException("取款金额不能为负");
        }
        return this;
    }
    //取款
    public abstract Account withDraw(double amount) throws BalanceException;
    //校验密码
    public boolean checkPassword(String pwd){
        return password.equals(pwd);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", personId='" + personId + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Account() {
    }

    public Account(Long id, String password, String name, String personId, String email, double balance) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.personId = personId;
        this.email = email;
        this.balance = balance;
    }
}
