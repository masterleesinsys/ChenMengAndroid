package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 教师
 */
public class TeacherInfo implements Serializable {
    /**
     * teacher_id : 17
     * photo : /media/user/teacher//24-photo-20180125070209049000.png
     * name : 张海涛
     */
    private String teacher_id;
    private String photo;
    private String name;

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
