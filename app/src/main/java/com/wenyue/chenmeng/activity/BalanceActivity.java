package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.DrawingAccountInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 余额
 */
@ContentView(R.layout.activity_balance)
public class BalanceActivity extends BaseActivity {
    @ViewInject(R.id.tv_withdrawDeposit)
    private TextView tv_withdrawDeposit;
    @ViewInject(R.id.tv_topUp)
    private TextView tv_topUp;
    @ViewInject(R.id.tv_price)
    private TextView tv_price;  //金额
    @ViewInject(R.id.tv_withdrawDeposit_user)
    private TextView tv_withdrawDeposit_user;  //提现账户
    @ViewInject(R.id.tv_username)
    private TextView tv_username;  //提现用户
    @ViewInject(R.id.tv_name)
    private TextView tv_name;  //提现用户昵称

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;
    @ViewInject(R.id.not_info)
    private View not_info;

    @Override
    protected void initView() {
        setStyle(STYLE_JINGPIN);
        showBackButton(true);
        setCaption("余额");
        setTitleBarRightTvVisibility(View.VISIBLE);
        setTitleBarRightTvText("修改");

        setOnTitleBarRightTvListener(new onTitleBarRightTvListener() {
            @Override
            public void onTitleBarRightTvListener() {
                openNeedLoginActivity(ShroffAccountNumberActivity.class);
            }
        });

        tv_withdrawDeposit.setBackgroundDrawable(mDrawableStyle(R.color.white, 10));
        tv_topUp.setBackgroundDrawable(mDrawableStyle(R.color.white, 10));

        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.FINANCE_DRAWING_ACCOUNT), null, spin_kit, new onFinanceDrawingAccountXCallBack());
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.FINANCE_PROMOTING_LIST), null, spin_kit, new onFinancePromotingListXCallBack());
    }

    @Event(value = {R.id.tv_topUp, R.id.tv_withdrawDeposit})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.tv_topUp: //充值
                openNeedLoginActivity(RechargeActivity.class);
                break;
            case R.id.tv_withdrawDeposit:   //提现
                openNeedLoginActivity(WithdrawalsActivity.class);
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
                tv_username.setText(drawingAccountInfo.getAccount_number());
                tv_name.setText(drawingAccountInfo.getAccount_name());
                tv_withdrawDeposit_user.setText(drawingAccountInfo.getType_text() + "账户提现");
            }
        }
    }

    /**
     * 余额详情
     */
    private class onFinancePromotingListXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            not_info.setVisibility(View.VISIBLE);
        }
    }
}
