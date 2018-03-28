package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 课程列表
 */
public class CourseInfo implements Serializable {
    /**
     * grade : 3
     * public_time : 01-16
     * thumb : /media/video/thumb//4-20180116225210366400.png
     * subject_text : 语文
     * press : 1
     * press_text : 人教版
     * id : 4
     * grade_text : 九年级
     * subject : 1
     */

    private int grade;
    private String public_time;
    private String thumb;
    private String subject_text;
    private int press;
    private String press_text;
    private int id;
    private String grade_text;
    private int subject;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getPublic_time() {
        return public_time;
    }

    public void setPublic_time(String public_time) {
        this.public_time = public_time;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getSubject_text() {
        return subject_text;
    }

    public void setSubject_text(String subject_text) {
        this.subject_text = subject_text;
    }

    public int getPress() {
        return press;
    }

    public void setPress(int press) {
        this.press = press;
    }

    public String getPress_text() {
        return press_text;
    }

    public void setPress_text(String press_text) {
        this.press_text = press_text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrade_text() {
        return grade_text;
    }

    public void setGrade_text(String grade_text) {
        this.grade_text = grade_text;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }
}
