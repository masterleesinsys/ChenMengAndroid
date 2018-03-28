package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 教师详情
 */
public class TeacherDetailInfo implements Serializable {
    /**
     * status : 1
     * stars : null
     * photo : /media/user/teacher//26-photo-20180210073900042172.jpg
     * collected : false
     * intro : 认真负责，用爱心去铸造孩子们的未来！
     * teacher_id : 20
     * name : 尹仁霞
     * gender : 女
     * subject_text : 数学
     * follower_count : 0
     * grade_text : 一年级
     */
    private int status;
    private String stars;
    private String photo;
    private boolean collected;
    private String intro;
    private String teacher_id;
    private String name;
    private String gender;
    private String subject_text;
    private int follower_count;
    private String grade_text;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSubject_text() {
        return subject_text;
    }

    public void setSubject_text(String subject_text) {
        this.subject_text = subject_text;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public String getGrade_text() {
        return grade_text;
    }

    public void setGrade_text(String grade_text) {
        this.grade_text = grade_text;
    }
}
