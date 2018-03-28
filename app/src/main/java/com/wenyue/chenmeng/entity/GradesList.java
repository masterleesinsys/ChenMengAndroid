package com.wenyue.chenmeng.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 学科
 */
public class GradesList implements Serializable{
    private String gradeName;
    private List<String> subList;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public List<String> getSubList() {
        return subList;
    }

    public void setSubList(List<String> subList) {
        this.subList = subList;
    }
}
