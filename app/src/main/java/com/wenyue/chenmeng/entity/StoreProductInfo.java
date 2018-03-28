package com.wenyue.chenmeng.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品信息
 */
public class StoreProductInfo implements Serializable {
    /**
     * code :
     * sales : 0
     * images : ["/media/store/product/2-20180126104405883000.png"]
     * file :
     * subcategory_text : 语文
     * id : 2
     * name : 初中七年级数学
     * category_text : 教程
     * is_virtual : false
     * guide_price : 30
     * subject_text :
     * grade_text :
     * stock : 100
     */
    private String code;
    private int sales;
    private String file;
    private String subcategory_text;
    private String id;
    private String name;
    private String category_text;
    private boolean is_virtual;
    private Double guide_price;
    private String subject_text;
    private String grade_text;
    private int stock;
    private List<String> images = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getSubcategory_text() {
        return subcategory_text;
    }

    public void setSubcategory_text(String subcategory_text) {
        this.subcategory_text = subcategory_text;
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

    public String getCategory_text() {
        return category_text;
    }

    public void setCategory_text(String category_text) {
        this.category_text = category_text;
    }

    public boolean isIs_virtual() {
        return is_virtual;
    }

    public void setIs_virtual(boolean is_virtual) {
        this.is_virtual = is_virtual;
    }

    public void setGuide_price(Double guide_price) {
        this.guide_price = guide_price;
    }

    public Double getGuide_price() {
        return guide_price;
    }

    public String getSubject_text() {
        return subject_text;
    }

    public void setSubject_text(String subject_text) {
        this.subject_text = subject_text;
    }

    public String getGrade_text() {
        return grade_text;
    }

    public void setGrade_text(String grade_text) {
        this.grade_text = grade_text;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
