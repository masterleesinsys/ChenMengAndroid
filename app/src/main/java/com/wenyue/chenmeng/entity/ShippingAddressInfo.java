package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 收货地址
 */
public class ShippingAddressInfo implements Serializable {
    /**
     * mobile : 23985
     * is_default : false
     * address : 哦哦哦
     * id : 12
     * name : 恐
     */
    private String mobile;
    private boolean is_default;
    private String address;
    private String id;
    private String name;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isIs_default() {
        return is_default;
    }

    public void setIs_default(boolean is_default) {
        this.is_default = is_default;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
