package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.view.View;

import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * 晨师教育
 */
@ContentView(R.layout.activity_education)
public class EducationActivity extends BaseActivity {

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("晨师教育");
    }

    @Event(value = {R.id.ll_intro, R.id.ll_publicBenefit, R.id.ll_mediaAttention, R.id.ll_liveIsIntroduced, R.id.ll_agentToJoinIn, R.id.ll_contactUs, R.id.ll_talentedPeople, R.id.ll_legalNotice, R.id.ll_advertisingCooperation})
    private void onClcik(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_intro: //晨师简介
                intent = new Intent();
                intent.putExtra("education", "晨师简介");
                openActivity(intent, EducationDetailActivity.class);
                break;
            case R.id.ll_publicBenefit: //晨师公益
                intent = new Intent();
                intent.putExtra("education", "晨师公益");
                openActivity(intent, EducationDetailActivity.class);
                break;
            case R.id.ll_mediaAttention: //媒体关注
                intent = new Intent();
                intent.putExtra("education", "媒体关注");
                openActivity(intent, EducationDetailActivity.class);
                break;
            case R.id.ll_liveIsIntroduced: //直播介绍
                intent = new Intent();
                intent.putExtra("education", "直播介绍");
                openActivity(intent, EducationDetailActivity.class);
                break;
            case R.id.ll_agentToJoinIn: //代理加盟
                intent = new Intent();
                intent.putExtra("education", "代理加盟");
                openActivity(intent, EducationDetailActivity.class);
                break;
            case R.id.ll_contactUs: //联系我们
                intent = new Intent();
                intent.putExtra("education", "联系我们");
                openActivity(intent, EducationDetailActivity.class);
                break;
            case R.id.ll_talentedPeople: //诚聘英才
                intent = new Intent();
                intent.putExtra("education", "诚聘英才");
                openActivity(intent, EducationDetailActivity.class);
                break;
            case R.id.ll_legalNotice: //法律声明
                intent = new Intent();
                intent.putExtra("education", "法律声明");
                openActivity(intent, EducationDetailActivity.class);
                break;
            case R.id.ll_advertisingCooperation: //广告合作
                intent = new Intent();
                intent.putExtra("education", "广告合作");
                openActivity(intent, EducationDetailActivity.class);
                break;
        }
    }
}
