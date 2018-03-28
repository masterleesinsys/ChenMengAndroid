package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 会员
 */
public class MemberInfo implements Serializable {
    /**
     * level : 0
     * grade : 1
     * level_text : 0
     * total_fee : 0
     * icon : /media/member/23_1516882313.png
     * grade_text : 一年级
     */
    private int level;
    private Double grade;
    private String level_text;
    private String vip_expire_date;
    private Double total_fee;
    private String icon;
    private String grade_text;

    public String getVip_expire_date() {
        return vip_expire_date;
    }

    public void setVip_expire_date(String vip_expire_date) {
        this.vip_expire_date = vip_expire_date;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getLevel_text() {
        return level_text;
    }

    public void setLevel_text(String level_text) {
        this.level_text = level_text;
    }

    public Double getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Double total_fee) {
        this.total_fee = total_fee;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGrade_text() {
        return grade_text;
    }

    public void setGrade_text(String grade_text) {
        this.grade_text = grade_text;
    }
}
