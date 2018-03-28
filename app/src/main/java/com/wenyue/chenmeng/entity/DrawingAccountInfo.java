package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 提现账户
 */
public class DrawingAccountInfo implements Serializable {
    /**
     * create_user : 21
     * type_text : 微信
     * create_time : 2018-02-11 09:16
     * account_number : 15896847719
     * account_bank : 支付宝
     * create_user_text : 17760761719
     * type : 1
     * id : 7
     * account_name : 小白
     */
    private String create_user;
    private String type_text;
    private String create_time;
    private String account_number;
    private String account_bank;
    private String create_user_text;
    private int type;
    private String id;
    private String account_name;

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getType_text() {
        return type_text;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_bank() {
        return account_bank;
    }

    public void setAccount_bank(String account_bank) {
        this.account_bank = account_bank;
    }

    public String getCreate_user_text() {
        return create_user_text;
    }

    public void setCreate_user_text(String create_user_text) {
        this.create_user_text = create_user_text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }
}
