package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wenyue.chenmeng.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 广告详情 链接页
 */
@SuppressWarnings({"deprecation", "FieldCanBeLocal"})
@ContentView(R.layout.activity_ad_details)
public class AdDetailsActivity extends BaseActivity {
    @ViewInject(R.id.wv_addetails)
    private WebView wv_addetails;

    private String object = null, name = null;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        Intent intent = getIntent();
        object = intent.getStringExtra("object");
        name = intent.getStringExtra("name");

        setStyle(STYLE_BACK);
        setCaption("".equals(name) ? "详情" : name);

        //设置 缓存模式
        wv_addetails.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        wv_addetails.getSettings().setDomStorageEnabled(true);

        wv_addetails.loadUrl(object == null ? "http://www.baidu.com" : object);
        wv_addetails.getSettings().setJavaScriptEnabled(true);

        wv_addetails.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //  html加载完成之后，调用js的方法
                imgReset();
            }
        });
    }

    private void imgReset() {
        wv_addetails.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName('img'); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "var img = objs[i];   "
                + "    img.style.width = '100%';   "
                + "    img.style.height = 'auto';   "
                + "}" + "})()");
    }

    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv_addetails.canGoBack()) {
            wv_addetails.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        this.finish();
        return false;
    }
}
