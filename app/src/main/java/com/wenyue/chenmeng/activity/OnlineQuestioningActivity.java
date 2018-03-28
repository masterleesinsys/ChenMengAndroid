package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.TeacherDetailInfo;
import com.wenyue.chenmeng.util.GlideImageLoader;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * 在线提问
 */
@SuppressWarnings("ALL")
@ContentView(R.layout.activity_online_questioning)
public class OnlineQuestioningActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @ViewInject(R.id.rb_one)
    private RadioButton rb_one;
    @ViewInject(R.id.rb_two)
    private RadioButton rb_two;
    @ViewInject(R.id.rb_three)
    private RadioButton rb_three;
    @ViewInject(R.id.rb_four)
    private RadioButton rb_four;
    @ViewInject(R.id.rb_five)
    private RadioButton rb_five;
    @ViewInject(R.id.rb_six)
    private RadioButton rb_six;
    @ViewInject(R.id.rb_seven)
    private RadioButton rb_seven;
    @ViewInject(R.id.rb_eight)
    private RadioButton rb_eight;
    @ViewInject(R.id.rb_nine)
    private RadioButton rb_nine;
    @ViewInject(R.id.rb_ten)
    private RadioButton rb_ten;
    @ViewInject(R.id.tv_cost)
    private TextView tv_cost;   //费用
    @ViewInject(R.id.tv_total)
    private TextView tv_total;   //总价
    @ViewInject(R.id.tv_service_content)
    private TextView tv_service_content;   //科目
    @ViewInject(R.id.et_context)
    private EditText et_context;    //问题描述
    @ViewInject(R.id.et_title)
    private EditText et_title;  //问题标题
    @ViewInject(R.id.iv_image)
    private ImageView iv_image; //展示图片
    @ViewInject(R.id.iv_headImg)
    private ImageView iv_headImg; //教师头像
    @ViewInject(R.id.tv_name)
    private TextView tv_name; //教师昵称
    @ViewInject(R.id.ll_photograph)
    private LinearLayout ll_photograph; //拍照
    @ViewInject(R.id.sv_online_questioning)
    private ScrollView sv_online_questioning;
    @ViewInject(R.id.rl_teacher)
    private RelativeLayout rl_teacher;  //教师
    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private static final int XJ_RESULT = 1;
    private static final int GRADE_RESULT = 2;
    private File questioningfile = null;       //上传用户提问文件
    private ArrayList<ImageItem> images;

    private Double unitCost = 30.0;     //单价
    private String teacher_id = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("在线提问");

        rb_one.setChecked(true);
        tv_cost.setText("￥" + unitCost + "*1");
        tv_total.setText("￥" + (unitCost * 1));

        OverScrollDecoratorHelper.setUpOverScroll(sv_online_questioning);

        if (getIntent().hasExtra("type"))
            rl_teacher.setVisibility(View.VISIBLE);

        if (getIntent().hasExtra("teacher_id")) {
            teacher_id = getIntent().getStringExtra("teacher_id");
            String resval = String.format(Constants.TEACHER_DETAIL, teacher_id);
            HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), null, spin_kit, new TeacherDetailXCallBack());
        }
        initPicValue();
        initListener();
    }

    @Event(value = {R.id.ll_photograph, R.id.ll_subject})
    private void onClcick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_photograph:    //拍照
                intent = new Intent(OnlineQuestioningActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
                startActivityForResult(intent, XJ_RESULT);
                break;
            case R.id.ll_subject:   //选择科目
                intent = new Intent(this, GradeChoiceActivity.class);
                startActivityForResult(intent, GRADE_RESULT);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        try {
            if (isChecked) {
                tv_cost.setText("￥" + unitCost + " * " + buttonView.getText().toString().trim());
                tv_total.setText("￥" + (unitCost * Integer.valueOf(buttonView.getText().toString().trim())));
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
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
            }
        }
    }

    /**
     * 获取图片
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case ImagePicker.RESULT_CODE_ITEMS:
                if (data != null) {
                    images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    switch (requestCode) {
                        case XJ_RESULT:
                            if (images.size() > 0) {
                                String imgpath = images.get(0).path;
                                questioningfile = new File(imgpath);
                                HttpHelper.getInstance().mLoadPicture(imgpath, iv_image);
                            } else {
                                showToastText("没有选择图片");
                            }
                            break;
                    }
                } else {
                    showToastText("没有数据");
                    LogUtils.e("没有数据");
                }
                break;
            case RESULT_OK:
                String grade_id = data.getStringExtra("grade_id");
                String grade_name = data.getStringExtra("grade_name");
                String subject_id = data.getStringExtra("subject_id");
                String subject_name = data.getStringExtra("subject_name");
                tv_service_content.setText(grade_name + " " + subject_name);
                break;
        }
    }

    private void initPicValue() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(500);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(500);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(300);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(300);//保存文件的高度。单位像素
    }

    private void initListener() {
        rb_one.setOnCheckedChangeListener(this);
        rb_two.setOnCheckedChangeListener(this);
        rb_three.setOnCheckedChangeListener(this);
        rb_four.setOnCheckedChangeListener(this);
        rb_five.setOnCheckedChangeListener(this);
        rb_six.setOnCheckedChangeListener(this);
        rb_seven.setOnCheckedChangeListener(this);
        rb_eight.setOnCheckedChangeListener(this);
        rb_nine.setOnCheckedChangeListener(this);
        rb_ten.setOnCheckedChangeListener(this);
    }

}
