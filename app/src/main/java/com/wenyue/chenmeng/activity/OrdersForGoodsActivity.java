package com.wenyue.chenmeng.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.OrderDynamicsAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 商品订单
 */
@ContentView(R.layout.activity_orders_for_goods)
public class OrdersForGoodsActivity extends BaseActivity {
    @ViewInject(R.id.rv_commodity)
    private RecyclerView rv_commodity;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("商品订单");

        rv_commodity.setNestedScrollingEnabled(false);
        mInitRecyclerView(rv_commodity);
        OrderDynamicsAdapter orderDynamicsAdapter = new OrderDynamicsAdapter(OrdersForGoodsActivity.this);
        rv_commodity.setAdapter(orderDynamicsAdapter);
        orderDynamicsAdapter.setOnItemClickListener(new OrderDynamicsAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }
        });
    }

    @Event(value = {R.id.rl_shippingAddress})
    private void onClcik(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.rl_shippingAddress:   //收货地址
                openActivity(ShippingAddressActivity.class);
                break;
        }
    }
}
