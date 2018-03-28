package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.TrolleyAdapter;
import com.wenyue.chenmeng.entity.TrolleyInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 购物车
 */
@ContentView(R.layout.activity_shopping_trolley)
public class ShoppingTrolleyActivity extends BaseActivity {
    @ViewInject(R.id.iv_checkAll)
    private ImageView iv_checkAll;  //全选
    @ViewInject(R.id.tv_closeAnAccount)
    private TextView tv_closeAnAccount; //结算
    @ViewInject(R.id.tv_price)
    private TextView tv_price; //合计
    @ViewInject(R.id.rv_trolley)
    private RecyclerView rv_trolley;
    @ViewInject(R.id.not_info)
    private View not_info;
    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private List<TrolleyInfo> list = null;
    private TrolleyAdapter trolleyAdapter;
    private Double total_price = 0.0;

    private Boolean is_checkAll = false;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("购物车");

        iv_checkAll.setBackgroundResource(R.drawable.ico_choose);

        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.STORE_CART), null, spin_kit, new StoreCartXCallBack());

        mInitRecyclerView(rv_trolley, 2);
    }

    @Event(value = {R.id.ll_bottom, R.id.tv_closeAnAccount})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.ll_bottom:    //全选
                is_checkAll = !is_checkAll;

                if (is_checkAll) {
                    iv_checkAll.setBackgroundResource(R.drawable.ico_choose_def);
                } else {
                    if (null != trolleyAdapter) {
                        trolleyAdapter.updateData(list);
                    }
                    iv_checkAll.setBackgroundResource(R.drawable.ico_choose);
                }
                break;
            case R.id.tv_closeAnAccount:    //结算
                openActivity(OrdersForGoodsActivity.class);
                break;
        }
    }

    /**
     * 购物车内容
     */
    private class StoreCartXCallBack implements HttpHelper.XCallBack {
        @SuppressLint("SetTextI18n")
        @Override
        public void onResponse(String result) {
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, TrolleyInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }
            if (null != list && list.size() > 0) {
                not_info.setVisibility(View.GONE);
                for (TrolleyInfo trolleyInfo : list) {
                    total_price += trolleyInfo.getPrice();
                }
                tv_price.setText("￥" + total_price);
                tv_closeAnAccount.setText("结算(" + list.size() + ")");
            } else {
                not_info.setVisibility(View.VISIBLE);
            }
            trolleyAdapter = new TrolleyAdapter(list, ShoppingTrolleyActivity.this);
            rv_trolley.setAdapter(trolleyAdapter);
            trolleyAdapter.setOnItemClickListener(new TrolleyAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(int position) {

                }
            });
        }
    }
}
