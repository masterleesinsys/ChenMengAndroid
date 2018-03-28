package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.View;
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

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;
import java.util.HashMap;

/**
 * 忘记密码
 */
@ContentView(R.layout.activity_forget_password)
public class ForgetPasswordActivity extends BaseActivity {
    @ViewInject(R.id.et_forgetpassword_phone)
    private EditText et_forgetpassword_phone;

    @ViewInject(R.id.et_forgetpassword_verification)
    private EditText et_forgetpassword_verification;

    @ViewInject(R.id.tv_forgetpassword_sendverification)
    private TextView tv_forgetpassword_sendverification;

    @ViewInject(R.id.tv_forgetpassword_registernext)
    private TextView tv_forgetpassword_registernext;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private TimeCount time; // 倒计时

    private String username = "";
    private String verification = "";

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("验证手机");

        time = new TimeCount(60000, 1000);// 设置倒计时间，60秒
    }

    @Event(value = {R.id.tv_forgetpassword_registernext, R.id.tv_forgetpassword_sendverification})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        username = et_forgetpassword_phone.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_forgetpassword_sendverification:
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

            case R.id.tv_forgetpassword_registernext:
                if (username.equals("")) {
                    showToastText("帐号不能为空!");
                    return;
                }
                if (!Common.isMobileNO(username)) {
                    showToastText("手机不对!");
                    return;
                }
                verification = et_forgetpassword_verification.getText().toString().trim();
                if (verification.equals("")) {
                    showToastText("验证码不能为空!");
                    return;
                }
                HashMap<String, String> params2 = new HashMap<>();
                params2.put("mobile", username);
                params2.put("vcode", verification);
                HttpHelper.getInstance().get(MyApplication.getURL(Constants.RESET_PASSWORD_CHECK), params2, spin_kit, new onCheckMoblieCodeCallback());
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
                Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("vcode", verification);
                startActivity(intent);
            }
        }
    }

    private class onGetMoblieCodeCallback implements HttpHelper.XCallBack {
        public void onResponse(String result) {
            String code;
            try {
                code = getHttpResult(result, String.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                tv_forgetpassword_sendverification.setText(" 请求验证码 ");
                tv_forgetpassword_sendverification.setBackgroundResource(R.drawable.verificationbackground);
                tv_forgetpassword_sendverification.setClickable(true);
                return;
            }
            if (code != null) {
                showToastText("短信已经发送成功，请从手机中查看短信");
            }
        }
    }

    /**
     * 倒计时控件
     */
    class TimeCount extends CountDownTimer {
        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            tv_forgetpassword_sendverification.setText(" 请求验证码 ");
            tv_forgetpassword_sendverification.setBackgroundResource(R.drawable.verificationbackground);
            tv_forgetpassword_sendverification.setTextColor(ContextCompat.getColor(ForgetPasswordActivity.this, R.color.textColor_title));
            tv_forgetpassword_sendverification.setClickable(true);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            tv_forgetpassword_sendverification.setClickable(false);
            tv_forgetpassword_sendverification.setTextColor(ContextCompat.getColor(ForgetPasswordActivity.this, R.color.white));
            tv_forgetpassword_sendverification.setBackgroundResource(R.color.gray);
            tv_forgetpassword_sendverification.setText("" + (millisUntilFinished / 1000) + "秒");
        }
    }
}
