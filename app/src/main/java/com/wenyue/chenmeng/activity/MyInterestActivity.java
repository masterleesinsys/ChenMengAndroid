package com.wenyue.chenmeng.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.ybq.android.spinkit.SpinKitView;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.InterestAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 我的关注
 */
@ContentView(R.layout.activity_my_interest)
public class MyInterestActivity extends BaseActivity {
    @ViewInject(R.id.rv_order_dynamic)
    private RecyclerView rv_order_dynamic;
    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;
    @ViewInject(R.id.not_info)
    private View not_info;


    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("我的关注");

        mInitRecyclerView(rv_order_dynamic, 2);
        InterestAdapter interestAdapter = new InterestAdapter(MyInterestActivity.this);
        rv_order_dynamic.setAdapter(interestAdapter);
        interestAdapter.setOnItemClickListener(new InterestAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                openActivity(TeacherDetailActivity.class);
            }
        });
    }
}
