package com.wenyue.chenmeng.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页视频列表
 */
public class VideoHomeInfo implements Serializable {
    private List<VideoDetailInfo> recommend = new ArrayList<>();    //推荐视频
    private List<VideoDetailInfo> preview = new ArrayList<>();      //体验视频
    private List<VideoDetailInfo> last_update = new ArrayList<>();  //最近更新

    public List<VideoDetailInfo> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<VideoDetailInfo> recommend) {
        this.recommend = recommend;
    }

    public List<VideoDetailInfo> getPreview() {
        return preview;
    }

    public void setPreview(List<VideoDetailInfo> preview) {
        this.preview = preview;
    }

    public List<VideoDetailInfo> getLast_update() {
        return last_update;
    }

    public void setLast_update(List<VideoDetailInfo> last_update) {
        this.last_update = last_update;
    }
}
