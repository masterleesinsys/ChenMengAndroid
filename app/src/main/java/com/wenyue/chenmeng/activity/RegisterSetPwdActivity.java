package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.UserInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * 设置密码
 */
@SuppressWarnings("ALL")
@ContentView(R.layout.activity_set_password)
public class RegisterSetPwdActivity extends BaseActivity {
    @ViewInject(R.id.et_setpassword)
    private EditText et_setpassword;
    @ViewInject(R.id.et_repeat_setpassword)
    private EditText et_repeat_setpassword;
    @ViewInject(R.id.tv_setpasswordnext)
    private TextView tv_setpasswordnext;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String username = "";
    private String verification = "";
    private String recommendAccount = "";

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("帐号注册:设置密码(2/3)");

        username = this.getIntent().getStringExtra("username");
        verification = this.getIntent().getStringExtra("vcode");
        recommendAccount = this.getIntent().getStringExtra("recommendAccount");
    }

    @Event(value = R.id.tv_setpasswordnext)
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_setpasswordnext:
                String password = et_setpassword.getText().toString().trim();
                String password1 = et_repeat_setpassword.getText().toString().trim();
                if (password.equals("") || password1.equals("")) {
                    showToastText("请设置密码!");
                    return;
                }
                if (!password.equals(password1)) {

                    showToastText("两次密码不一致!");
                    return;
                }
                if (password.length() < 6) {
                    showToastText("请输入不少于六位密码!");
                    return;
                }
                HashMap<String, Object> params2 = new HashMap<>();
                params2.put("username", username);
                params2.put("vcode", verification);
                params2.put("password", password);
                params2.put("weixin_id", MyApplication.USER_WEIXIN_OPEN_ID);
                params2.put("referee", recommendAccount);
                HttpHelper.getInstance().post(MyApplication.getURL(Constants.USER_REGISTER_MEMBER), params2, spin_kit, new onUserRegisterCallback());
                break;
        }
    }

    private class onUserRegisterCallback implements HttpHelper.XCallBack {
        public void onResponse(String result) {
            UserInfo user = null;
            try {
                user = getHttpResult(result, UserInfo.class);
                user.setId(user.getUser());
            } catch (Exception e) {
                LogUtils.e(e.toString());
            }
            if (user != null) {
                showToastText("注册成功");
                openActivity(MainActivity.class);
                finish();
                LogUtils.e("注册成功");
            } else {
                showToastText("帐号注册失败，请联系技术人员");
                LogUtils.e("帐号注册失败");
            }
        }
    }
}
