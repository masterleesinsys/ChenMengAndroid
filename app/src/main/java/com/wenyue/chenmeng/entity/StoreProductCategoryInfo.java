package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 商城分类
 */
public class StoreProductCategoryInfo implements Serializable{
    /**
     * parent_name :
     * icon :
     * id : 1
     * name : 教程
     */
    private String parent_name;
    private String icon;
    private String id;
    private String name;

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
