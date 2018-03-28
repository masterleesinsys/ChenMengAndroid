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
import com.wenyue.chenmeng.adapter.InformationAdapter;
import com.wenyue.chenmeng.adapter.MessageAdapter;
import com.wenyue.chenmeng.entity.JournalismInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 消息
 */
@ContentView(R.layout.activity_message)
public class MessageActivity extends BaseActivity {
    @ViewInject(R.id.tv_message)
    private TextView tv_message;
    @ViewInject(R.id.tv_information)
    private TextView tv_information;
    @ViewInject(R.id.rv_message)
    private RecyclerView rv_message;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("消息");

        tv_message.setBackgroundResource(R.color.main_update_color);
        tv_message.setTextColor(ContextCompat.getColor(this, R.color.white));

        initRecycler();
    }

    private void initRecycler() {
        mInitRecyclerView(rv_message, 2);
        MessageAdapter messageAdapter = new MessageAdapter(this);
        rv_message.setAdapter(messageAdapter);
        messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                openActivity(VideoDetailActivity.class);
            }
        });
    }

    @Event(value = {R.id.tv_message, R.id.tv_information})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.tv_message:   //消息
                clearColor();
                tv_message.setBackgroundResource(R.color.main_update_color);
                tv_message.setTextColor(ContextCompat.getColor(this, R.color.white));

                MessageAdapter messageAdapter = new MessageAdapter(this);
                rv_message.setAdapter(messageAdapter);
                messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        openActivity(VideoDetailActivity.class);
                    }
                });
                break;
            case R.id.tv_information:   //资讯
                clearColor();
                tv_information.setBackgroundResource(R.color.main_update_color);
                tv_information.setTextColor(ContextCompat.getColor(this, R.color.white));

                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.NEWS_LIST), null, spin_kit, new NewsListXCallBack());
                break;
        }
    }

    private void clearColor() {
        tv_message.setBackgroundResource(R.color.main_background);
        tv_message.setTextColor(ContextCompat.getColor(this, R.color.black));
        tv_information.setBackgroundResource(R.color.main_background);
        tv_information.setTextColor(ContextCompat.getColor(this, R.color.black));
    }

    /**
     * 新闻列表
     */
    private class NewsListXCallBack implements HttpHelper.XCallBack {
        private List<JournalismInfo> list = null;

        @Override
        public void onResponse(String result) {
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, JournalismInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }
            if (null != list) {
                InformationAdapter informationAdapter = new InformationAdapter(list, MessageActivity.this);
                rv_message.setAdapter(informationAdapter);
                informationAdapter.setOnItemClickListener(new InformationAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        Intent intent = new Intent();
                        intent.putExtra("is_read", "is_read");
                        intent.putExtra("id", Integer.valueOf(list.get(position).getId()));
                        openActivity(intent, NewsDetailActivity.class);
                    }
                });
            }
        }
    }
}
