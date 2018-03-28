package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 提现记录
 */
public class CashRegisterInfo implements Serializable {
    /**
     * account_bank : 支付宝
     * create_user : null
     * account_name : zhangsan
     * notes : null
     * balance_before : 100
     * account_number : aa@qq.com
     * total_fee : 10
     * balance_after : null
     */
    private String account_bank;
    private String create_user;
    private String account_name;
    private String notes;
    private String balance_before;
    private String account_number;
    private String total_fee;
    private String balance_after;

    public String getAccount_bank() {
        return account_bank;
    }

    public void setAccount_bank(String account_bank) {
        this.account_bank = account_bank;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getBalance_before() {
        return balance_before;
    }

    public void setBalance_before(String balance_before) {
        this.balance_before = balance_before;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getBalance_after() {
        return balance_after;
    }

    public void setBalance_after(String balance_after) {
        this.balance_after = balance_after;
    }
}
