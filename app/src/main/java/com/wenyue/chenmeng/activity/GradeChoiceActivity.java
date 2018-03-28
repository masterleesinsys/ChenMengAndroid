package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.SubjectChoiceAdapter;
import com.wenyue.chenmeng.entity.GradeInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 年级选择
 */
@SuppressWarnings("ALL")
@ContentView(R.layout.activity_grade_choice)
public class GradeChoiceActivity extends BaseActivity {
    @ViewInject(R.id.rv_grade)
    private RecyclerView rv_grade;
    @ViewInject(R.id.rv_subject)
    private RecyclerView rv_subject;

    private List<GradeInfo> gradeInfoList = null;
    private SubjectChoiceAdapter subjectChoiceAdapter;

    private String grade_id = "";
    private String grade_name = "";
    private String subject_id = "";
    private String subject_name = "";

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("年级选择");

        initRecycler();
    }

    private void initRecycler() {
        mInitRecyclerView(rv_grade, 4, 2);
        mInitRecyclerView(rv_subject, 4, 2);

        String resval = String.format(Constants.BASIC_MODEL_LIST, "grade");
        HttpHelper.getInstance().get(MyApplication.getURL(resval), null, spin_kit, new BasicModelGradeListXCallBack());
    }

    @Event(value = {R.id.tv_reset, R.id.tv_confirm})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_reset:     //重置
                grade_id = "";
                grade_name = "";
                subject_id = "";
                subject_name = "";
                String resval = String.format(Constants.BASIC_MODEL_LIST, "grade");
                HttpHelper.getInstance().get(MyApplication.getURL(resval), null, spin_kit, new BasicModelGradeListXCallBack());
                break;
            case R.id.tv_confirm:   //确定
                if ("".equals(grade_id) || "".equals(grade_name)) {
                    showToastText("请选择年级");
                    return;
                }
                if ("".equals(subject_id) || "".equals(subject_name)) {
                    showToastText("请选择科目");
                    return;
                }
                intent = new Intent();
                intent.putExtra("grade_id", grade_id);
                intent.putExtra("grade_name", grade_name);
                intent.putExtra("subject_id", subject_id);
                intent.putExtra("subject_name", subject_name);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    /**
     * 年级列表
     */
    private class BasicModelGradeListXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            try {
                String data = getHttpResultList(result);
                gradeInfoList = JSON.parseArray(data, GradeInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
            }
            if (gradeInfoList != null) {
                subjectChoiceAdapter = new SubjectChoiceAdapter(gradeInfoList, GradeChoiceActivity.this);
                rv_grade.setAdapter(subjectChoiceAdapter);

                subjectChoiceAdapter.setOnItemClickListener(new SubjectChoiceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        grade_id = gradeInfoList.get(position).getId();
                        grade_name = gradeInfoList.get(position).getName();
                        String resva2 = String.format(Constants.BASIC_MODEL_LIST, "subject");
                        Map<String, String> map = new HashMap<>();
                        map.put("grade", grade_id);
                        HttpHelper.getInstance().get(MyApplication.getURL(resva2), map, spin_kit, new BasicModelSubjectListXCallBack());
                    }
                });
            }
        }
    }

    /**
     * 学科列表
     */
    private class BasicModelSubjectListXCallBack implements HttpHelper.XCallBack {
        private List<GradeInfo> subjectList = null;

        @Override
        public void onResponse(String result) {
            try {
                String data = getHttpResultList(result);
                subjectList = JSON.parseArray(data, GradeInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
            }
            if (subjectList != null) {
                subjectChoiceAdapter = new SubjectChoiceAdapter(subjectList, GradeChoiceActivity.this);
                rv_subject.setAdapter(subjectChoiceAdapter);
                subjectChoiceAdapter.setOnItemClickListener(new SubjectChoiceAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        subject_id = subjectList.get(position).getId();
                        subject_name = subjectList.get(position).getName();
                    }
                });
            }
        }
    }
}
