package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 广告列表
 */
public class AdvertInfo implements Serializable {
    /**
     * "type_text": "视频",
     * "name": "as",
     * "file": "/media/advert/1-20180210075343434932.png",
     * "target_url": "http://www.baidu.com",
     * "location_text": "首页滚动",
     * "position": 11,
     * "id": 1,
     * "object_id": 1
     */
    private String name;        //广告标题名称
    private String file;        //图片
    private String target_url;      //网址链接
//    private String create_time;     //创建时间
    private Integer position;   //排序
//    private Integer type;       //广告类型
    private String type_text;
    private String location_text;
    private String object_id;
    private Integer id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getType_text() {
        return type_text;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getLocation_text() {
        return location_text;
    }

    public void setLocation_text(String location_text) {
        this.location_text = location_text;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
