package com.wenyue.chenmeng.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

/**
 * 重置密码
 */
@ContentView(R.layout.activity_reset_password)
public class ResetPasswordActivity extends BaseActivity {
    @ViewInject(R.id.et_resetpw_setpassword)
    private EditText et_resetpw_setpassword;     //重置密码

    @ViewInject(R.id.et_resetpw_againpassword)
    private EditText et_resetpw_againpassword;   //再次输入

    @ViewInject(R.id.tv_resetpw_determine)
    private TextView tv_resetpw_determine;   //确认

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String username = "";
    private String verification = "";
    private String password2;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("重置密码");

        username = this.getIntent().getStringExtra("username");
        verification = this.getIntent().getStringExtra("vcode");
    }

    @Event(value = R.id.tv_resetpw_determine)
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.tv_resetpw_determine:
                String password1 = et_resetpw_setpassword.getText().toString().trim();
                password2 = et_resetpw_againpassword.getText().toString().trim();

                if (password1.equals("") || password2.equals("")) {
                    showToastText("请设置密码!");
                    return;
                }

                if (!password1.equals(password2)) {
                    showToastText("两次密码不一致!");
                    return;
                }

                HttpHelper.getInstance().post(MyApplication.getURL(Constants.USER_PASSWORD_FORGET), getHashMap(), spin_kit, new HttpHelper.XCallBack() {
                    @Override
                    public void onResponse(String result) {
                        String reault = "";
                        try {
                            reault = getHttpResult(result, String.class);
                        } catch (Exception e) {
                            LogUtils.e(e.toString());
                        }
                        if (reault != null) {
                            showToastText("重置密码成功");
                            openActivity(LoginActivity.class);
                        }
                    }
                });
                break;
        }
    }

    private Map<String, Object> getHashMap() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("vcode", verification);
        params.put("username", username);
        params.put("new_password", password2);
        return params;
    }
}
