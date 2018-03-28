package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 收藏&关注
 */
public class CollectionInfo implements Serializable {
    /**
     * create_time : 2018-02-11 15:28
     * type : 0
     * id : 28
     */
    private String create_time;
    private int type;
    private int id;
    private ObjectInfo object = new ObjectInfo();

    public ObjectInfo getObject() {
        return object;
    }

    public void setObject(ObjectInfo object) {
        this.object = object;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
