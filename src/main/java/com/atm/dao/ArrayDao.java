package com.atm.dao;

import com.atm.imp.idCreaterImp;
import com.atm.model.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArrayDao implements idCreaterImp {
    private Account[] accounts = new Account[50];

    private final HashMap<String, List<Account>> accountsmap;
    private List<Account> accountList;
    private int index = 0;

    private long lastId = 100000000000000000L;
    {
        this.accountList = new ArrayList<>();
        this.accountsmap = new HashMap<>();
    }

    @Override
    public long getLastId() {
        return lastId;
    }
    @Override
    public void setLastId(long lastId) {
        this.lastId = lastId;
    }

    private static class singleton{
        private static final ArrayDao instance = new ArrayDao();
    }

    public Account selectOne(Long id, String pwd){
        if (accountList.contains(id)) {
            for (int i = 0; i < accountList.size(); i++) {
                if (accountList.get(i).getId()==id&&accountList.get(i).checkPassword(pwd))
                    return accountList.get(i);
            }
        }
        return null;
    }
    public Account selectOne(Long id){
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getId()==id)
                return accountList.get(i);
        }
        return null;
    }

    public Account[] selectAll(){
        return (Account[]) accountList.toArray();
    }

    public boolean insert(Account acc){
        if (acc != null) {
            if (!accountList.contains(acc)){
                accountList.add(acc);
            }else {
                return false;
            }
            String person = acc.getPersonId();
            if (accountsmap.containsKey(person)){
                accountsmap.get(person).add(acc);
            }else {
                ArrayList<Account> arrayList = new ArrayList<>();
                arrayList.add(acc);
                accountsmap.put(person, arrayList);
            }
            return true;
        }
        return false;
    }

    public Account delete(Long id){
       Account account = selectOne(id);
       if (account != null){
           accountList.remove(account);
           accountsmap.remove(account.getPersonId());
       }else {
           throw new IllegalArgumentException("删除异常");
       }
       return account;
    }

    public Account update(Account acc){
        Account account = selectOne(acc.getId());
        if (account != null){
            for (int i = 0; i < accountList.size(); i++) {
                if (accountList.get(i).getId()==acc.getId()){
                    accountList.remove(i);
                    accountList.add(acc);
                    accountsmap.get(account.getPersonId()).remove(account);
                    accountsmap.get(account.getPersonId()).add(acc);
                }
            }
        }else {
            throw new IllegalArgumentException("未找到要修改的账户");
        }
        return acc;
    }
    public static ArrayDao getInstanceDao(){
        return singleton.instance;
    }

}
