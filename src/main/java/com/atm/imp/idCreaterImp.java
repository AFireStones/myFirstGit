package com.atm.imp;

import com.atm.dao.ArrayDao;

import java.util.Calendar;

public interface idCreaterImp {
    /** 生成id
     * @return
     */
    default long createId(){
        Calendar calendar = Calendar.getInstance();
        String lastid = String.valueOf(this.getLastId());
        //截取
        int year = Integer.parseInt(lastid.substring(8,12));
        int month = Integer.parseInt(lastid.substring(12,14));
        if (calendar.get(Calendar.MONTH)+1 != month){
            this.setLastId(1L);
        }
        if (calendar.get(Calendar.YEAR) != year){
            this.setLastId(1L);
        }
        String date = ""+calendar.get(Calendar.YEAR)+String.format("%02d",calendar.get(Calendar.MONTH)+1);

        Long id = Long.valueOf("86815021"+date+String.format("%04d",getLastId()));
        setLastId(getLastId()+1);
        return id;
    }

    long getLastId();
    void setLastId(long lastid);
}
