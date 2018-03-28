package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.github.ybq.android.spinkit.SpinKitView;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.ShippingAddressAdapter;
import com.wenyue.chenmeng.entity.ShippingAddressInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收货地址管理
 */
@ContentView(R.layout.activity_shipping_address)
public class ShippingAddressActivity extends BaseActivity {
    @ViewInject(R.id.rv_address)
    private RecyclerView rv_address;
    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @ViewInject(R.id.not_info)
    private View not_info;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK_QUERY);
        setCaption("收货地址管理");
        setTitleBarImageResource(R.drawable.ico_screening);
        setOnTitleBarRightImgListener(new onTitleBarRightImgListener() {
            @Override
            public void onTitleBarRightImgListener() {
                Intent intent = new Intent();
                intent.putExtra("type", "add");
                openActivity(intent, ShippingEditAddressActivity.class);
            }
        });

        mInitRecyclerView(rv_address, 2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.MEMBER_ADDRESS_LIST), null, spin_kit, new MemberAddressListXCallBack());
    }

    /**
     * 收货地址列表
     */
    private class MemberAddressListXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            List<ShippingAddressInfo> list = null;
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, ShippingAddressInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }

            if (null != list && list.size() > 0) {
                not_info.setVisibility(View.GONE);
                ShippingAddressAdapter shippingAddressAdapter = new ShippingAddressAdapter(list, ShippingAddressActivity.this);
                rv_address.setAdapter(shippingAddressAdapter);
                shippingAddressAdapter.setOnItemCompileClickListener(new ShippingAddressAdapter.OnItemCompileClickListener() {
                    @Override
                    public void onItemClickListener(int position, String id, String name, String mobile, String address) {
                        Intent intent = new Intent();
                        intent.putExtra("type", "edit");
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("mobile", mobile);
                        intent.putExtra("address", address);
                        openActivity(intent, ShippingEditAddressActivity.class);
                    }
                });
                shippingAddressAdapter.setOnItemDeleteClickListener(new ShippingAddressAdapter.OnItemDeleteClickListener() {
                    @Override
                    public void onItemClickListener(int position, String id) {
                        Map<String, String> map2 = new HashMap<>();
                        map2.put("id", id);
                        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.MEMBER_ADDRESS_DEL), map2, spin_kit, new MemberAddressDelXCallBack());
                    }
                });
            } else {
                not_info.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 删除收货地址
     */
    private class MemberAddressDelXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            int success = 0;
            try {
                success = getHttpResult(result);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }
            if (1 == success) {
                new AlertView("删除收货地址", "删除收货地址成功", null, new String[]{"知道了"}, null, ShippingAddressActivity.this, AlertView.Style.Alert, null).setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(Object o) {
                        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.MEMBER_ADDRESS_LIST), null, spin_kit, new MemberAddressListXCallBack());
                    }
                }).show();
            }
        }
    }
}
