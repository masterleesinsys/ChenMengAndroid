package com.wenyue.chenmeng.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品详情
 */
public class MallDetailInfo implements Serializable {
    /**
     * code :
     * description : <h1 title="2018春 小学教材全解 五年级数学下 人教版(RJ版)" style="margin: 0px auto; padding: 0px; list-style-type: none; border: 0px; line-height: 24px; max-height: 48px; overflow: hidden; color: rgb(50, 50, 50); font-size: 18px; font-family: Verdana, &quot;Microsoft Yahei&quot;; white-space: normal; background-color: rgb(255, 255, 255);">2018春 小学教材全解 五年级数学下 人教版(RJ版)</h1><h2 style="margin: 0px; padding: 6px 0px 0px; font-size: 16px; font-weight: normal; list-style-type: none; border: 0px; max-height: 96px; overflow: hidden; line-height: 24px; color: rgb(100, 100, 100); font-family: Verdana, &quot;Microsoft Yahei&quot;; white-space: normal; background-color: rgb(255, 255, 255);"><span class="head_title_name" title="学生用它能自学 教师用它能备课 家长用它能辅导&nbsp;购买套装，请点击！&nbsp;" style="color: rgb(50, 50, 50);">学生用它能自学 教师用它能备课 家长用它能辅导</span></h2><p><span style="color: rgb(51, 51, 51); font-family: &quot;Microsoft YaHei&quot;; font-size: 14px; background-color: rgb(248, 248, 248);">会当凌绝顶，一览众山小。 只有出乎其类，方能拔乎其萃。目前，《小学教材全解》的同类产品已不下百种，但薛金星主编的《小学教材全解》依旧在市场上 倍受青睐 。</span></p>
     * sales : 0
     * discount_price : 24
     * images : ["/media/store/product/15-20180209013155461478.jpg"]
     * file :
     * subcategory_text : 数学
     * id : 15
     * name : 2018春 小学教材全解 五年级数学下
     * category_text : 教程
     * is_virtual : false
     * guide_price : 26.7
     * subject_text :
     * collected : false
     * grade_text :
     * stock : 5692
     */
    private String code;
    private String description;
    private int sales;
    private Double discount_price;
    private String file;
    private String subcategory_text;
    private String id;
    private String name;
    private String category_text;
    private boolean is_virtual;
    private double guide_price;
    private String subject_text;
    private boolean collected;
    private String grade_text;
    private int stock;
    private List<String> images = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public Double getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(Double discount_price) {
        this.discount_price = discount_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getGuide_price() {
        return guide_price;
    }

    public void setGuide_price(double guide_price) {
        this.guide_price = guide_price;
    }

    public String getSubject_text() {
        return subject_text;
    }

    public void setSubject_text(String subject_text) {
        this.subject_text = subject_text;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
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
