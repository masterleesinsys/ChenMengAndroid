package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.util.Common;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;
import com.wenyue.chenmeng.util.Placard;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;
import java.util.HashMap;

/**
 * 注册账号
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {
    @SuppressLint("StaticFieldLeak")
    public static RegisterActivity registerActivity = null;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.et_recommendAccount)
    private EditText et_recommendAccount;
    @ViewInject(R.id.et_verification)
    private EditText et_verification;
    @ViewInject(R.id.tv_sendverification)
    private TextView tv_sendverification;
    @ViewInject(R.id.tv_registernext)
    private TextView tv_registernext;
    private TimeCount time; // 倒计时
    @ViewInject(R.id.cb_user_agreement)
    private CheckBox cb_user_agreement;
    @ViewInject(R.id.tv_user_agreement)
    private TextView tv_user_agreement;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String username = "";
    private String verification = "";
    private String recommendAccount = "";

    //来源类型
    private int mType = -1;

    @Override
    protected void initView() {
        registerActivity = this;

        setStyle(STYLE_BACK);
        setCaption("注册帐号:验证手机(1/2)");
        mType = getIntent().getIntExtra("type", -1);
        if (mType == 29) {
            setCaption("绑定手机(1/2)");
        }

        time = new TimeCount(60000, 1000);// 设置倒计时间，60秒
        setCbChangeListener();
    }

    private void setCbChangeListener() {
        cb_user_agreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    tv_registernext.setBackgroundResource(R.drawable.login_next);
                    tv_registernext.setEnabled(true);
                } else {
                    tv_registernext.setBackgroundResource(R.color.gray);
                    tv_registernext.setEnabled(false);
                }
            }
        });
    }

    @Event(value = {R.id.tv_registernext, R.id.tv_sendverification, R.id.tv_user_agreement})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        username = et_phone.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_sendverification:
                if (username.equals("")) {
                    showToastText("帐号不能为空!");
                    return;
                }
                if (!Common.isMobileNO(username)) {
                    showToastText("手机不对!");
                    return;
                }

                if (MyApplication.SendMoblieCodePreLimitTime != null) {
                    Date prtime = MyApplication.SendMoblieCodePreLimitTime;
                    // now
                    Date now = new Date();
                    long mill = now.getTime() - prtime.getTime();
                    if (mill < 60000) {
                        // 在之后
                        long timenum = (60000 - mill) / 1000;
                        showToastText("请在" + timenum + "秒后再次操作");
                        return;
                    }
                }

                // 获取验证码程序
                if (!username.equals("") && username != null) {
                    time.start();
                }
                HashMap<String, String> params = new HashMap<>();
                params.put("mobile", username);
                HttpHelper.getInstance().get(MyApplication.getURL(Constants.GET_VODE), params, spin_kit, new onGetMoblieCodeCallback());
                break;
            case R.id.tv_registernext:
                if (username.equals("")) {
                    showToastText("帐号不能为空!");
                    return;
                }
                if (!Common.isMobileNO(username)) {
                    showToastText("手机不对!");
                    return;
                }
                verification = et_verification.getText().toString().trim();
                if (verification.equals("")) {
                    showToastText("验证码不能为空!");
                    return;
                }
                recommendAccount = et_recommendAccount.getText().toString().trim();
                HashMap<String, String> params2 = new HashMap<>();
                params2.put("mobile", username);
                params2.put("vcode", verification);
                params2.put("referee", recommendAccount);
                HttpHelper.getInstance().get(MyApplication.getURL(Constants.MOBLIE_VODE_CHECK), params2, spin_kit, new onCheckMoblieCodeCallback());
                break;
            case R.id.tv_user_agreement:    //用户协议
                if (cb_user_agreement.isChecked()) {
                    tv_registernext.setBackgroundResource(R.drawable.login_next);
                    tv_registernext.setEnabled(true);
                    openActivity(RegisterUserAgreementActivity.class);
                } else {
                    tv_registernext.setBackgroundResource(R.color.gray);
                    tv_registernext.setEnabled(false);
                }
                break;
        }
    }

    private class onCheckMoblieCodeCallback implements HttpHelper.XCallBack {
        public void onResponse(String result) {
            String reault;
            try {
                reault = getHttpResult(result, String.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                return;
            }
            if (reault != null) {
                Intent intent = new Intent(RegisterActivity.this, RegisterSetPwdActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("vcode", verification);
                intent.putExtra("recommendAccount", recommendAccount);
                startActivity(intent);
            }
        }
    }

    private class onGetMoblieCodeCallback implements HttpHelper.XCallBack {
        public void onResponse(String result) {
            String code = "";
            try {
                code = getHttpResult(result, String.class);
            } catch (Exception e) {
                Placard.showInfo(e.toString());
                LogUtils.e(e.toString());
                tv_sendverification.setText(" 请求验证码 ");
                tv_sendverification.setBackgroundResource(R.drawable.verificationbackground);
                tv_sendverification.setClickable(true);
                return;
            }
            if (code != null) {
                Placard.showInfo("短信已经发送成功，请从手机中查看短信");
                LogUtils.e("短信发送成功");
            }
        }
    }

    /* 倒计时控件 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            tv_sendverification.setText(" 请求验证码 ");
            tv_sendverification.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.textColor_title));
            tv_sendverification.setBackgroundResource(R.drawable.verificationbackground);
            tv_sendverification.setClickable(true);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            tv_sendverification.setClickable(false);
            tv_sendverification.setTextColor(ContextCompat.getColor(RegisterActivity.this, R.color.white));
            tv_sendverification.setBackgroundResource(R.color.gray);
            tv_sendverification.setText("" + millisUntilFinished / 1000 + "秒");
        }
    }
}
