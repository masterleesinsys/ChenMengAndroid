package com.wenyue.chenmeng.entity;

import java.io.Serializable;

/**
 * 年级
 */
public class GradeInfo implements Serializable {
    /**
     * name : 七年级
     * notes :
     * id : 1
     * stage_text : 初中
     * stage : 1
     */
    private String name;
    private String notes;
    private String id;
    private String stage_text;
    private int stage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStage_text() {
        return stage_text;
    }

    public void setStage_text(String stage_text) {
        this.stage_text = stage_text;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}
