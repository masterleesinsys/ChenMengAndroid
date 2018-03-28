package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.ybq.android.spinkit.SpinKitView;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.OrderDynamicAdapter;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 订单动态
 */
@ContentView(R.layout.activity_order_dynamic)
public class OrderDynamicActivity extends BaseActivity {
    @ViewInject(R.id.rv_order_dynamic)
    private RecyclerView rv_order_dynamic;
    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;
    @ViewInject(R.id.not_info)
    private View not_info;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("订单动态");

        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.ORDER_LIST), null, spin_kit, new OrderListXCalllBack());

        mInitRecyclerView(rv_order_dynamic, 2);
        OrderDynamicAdapter orderDynamicAdapter = new OrderDynamicAdapter(OrderDynamicActivity.this);
        rv_order_dynamic.setAdapter(orderDynamicAdapter);
        orderDynamicAdapter.setOnItemClickListener(new OrderDynamicAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent();
                intent.putExtra("title", "订单号：896314751");
                openNeedLoginActivity(intent, OrderDetailActivity.class);
            }
        });
    }

    /**
     * 我的订单
     */
    private class OrderListXCalllBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            LogUtils.e(result);
        }
    }
}
