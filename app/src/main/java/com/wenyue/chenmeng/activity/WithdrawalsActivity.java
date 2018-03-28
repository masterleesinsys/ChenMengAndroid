package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.github.ybq.android.spinkit.SpinKitView;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.DrawingAccountInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 提现
 */
@ContentView(R.layout.activity_withdrawals)
public class WithdrawalsActivity extends BaseActivity {
    @ViewInject(R.id.et_withdrawal)
    private EditText et_withdrawal;     //用户输入的提现金额
    @ViewInject(R.id.tv_prompt)
    private TextView tv_prompt;   //转到XXX账户上
    @ViewInject(R.id.tv_determine)
    private TextView tv_determine;    //确定

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @Override
    protected void initView() {
        setStyle(STYLE_JINGPIN);
        setCaption("提现");
        setTitleBarRightTvVisibility(View.VISIBLE);
        setTitleBarRightTvText("记录").setOnTitleBarRightTvListener(new onTitleBarRightTvListener() {
            @Override
            public void onTitleBarRightTvListener() {
                openNeedLoginActivity(WithdrawalsRecordActivity.class);
            }
        });
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.FINANCE_DRAWING_ACCOUNT), null, spin_kit, new onFinanceDrawingAccountXCallBack());
    }

    @Event(value = {R.id.tv_determine})
    private void onClick(View view) {
        String withdrawal = et_withdrawal.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_determine:
                Map<String, Object> map = null;
                try {
                    if (et_withdrawal.getText().toString().trim().equals("0")) {
                        et_withdrawal.setText("");
                    } else if (withdrawal.equals("")) {
                        showToastText("请输入提现金额!");
                        return;
                    } else if (Double.valueOf(withdrawal) < 0) {
                        showToastText("请输入正确的提现金额！");
                        return;
                    }
                    map = new HashMap<>();
                    map.put("total_fee", Double.valueOf(withdrawal));
                } catch (Exception e) {
                    LogUtils.e(e.toString());
                    showToastText("您的输入有误，请尝试重新输入！");
                }
                HttpHelper.getInstance().post(MyApplication.getTokenURL(Constants.FINANCE_DRAWING_ADD), map, spin_kit, true, new FinanceDrawingAddXCallBack());
                break;
        }
    }

    /**
     * 提现账户
     */
    private class onFinanceDrawingAccountXCallBack implements HttpHelper.XCallBack {
        private DrawingAccountInfo drawingAccountInfo = null;

        @SuppressLint("SetTextI18n")
        @Override
        public void onResponse(String result) {
            try {
                drawingAccountInfo = getHttpResult(result, DrawingAccountInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }
            if (null != drawingAccountInfo) {
                tv_prompt.setText("申请成功将转账到" + drawingAccountInfo.getType_text() + drawingAccountInfo.getAccount_number() + "账户上");
            }
        }
    }

    /**
     * 提现
     */
    private class FinanceDrawingAddXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            String data = null;
            try {
                data = getHttpResult(result, String.class);
            } catch (Exception e) {
                showToastText(e.toString());
                LogUtils.e(e.toString());
                et_withdrawal.setText("");
            }
            if ("{}".equals(data)) {
                new AlertView("提现", "已提现成功,可以在记录中查看", null, new String[]{"知道了"}, null, WithdrawalsActivity.this, AlertView.Style.Alert, null).setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(Object o) {
                        et_withdrawal.setText("");
                    }
                }).show();
            }
        }
    }
}
