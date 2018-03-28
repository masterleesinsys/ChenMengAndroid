package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.UserInfo;
import com.wenyue.chenmeng.util.Common;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;
import com.wenyue.chenmeng.util.Placard;
import com.wenyue.chenmeng.util.SharedPreferencesUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

import static com.wenyue.chenmeng.MyApplication.getURL;


/**
 * 登录
 */
@SuppressWarnings("ALL")
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.actv_username)
    private AutoCompleteTextView mUsernameView;
    @ViewInject(R.id.et_password)
    private EditText mPasswordView;
    @ViewInject(R.id.tv_forget)
    private TextView txt_forget;
    @ViewInject(R.id.iv_login)
    private ImageView iv_login;
    @ViewInject(R.id.tv_register)
    private TextView tv_register;   //注册账号
    @ViewInject(R.id.ll_wxlogin)
    private LinearLayout ll_wxlogin;    //微信登录
    @ViewInject(R.id.ll_qq)
    private LinearLayout ll_qq;     //QQ登录
    @ViewInject(R.id.tv_mobilePhoneDynamicCodeLogin)
    private TextView tv_mobilePhoneDynamicCodeLogin;    //手机动态码登录

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("登录");
    }

    //微信登录
    private void WeiXinLogin(String weixin_id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("weixin_id", weixin_id);
        HttpHelper.getInstance().post(getURL(Constants.AUTH_WEIXIN), params, new onWeiXinLoginCallback());
    }

    @Event(value = {R.id.tv_login, R.id.tv_register, R.id.tv_forget, R.id.ll_wxlogin, R.id.ll_qq, R.id.tv_mobilePhoneDynamicCodeLogin, R.id.iv_login, R.id.actv_username, R.id.et_password})
    private void onClickLogin(View v) {
        ElasticAction.doAction(v, 400, 0.85f, 0.85f);
        switch (v.getId()) {
            case R.id.et_password:
                break;
            case R.id.actv_username:
                break;
            case R.id.iv_login:
                break;
            case R.id.tv_login: //登录
                attemptLogin();
                break;
            case R.id.tv_register:  //注册
                MyApplication.USER_WEIXIN_OPEN_ID = "";
                openActivity(RegisterActivity.class);
                break;
            case R.id.tv_forget:    //忘记密码
                openActivity(ForgetPasswordActivity.class);
                break;
            case R.id.ll_wxlogin:   //微信登录
                MyApplication.USER_WEIXIN_OPEN_ID = "";
//                platform = SHARE_MEDIA.WEIXIN;
//                mShareAPI.doOauthVerify(LoginActivity.this, platform, authListener);
                break;
            case R.id.ll_qq:    //QQ登录
                Placard.ToastMessage("测试自定义Toast 测试自定义Toast 测试自定义Toast 测试自定义Toast 测试自定义Toast 测试自定义Toast 测试自定义Toast测试自定义Toast 测试自定义Toast  测试自定义Toast 测试自定义Toast");
                break;
            case R.id.tv_mobilePhoneDynamicCodeLogin:   //动态码登录
                break;
        }
    }

    @Event(value = R.id.et_password, type = View.OnKeyListener.class)
    private boolean onPasswordEditorAction(View v, int id, KeyEvent keyEvent) {
        ElasticAction.doAction(v, 400, 0.85f, 0.85f);
        if (id == R.id.tv_login || id == EditorInfo.IME_NULL) {
            attemptLogin();
            return true;
        }
        return false;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    /**
     * 开始登录
     */
    private void attemptLogin() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (!Common.isMobileNO(username)) {
            mUsernameView.setError(getString(R.string.error_field_moblie_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            HashMap<String, Object> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
            HttpHelper.getInstance().post(getURL(Constants.USER_AUTH), params, new onLoginCallback());
        }
    }

    /**
     * 微信登录
     */
    private class onWeiXinLoginCallback implements HttpHelper.XCallBack {
        public void onResponse(String result) {
            UserInfo user = null;
            try {
                user = getHttpResult(result, UserInfo.class);
                user.setId(user.getUser());
            } catch (Exception e) {
                LogUtils.e(e.toString());
                return;
            }

            if (user != null) {
                if ("".equals(user.getName()) || user.getName() == null) {
                    Intent intent = new Intent();
                    intent.putExtra("user_id", user.getId());
                    openActivity(intent, RegisterPerfectdataActivity.class);
                } else {
                    //缓存登录数据，帐号、token等值
                    MyApplication.syncDetail(user);

                    finish();
                }
                finish();
            } else {
                showToastText("帐号登录失败，请联系技术人员");
                finish();
            }
        }
    }

    /**
     * 正常登录
     */
    @SuppressWarnings("ConstantConditions")
    private class onLoginCallback implements HttpHelper.XCallBack {
        public void onResponse(String result) {
            UserInfo user = null;
            try {
                user = getHttpResult(result, UserInfo.class);
                user.setId(user.getUser());
            } catch (Exception e) {
                LogUtils.e(e.toString());
                return;
            }

            if (user != null) {
                SharedPreferencesUtils.save(user.getType(), SharedPreferencesUtils.CONFIG_USER_TYPE);

                //缓存登录数据，帐号、token等值
                MyApplication.syncDetail(user);

                if ("".equals(user.getName()) || user.getName() == null) {
                    Intent intent = new Intent();
                    intent.putExtra("user_id", user.getId());
                    openActivity(intent, RegisterPerfectdataActivity.class);
                } else {
                    finish();
                }
            } else {
                showToastText("帐号登录失败，请联系技术人员");
                finish();
            }
        }
    }
}