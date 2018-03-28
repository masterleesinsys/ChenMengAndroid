package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 资讯详情(ById)
 */
public class NewsDetailInfo implements Serializable {
    private String content; //资讯内容
    private String image; //资讯图片
    private String create_time; //创建时间
    private Integer id;
    private String title;   //标题

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
