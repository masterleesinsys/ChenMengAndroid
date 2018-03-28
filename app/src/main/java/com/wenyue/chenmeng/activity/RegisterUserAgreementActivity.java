package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.view.KeyEvent;

import com.github.ybq.android.spinkit.SpinKitView;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.util.HttpHelper;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * 注册用户协议
 */
@ContentView(R.layout.activity_register_user_agreement)
public class RegisterUserAgreementActivity extends BaseActivity {
    @ViewInject(R.id.wv_user_agreement)
    private WebView wv_user_agreement;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("用户协议");

        GetContent();
    }

    private void GetContent() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("title", "TCP");
        HttpHelper.getInstance().post(MyApplication.getTokenURL(Constants.NEWS_DETAIL_BY_TITLE), params, spin_kit, new onUserAgreementCallback());

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showWebView(String data) {
        // 设置WevView要显示的网页
        String content = data.replace("<img", "<img height=\"250px\"; width=\"100%\"");
        wv_user_agreement.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
        wv_user_agreement.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv_user_agreement.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        wv_user_agreement.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        wv_user_agreement.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv_user_agreement.canGoBack()) {
            wv_user_agreement.goBack();//返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 用户协议单页面
     */
    private class onUserAgreementCallback implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            String content = null;
            String data = null;
            try {
                content = getHttpResult(result, String.class);
                data = new JSONObject(content).getString("content");
            } catch (Exception e) {
                return;
            }
            if (data != null) {
                showWebView(data);
            }
        }
    }
}
