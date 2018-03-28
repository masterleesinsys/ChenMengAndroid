package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 资讯详情
 */
public class JournalismInfo implements Serializable {
    /**
     * image :
     * create_time : 2018-02-07 01:13
     * id : 7
     * title : 熟悉掌握这些口诀, 小学语文考试不是问题!
     */
    private String image;
    private String create_time;
    private String id;
    private String title;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
