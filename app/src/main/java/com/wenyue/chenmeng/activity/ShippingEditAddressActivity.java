package com.wenyue.chenmeng.activity;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.util.Common;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加&编辑 收货地址
 */
@ContentView(R.layout.activity_shipping_edit_address)
public class ShippingEditAddressActivity extends BaseActivity {
    @ViewInject(R.id.actv_phone)
    private AutoCompleteTextView actv_phone;
    @ViewInject(R.id.et_name)
    private EditText et_name;
    @ViewInject(R.id.et_site)
    private EditText et_site;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String type = "";
    private String id = "";
    private String name = "";
    private String mobile = "";
    private String address = "";

    boolean cancel = false;
    View focusView = null;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        if (getIntent().hasExtra("type")) {
            type = getIntent().getStringExtra("type");
            switch (type) {
                case "add":
                    setCaption("添加收货地址");
                    break;
                case "edit":
                    if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("mobile") && getIntent().hasExtra("address")) {
                        id = getIntent().getStringExtra("id");
                        name = getIntent().getStringExtra("name");
                        mobile = getIntent().getStringExtra("mobile");
                        address = getIntent().getStringExtra("address");

                        et_name.setText(name);
                        actv_phone.setText(mobile);
                        et_site.setText(address);
                    }
                    setCaption("编辑收货地址");
                    break;
            }
        }
    }

    @Event(value = {R.id.tv_confirm})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        String name = et_name.getText().toString().trim();
        String mobile = actv_phone.getText().toString().trim();
        String address = et_site.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_confirm:   //确定
                if ("".equals(name)) {
                    showToastText("请填写收货人姓名");
                    return;
                } else if ("".equals(mobile)) {
                    showToastText("请填写收货人联系方式");
                    return;
                } else if ("".equals(address)) {
                    showToastText("请填写收货地址");
                    return;
                }
                isMoblie(mobile);
                if (cancel) {
                    focusView.requestFocus();
                    return;
                }
                switch (type) {
                    case "add":
                        Map<String, Object> map = new HashMap<>();
                        map.put("name", name);
                        map.put("mobile", mobile);
                        map.put("address", address);
                        HttpHelper.getInstance().post(MyApplication.getTokenURL(Constants.MEMBER_ADDRESS_SAVE), map, spin_kit, new MemberAddressSaveXCallBack());
                        break;
                    case "edit":
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("name", name);
                        map2.put("mobile", mobile);
                        map2.put("address", address);
                        HttpHelper.getInstance().post(MyApplication.getTokenURL(Constants.MEMBER_ADDRESS_SAVE) + "&id=" + id, map2, spin_kit, new MemberAddressSaveXCallBack());
                        break;
                }
                break;
        }
    }

    /**
     * 是否为手机号
     *
     * @param mobile
     */
    private void isMoblie(String mobile) {
        actv_phone.setError(null);

        if (!Common.isMobileNO(mobile)) {
            actv_phone.setError(getString(R.string.error_field_moblie_required));
            focusView = actv_phone;
            cancel = true;
        }
    }

    /**
     * 收货地址修改&添加
     */
    private class MemberAddressSaveXCallBack implements HttpHelper.XCallBack {
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
                switch (type) {
                    case "add":
                        new AlertView("添加收货地址", "添加收货地址成功", null, new String[]{"知道了"}, null, ShippingEditAddressActivity.this, AlertView.Style.Alert, null).setOnDismissListener(new OnDismissListener() {
                            @Override
                            public void onDismiss(Object o) {
                                finish();
                            }
                        }).show();
                        break;
                    case "edit":
                        new AlertView("编辑收货地址", "编辑收货地址成功", null, new String[]{"知道了"}, null, ShippingEditAddressActivity.this, AlertView.Style.Alert, null).setOnDismissListener(new OnDismissListener() {
                            @Override
                            public void onDismiss(Object o) {
                                finish();
                            }
                        }).show();
                        break;
                }
            }
        }
    }
}
