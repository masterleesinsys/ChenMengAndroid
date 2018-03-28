package com.wenyue.chenmeng.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.angmarch.views.NiceSpinner;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收款账户设置
 */
@ContentView(R.layout.activity_shroff_account_number)
public class ShroffAccountNumberActivity extends BaseActivity {
    @ViewInject(R.id.ns_account)
    private NiceSpinner ns_account;
    @ViewInject(R.id.et_account)
    private EditText et_account;
    @ViewInject(R.id.et_nickname)
    private EditText et_nickname;
    @ViewInject(R.id.tv_confirm)
    private TextView tv_confirm;
    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String[] str1 = {"账户类型", "支付宝", "微信"};  //type=0 支付宝 ，1  微信

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("收款账户设置");

        List<String> dataset1 = new ArrayList<>();
        dataset1.addAll(Arrays.asList(str1));
        ns_account.attachDataSource(dataset1);
    }

    @Event(value = {R.id.tv_confirm})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        String type = ns_account.getText().toString().trim();
        String account = et_account.getText().toString().trim();
        String name = et_nickname.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_confirm:   //确定
                if ("".equals(type) || "账户类型".equals(type)) {
                    showToastText("请选择账户类型");
                    return;
                } else if ("".equals(account)) {
                    showToastText("请填写账户");
                    return;
                } else if ("".equals(name)) {
                    showToastText("请填写账户姓名/昵称");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("type", "支付宝".equals(type) ? "0" : "1");
                map.put("account_number", account);
                map.put("account_name", name);
                map.put("account_bank", type);
                HttpHelper.getInstance().post(MyApplication.getTokenURL(Constants.FINANCE_DRAWING_ACCOUNT_SAVE), map, spin_kit, new FinanceDrawingAccountSaveXCallBack());
                break;
        }
    }

    /**
     * 提现账户保存
     */
    private class FinanceDrawingAccountSaveXCallBack implements HttpHelper.XCallBack {
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
                new AlertView("提现账户保存", "提现账户保存成功", null, new String[]{"知道了"}, null, ShroffAccountNumberActivity.this, AlertView.Style.Alert, null).setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(Object o) {
                        finish();
                    }
                }).show();
            }
        }
    }
}
