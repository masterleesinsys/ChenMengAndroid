package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.TeacherDetailInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * 教师详情
 */
@SuppressWarnings("ALL")
@ContentView(R.layout.activity_teacher_detail)
public class TeacherDetailActivity extends BaseActivity {
    @ViewInject(R.id.tv_teacher_introduce)
    private TextView tv_teacher_introduce;  //介绍
    @ViewInject(R.id.rv_videoAndEvaluate)
    private RecyclerView rv_videoAndEvaluate;   //视频和评价
    @ViewInject(R.id.sv_teacher_detail)
    private ScrollView sv_teacher_detail;
    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @ViewInject(R.id.tv_introduce)
    private TextView tv_introduce;  //介绍
    @ViewInject(R.id.tv_video)
    private TextView tv_video;      //视频
    @ViewInject(R.id.tv_evaluate)
    private TextView tv_evaluate;   //评价
    @ViewInject(R.id.tv_status)
    private TextView tv_status;   //预约
    @ViewInject(R.id.tv_attention)
    private TextView tv_attention;   //关注

    @ViewInject(R.id.iv_headImg)
    private ImageView iv_headImg;   //头像
    @ViewInject(R.id.iv_sex)
    private ImageView iv_sex;   //性别
    @ViewInject(R.id.tv_name)
    private TextView tv_name;   //昵称

    private String teacher_id = "";
    private Boolean collected = false;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("教师详情");

        tv_status.setBackgroundDrawable(mDrawableStyle(R.color.full_transparency, 10, 1, R.color.mine_expiration_time));
        tv_attention.setBackgroundDrawable(mDrawableStyle(R.color.white, 10));

        OverScrollDecoratorHelper.setUpOverScroll(sv_teacher_detail);
        tv_introduce.setTextColor(ContextCompat.getColor(TeacherDetailActivity.this, R.color.main_update_color));

        if (getIntent().hasExtra("teacher_id")) {
            teacher_id = getIntent().getStringExtra("teacher_id");
        }

        String resval = String.format(Constants.TEACHER_DETAIL, teacher_id);
        HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), null, spin_kit, new TeacherDetailXCallBack());
    }

    @Event(value = {R.id.tv_introduce, R.id.tv_video, R.id.tv_evaluate, R.id.tv_status, R.id.tv_attention, R.id.tv_interactiveAnsweringQuestions})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_introduce:  //介绍
                clearColor();
                tv_introduce.setTextColor(ContextCompat.getColor(TeacherDetailActivity.this, R.color.main_update_color));
                break;
            case R.id.tv_video: //视频
                clearColor();
                tv_video.setTextColor(ContextCompat.getColor(TeacherDetailActivity.this, R.color.main_update_color));
                break;
            case R.id.tv_evaluate:  //评价
                clearColor();
                tv_evaluate.setTextColor(ContextCompat.getColor(TeacherDetailActivity.this, R.color.main_update_color));
                break;
            case R.id.tv_status:    //预约
                break;
            case R.id.tv_attention: //关注
                if ("".equals(teacher_id)) {
                    showToastText("未查询到该教师信息");
                    return;
                }
                if (collected) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", teacher_id);
                    HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.MEMBER_COLLECTION_DEL), map, spin_kit, new HttpHelper.XCallBack() {
                        @Override
                        public void onResponse(String result) {
                            int success = 0;
                            try {
                                success = getHttpResult(result);
                            } catch (Exception e) {
                                e.printStackTrace();
                                showToastText(e.toString());
                                LogUtils.e(e.toString());
                            }
                            if (1 == success) {
                                new AlertView("取消关注", "取消关注成功", null, new String[]{"知道了"}, null, TeacherDetailActivity.this, AlertView.Style.Alert, null).setOnDismissListener(new OnDismissListener() {
                                    @Override
                                    public void onDismiss(Object o) {
                                        String resval = String.format(Constants.TEACHER_DETAIL, teacher_id);
                                        HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), null, spin_kit, new TeacherDetailXCallBack());
                                    }
                                }).show();
                            }
                        }
                    });
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("type", "1");
                    map.put("object", teacher_id);
                    HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.MEMBER_COLLECTION_ADD), map, spin_kit, new HttpHelper.XCallBack() {
                        @Override
                        public void onResponse(String result) {
                            int success = 0;
                            try {
                                success = getHttpResult(result);
                            } catch (Exception e) {
                                e.printStackTrace();
                                showToastText(e.toString());
                                LogUtils.e(e.toString());
                            }
                            if (1 == success) {
                                new AlertView("添加关注", "添加关注成功", null, new String[]{"知道了"}, null, TeacherDetailActivity.this, AlertView.Style.Alert, null).setOnDismissListener(new OnDismissListener() {
                                    @Override
                                    public void onDismiss(Object o) {
                                        String resval = String.format(Constants.TEACHER_DETAIL, teacher_id);
                                        HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), null, spin_kit, new TeacherDetailXCallBack());
                                    }
                                }).show();
                            }
                        }
                    });
                }
                break;
            case R.id.tv_interactiveAnsweringQuestions: //互动答疑
                intent = new Intent();
                intent.putExtra("type", "teacher");
                intent.putExtra("teacher_id", teacher_id);
                openActivity(intent, OnlineQuestioningActivity.class);
                break;
        }
    }

    private void clearColor() {
        tv_introduce.setTextColor(ContextCompat.getColor(TeacherDetailActivity.this, R.color.textColor_scarch));
        tv_video.setTextColor(ContextCompat.getColor(TeacherDetailActivity.this, R.color.textColor_scarch));
        tv_evaluate.setTextColor(ContextCompat.getColor(TeacherDetailActivity.this, R.color.textColor_scarch));
    }

    /**
     * 教师详情
     */
    private class TeacherDetailXCallBack implements HttpHelper.XCallBack {
        private TeacherDetailInfo teacherDetailInfo = null;

        @Override
        public void onResponse(String result) {
            try {
                teacherDetailInfo = getHttpResult(result, TeacherDetailInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }
            if (null != teacherDetailInfo) {
                HttpHelper.getInstance().mLoadCirclePicture(MyApplication.IMAGE_HOST + teacherDetailInfo.getPhoto(), iv_headImg);
                tv_name.setText(teacherDetailInfo.getName());
                switch (teacherDetailInfo.getGender()) {
                    case "男":
                        iv_sex.setBackgroundResource(R.drawable.ico_man_js);
                        break;
                    case "女":
                        iv_sex.setBackgroundResource(R.drawable.ico_girl_js);
                        break;
                }

                if (teacherDetailInfo.isCollected()) {
                    tv_attention.setText("已关注");
                } else {
                    tv_attention.setText("关注");
                }
            }
        }
    }
}
