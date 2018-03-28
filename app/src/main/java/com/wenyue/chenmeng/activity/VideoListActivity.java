package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.github.ybq.android.spinkit.SpinKitView;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.GradeAdapter;
import com.wenyue.chenmeng.adapter.VideoListAdapter;
import com.wenyue.chenmeng.entity.CourseInfo;
import com.wenyue.chenmeng.entity.GradeInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * 视频列表
 */
@ContentView(R.layout.activity_video_list)
public class VideoListActivity extends BaseActivity {
    @ViewInject(R.id.rv_grade)
    private RecyclerView rv_grade;
    @ViewInject(R.id.rv_video)
    private RecyclerView rv_video;
    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;
    @ViewInject(R.id.not_info)
    private View not_info;

    private String title = "";
    private String type = "";

    @Override
    protected void initView() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        type = intent.getStringExtra("type");

        if ("featured".equals(type)) {
            rv_grade.setVisibility(View.GONE);
        }

        setStyle(STYLE_BACK);
        setCaption(title);

        initRecycler();

        String resva2 = String.format(Constants.BASIC_MODEL_LIST, "grade");
        HttpHelper.getInstance().get(MyApplication.getURL(resva2), null, spin_kit, new BasicModelGradeListXCallBack());
    }

    private void initRecycler() {
        mInitRecyclerView(rv_video, 2, 2);

        if (type.equals("preview")) {
            rv_grade.setVisibility(View.GONE);
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_grade.setLayoutManager(linearLayoutManager);

        OverScrollDecoratorHelper.setUpOverScroll(rv_grade, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL);
    }

    /**
     * 年级列表
     */
    private class BasicModelGradeListXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            List<GradeInfo> gradeInfoList = null;
            try {
                String data = getHttpResultList(result);
                gradeInfoList = JSON.parseArray(data, GradeInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
            }
            if (gradeInfoList != null) {
                GradeAdapter gradeAdapter = new GradeAdapter(gradeInfoList, VideoListActivity.this);
                rv_grade.setAdapter(gradeAdapter);
                gradeAdapter.setOnItemClickListener(new GradeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position, String grade_id) {
                        Map<String, String> map = new HashMap<>();
                        map.put("grade", grade_id);
                        String resval = String.format(Constants.VIDEO_LIST, type);
                        HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), type.equals("preview") ? null : map, spin_kit, new VideoListXCallBack());
                    }
                });

                Map<String, String> map = new HashMap<>();
                map.put("grade", "");
                String resval = String.format(Constants.VIDEO_LIST, type);
                HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), type.equals("preview") ? null : map, spin_kit, new VideoListXCallBack());
            }
        }
    }

    /**
     * 课程列表
     */
    private class VideoListXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            List<CourseInfo> courseInfoList = null;
            try {
                String data = getHttpResultList(result);
                courseInfoList = JSON.parseArray(data, CourseInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
            }

            if (null != courseInfoList && courseInfoList.size() > 0) {
                not_info.setVisibility(View.GONE);
                VideoListAdapter videoListAdapter = new VideoListAdapter(courseInfoList, VideoListActivity.this);
                rv_video.setAdapter(videoListAdapter);
                videoListAdapter.setOnItemClickListener(new VideoListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position, String id) {
                        Intent intent = new Intent();
                        intent.putExtra("id", id);
                        openActivity(intent, VideoDetailActivity.class);
                    }
                });
            } else {
                not_info.setVisibility(View.VISIBLE);
                VideoListAdapter videoListAdapter = new VideoListAdapter(null, VideoListActivity.this);
                rv_video.setAdapter(videoListAdapter);
            }
        }
    }
}
