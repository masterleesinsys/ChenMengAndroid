package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.ybq.android.spinkit.SpinKitView;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.GiveARewardAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 我的打赏
 */
@ContentView(R.layout.activity_give_areward)
public class GiveARewardActivity extends BaseActivity {
    @ViewInject(R.id.rv_Give_areward)
    private RecyclerView rv_order_dynamic;
    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;
    @ViewInject(R.id.not_info)
    private View not_info;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("我的关注");

        mInitRecyclerView(rv_order_dynamic, 2);
        GiveARewardAdapter giveARewardAdapter = new GiveARewardAdapter(GiveARewardActivity.this);
        rv_order_dynamic.setAdapter(giveARewardAdapter);
        giveARewardAdapter.setOnItemClickListener(new GiveARewardAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                openActivity(TeacherDetailActivity.class);
            }
        });
    }
}
