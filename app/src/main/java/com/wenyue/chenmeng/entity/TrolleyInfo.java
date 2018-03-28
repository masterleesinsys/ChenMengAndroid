package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 购物车内容
 */
public class TrolleyInfo implements Serializable {
    /**
     * product : 7
     * price : 56
     * product_text : 小学一年级上册语文
     * product_image : /media/store/product/7-20180208082725649001.jpg
     * create_time : 2018-02-12 03:13
     * quantily : 1
     */
    private int product;
    private Double price;
    private String product_text;
    private String product_image;
    private String create_time;
    private int quantily;

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProduct_text() {
        return product_text;
    }

    public void setProduct_text(String product_text) {
        this.product_text = product_text;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getQuantily() {
        return quantily;
    }

    public void setQuantily(int quantily) {
        this.quantily = quantily;
    }
}
