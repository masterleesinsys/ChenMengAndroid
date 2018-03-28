package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 搜索
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends BaseActivity {
    @ViewInject(R.id.et_search)
    private EditText et_search;

    @Override
    protected void initView() {
    }

    @Event(value = {R.id.imgBack, R.id.tv_datermine})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.imgBack:  //返回
                finish();
                break;
            case R.id.tv_datermine: //确定
                String keyword = et_search.getText().toString().trim();
                if ("".equals(keyword)) {
                    showToastText("请输入有效的视频名称");
                    return;
                }

                intent = new Intent();
                intent.putExtra("keyword", keyword);
                openActivity(intent, SearchResultActivity.class);
                break;
        }
    }
}
