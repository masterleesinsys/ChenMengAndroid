package com.wenyue.chenmeng.entity;


import java.io.Serializable;

/**
 * 购买/升级 vip 列表
 */
public class UpGeadeVipList implements Serializable {
    private int image;
    private String vip;
    private String one;
    private String two;
    private String three;
    private String price;

    public UpGeadeVipList(int image, String vip, String one, String two, String three, String price) {
        this.image = image;
        this.vip = vip;
        this.one = one;
        this.two = two;
        this.three = three;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
