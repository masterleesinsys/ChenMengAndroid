package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.github.ybq.android.spinkit.SpinKitView;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.SubjectAdapter;
import com.wenyue.chenmeng.adapter.TeacherAdapter;
import com.wenyue.chenmeng.entity.GradeInfo;
import com.wenyue.chenmeng.entity.TeacherInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 名师
 */
@ContentView(R.layout.activity_teacher)
public class TeacherActivity extends BaseActivity {
    @ViewInject(R.id.dl_navigation)
    private DrawerLayout dl_navigation;
    @ViewInject(R.id.rv_grade)
    private RecyclerView rv_grade;  //年级
    @ViewInject(R.id.rv_subject)
    private RecyclerView rv_subject;    //学科
    @ViewInject(R.id.rl_layout)
    private RelativeLayout rl_layout;
    @ViewInject(R.id.view)
    private View view;
    @ViewInject(R.id.rv_teacher)
    private RecyclerView rv_teacher;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.not_info)
    private View not_info;
    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private ActionBarDrawerToggle drawerbar;

    private SubjectAdapter gradeAdapter;
    private SubjectAdapter subjectAdapter;
    private List<GradeInfo> gradeInfoList = null;

    private String grade_id = "";

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new HashMap<>();
        map.put("grade", "");
        map.put("subject", "");
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.TEACHER_LIST), map, spin_kit, new TeacherListXCallBack());
    }

    @Override
    protected void initView() {
        setStyle(STYLE_BACK_QUERY);
        showBackButton(false);
        setCaption("名师导航");
        setTitleBarImageResource(R.drawable.ico_an_xinxi_new);
        setOnTitleBarRightImgListener(new onTitleBarRightImgListener() {
            @Override
            public void onTitleBarRightImgListener() {
                openActivity(OnlineQuestioningActivity.class);
            }
        });

        //设置菜单内容之外其他区域的背景色
//        dl_navigation.setScrimColor(Color.TRANSPARENT);

        toolbar.setNavigationIcon(R.drawable.ico_screening_def_dh);

        drawerbar = new ActionBarDrawerToggle(this, dl_navigation, toolbar, 0, 0) {
            //菜单打开
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            //菜单关闭
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
//        drawerbar.syncState();    //只有去掉这一行，自定义的图标才会显示
        dl_navigation.addDrawerListener(drawerbar);

        initRecycler();
    }

    private void initRecycler() {
        mInitRecyclerView(rv_grade, 2);
        mInitRecyclerView(rv_subject, 2);
        mInitRecyclerView(rv_teacher, 3, 2);

        rv_teacher.setNestedScrollingEnabled(false);

        String resval = String.format(Constants.BASIC_MODEL_LIST, "grade");
        HttpHelper.getInstance().get(MyApplication.getURL(resval), null, spin_kit, new BasicModelGradeListXCallBack());
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
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }
            if (gradeInfoList != null) {
                gradeAdapter = new SubjectAdapter(gradeInfoList, TeacherActivity.this, 1);
                rv_grade.setAdapter(gradeAdapter);

                gradeAdapter.setOnItemClickListener(new SubjectAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(final int position1, String id) {
                        view.setVisibility(View.VISIBLE);
                        rv_subject.setVisibility(View.VISIBLE);

                        grade_id = String.valueOf(id);

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
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }
            if (subjectList != null) {
                subjectAdapter = new SubjectAdapter(subjectList, TeacherActivity.this, 2);
                rv_subject.setAdapter(subjectAdapter);
                subjectAdapter.setOnItemClickListener(new SubjectAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position2, String id) {
                        if (dl_navigation.isDrawerOpen(rl_layout)) {
                            dl_navigation.closeDrawer(rl_layout);
                        }
                        Map<String, String> map = new HashMap<>();
                        map.put("grade", grade_id);
                        map.put("subject", subjectList.get(position2).getId());
                        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.TEACHER_LIST), map, spin_kit, new TeacherListXCallBack());
                    }
                });
            }
        }
    }

    /**
     * 名师导航
     */
    private class TeacherListXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            List<TeacherInfo> list = null;
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, TeacherInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }

            if (list != null && list.size() > 0) {
                not_info.setVisibility(View.GONE);
                TeacherAdapter teacherAdapter = new TeacherAdapter(list, TeacherActivity.this);
                rv_teacher.setAdapter(teacherAdapter);
                teacherAdapter.setOnItemClickListener(new TeacherAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position, String teacher_id) {
                        Intent intent = new Intent();
                        intent.putExtra("teacher_id", teacher_id);
                        openActivity(intent, TeacherDetailActivity.class);
                    }
                });
            } else {
                not_info.setVisibility(View.VISIBLE);
                TeacherAdapter teacherAdapter = new TeacherAdapter(null, TeacherActivity.this);
                rv_teacher.setAdapter(teacherAdapter);
            }
        }
    }
}
