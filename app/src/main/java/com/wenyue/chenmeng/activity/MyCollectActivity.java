package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.MyCollectAdapter;
import com.wenyue.chenmeng.entity.CollectionInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的收藏
 */
@ContentView(R.layout.activity_my_collect)
public class MyCollectActivity extends BaseActivity {
    @ViewInject(R.id.rv_collect)
    private RecyclerView rv_collect;
    @ViewInject(R.id.tv_video)
    private TextView tv_video;
    @ViewInject(R.id.tv_commodity)
    private TextView tv_commodity;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;
    @ViewInject(R.id.not_info)
    private View not_info;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("我的收藏");

        mInitRecyclerView(rv_collect, 2, 2);

        tv_video.setBackgroundResource(R.color.main_update_color);
        tv_video.setTextColor(ContextCompat.getColor(this, R.color.white));

        Map<String, String> map = new HashMap<>();
        map.put("type", "0");
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.MEMBER_COLLECTION_LIST), map, spin_kit, new MemberCollectionListXCallBack());
    }

    @Event(value = {R.id.tv_video, R.id.tv_commodity})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.tv_video:   //视频
                clearColor();
                tv_video.setBackgroundResource(R.color.main_update_color);
                tv_video.setTextColor(ContextCompat.getColor(this, R.color.white));
                break;
            case R.id.tv_commodity:   //商品
                clearColor();
                tv_commodity.setBackgroundResource(R.color.main_update_color);
                tv_commodity.setTextColor(ContextCompat.getColor(this, R.color.white));
                break;
        }
    }

    /**
     * 收藏列表
     */
    private class MemberCollectionListXCallBack implements HttpHelper.XCallBack {
        private List<CollectionInfo> list = null;

        @Override
        public void onResponse(String result) {
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, CollectionInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }
            if (null != list) {
                MyCollectAdapter myCollectAdapter = new MyCollectAdapter(list, MyCollectActivity.this);
                rv_collect.setAdapter(myCollectAdapter);
                myCollectAdapter.setOnItemClickListener(new MyCollectAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        Intent intent = new Intent();
                        intent.putExtra("id", String.valueOf(list.get(position).getObject().getId()));
                        openActivity(intent, VideoDetailActivity.class);
                    }
                });
            }
        }
    }

    private void clearColor() {
        tv_video.setBackgroundResource(R.color.main_background);
        tv_video.setTextColor(ContextCompat.getColor(this, R.color.black));
        tv_commodity.setBackgroundResource(R.color.main_background);
        tv_commodity.setTextColor(ContextCompat.getColor(this, R.color.black));
    }
}
