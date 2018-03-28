package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.NewsDetailInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

import static com.wenyue.chenmeng.util.Common.NewIsReadAdd;


/**
 * 资讯详情
 */
@SuppressWarnings("ALL")
@ContentView(R.layout.activity_news_detail)
public class NewsDetailActivity extends BaseActivity {
    @ViewInject(R.id.tv_newsdeyail_title)
    private TextView tv_newsdeyail_title;   //资讯标题

    @ViewInject(R.id.tv_newsdetail_date)
    private TextView tv_newsdetail_date;    //发布时间

    @ViewInject(R.id.wv_newsdetail_content)
    private WebView wv_newsdetail_content; //资讯内容

    @ViewInject(R.id.sv_news_detail)
    private ScrollView sv_news_detail;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private Integer id = 0;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("详情");

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        if (!"is_read".equals(intent.getStringExtra("is_read"))) {
            //标记新闻为已读
            NewIsReadAdd(id + "", mContext);
        }

        OverScrollDecoratorHelper.setUpOverScroll(sv_news_detail);

        String resVlt = String.format(Constants.NEWS_DETAIL_BY_ID, id);
        HttpHelper.getInstance().get(MyApplication.getTokenURL(resVlt), null, spin_kit, new onNewsDetailByIdXCallBack());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showWebView(NewsDetailInfo newsDetailById) {
        tv_newsdeyail_title.setText(newsDetailById.getTitle());
        tv_newsdetail_date.setText(newsDetailById.getCreate_time());
        //设置 缓存模式
        wv_newsdetail_content.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        wv_newsdetail_content.getSettings().setDomStorageEnabled(true);

        wv_newsdetail_content.loadDataWithBaseURL(null, newsDetailById.getContent(), "text/html", "utf-8", null);
//        wv_newsdetail_content.loadUrl(newsDetailById.getContent());
        wv_newsdetail_content.getSettings().setJavaScriptEnabled(true);
        wv_newsdetail_content.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。

        wv_newsdetail_content.setWebViewClient(new WebViewClient() {
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
        wv_newsdetail_content.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName('img'); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "var img = objs[i];   "
                + "    img.style.width = '100%';   "
                + "    img.style.height = 'auto';   "
                + "}" + "})()");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv_newsdetail_content.canGoBack()) {
            wv_newsdetail_content.goBack();//返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class onNewsDetailByIdXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            NewsDetailInfo newsDetailById = null;
            try {
                newsDetailById = getHttpResult(result, NewsDetailInfo.class);
            } catch (Exception e) {
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }

            if (newsDetailById != null) {
                showWebView(newsDetailById);
            }
        }
    }
}
