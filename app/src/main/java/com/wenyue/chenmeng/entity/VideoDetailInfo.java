package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 视频详情
 */
public class VideoDetailInfo implements Serializable {
    /**
     * grade : null
     * create_time : 2018-02-01 16:00
     * file : /media/video/file//9-20180210025844968604.mp4
     * id : 9
     * subject : null
     * thumb : /media/video/thumb//9-20180210025844808157.png
     * volume_text :
     * featured_category : 21
     * press_text :
     * subject_text :
     * collected : false
     * description : 夏有
     * volume : null
     * public_time : 02-10
     * featured_category_text : 三字经
     * press : null
     * teacher : {"teacher_id":18,"photo":"/media/user/teacher//1-photo-20180208103925617754.jpg","name":null,"stars":null}
     * chapter : null
     * chapter_text :
     * sub_featured_category_text : 三字经
     * grade_text :
     * sub_featured_category : 22
     */
    private String grade;
    private String create_time;
    private String file;
    private int id;
    private String subject;
    private String thumb;
    private String volume_text;
    private int featured_category;
    private String press_text;
    private String subject_text;
    private boolean collected;
    private String description;
    private String volume;
    private String public_time;
    private String featured_category_text;
    private String press;
    private TeacherInfo teacher = new TeacherInfo();
    private String chapter;
    private String chapter_text;
    private String sub_featured_category_text;
    private String grade_text;
    private int sub_featured_category;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getVolume_text() {
        return volume_text;
    }

    public void setVolume_text(String volume_text) {
        this.volume_text = volume_text;
    }

    public int getFeatured_category() {
        return featured_category;
    }

    public void setFeatured_category(int featured_category) {
        this.featured_category = featured_category;
    }

    public String getPress_text() {
        return press_text;
    }

    public void setPress_text(String press_text) {
        this.press_text = press_text;
    }

    public String getSubject_text() {
        return subject_text;
    }

    public void setSubject_text(String subject_text) {
        this.subject_text = subject_text;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPublic_time() {
        return public_time;
    }

    public void setPublic_time(String public_time) {
        this.public_time = public_time;
    }

    public String getFeatured_category_text() {
        return featured_category_text;
    }

    public void setFeatured_category_text(String featured_category_text) {
        this.featured_category_text = featured_category_text;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public TeacherInfo getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherInfo teacher) {
        this.teacher = teacher;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getChapter_text() {
        return chapter_text;
    }

    public void setChapter_text(String chapter_text) {
        this.chapter_text = chapter_text;
    }

    public String getSub_featured_category_text() {
        return sub_featured_category_text;
    }

    public void setSub_featured_category_text(String sub_featured_category_text) {
        this.sub_featured_category_text = sub_featured_category_text;
    }

    public String getGrade_text() {
        return grade_text;
    }

    public void setGrade_text(String grade_text) {
        this.grade_text = grade_text;
    }

    public int getSub_featured_category() {
        return sub_featured_category;
    }

    public void setSub_featured_category(int sub_featured_category) {
        this.sub_featured_category = sub_featured_category;
    }
}
