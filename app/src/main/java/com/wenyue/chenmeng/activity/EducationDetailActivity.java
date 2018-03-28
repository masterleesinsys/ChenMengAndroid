package com.wenyue.chenmeng.activity;


import com.wenyue.chenmeng.R;

import org.xutils.view.annotation.ContentView;

/**
 * 教育详情页
 */
@ContentView(R.layout.activity_education_detail)
public class EducationDetailActivity extends BaseActivity {

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption(getIntent().getStringExtra("education"));
    }
}
