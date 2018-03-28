package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.github.ybq.android.spinkit.SpinKitView;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.VideoListAdapter;
import com.wenyue.chenmeng.entity.CourseInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索结果
 */
@ContentView(R.layout.activity_search_result)
public class SearchResultActivity extends BaseActivity {
    @ViewInject(R.id.rv_search_result)
    private RecyclerView rv_search_result;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("搜索结果");

        Intent intent = getIntent();
        String keyword = intent.getStringExtra("keyword");

        mInitRecyclerView(rv_search_result, 2, 2);

        Map<String, String> map = new HashMap<>();
        map.put("keyword", keyword);
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.VIDEO_SEARCH), map, spin_kit, new VideoSearchXCallBack());
    }

    /**
     * 视频搜索
     */
    private class VideoSearchXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            List<CourseInfo> list = null;
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, CourseInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
            }
            if (null != list) {
                VideoListAdapter videoListAdapter = new VideoListAdapter(list, SearchResultActivity.this);
                rv_search_result.setAdapter(videoListAdapter);
                videoListAdapter.setOnItemClickListener(new VideoListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position, String id) {
                        Intent intent = new Intent();
                        intent.putExtra("id", id);
                        openActivity(intent, VideoDetailActivity.class);
                    }
                });
            }
        }
    }
}
