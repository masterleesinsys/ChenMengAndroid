package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.ybq.android.spinkit.SpinKitView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.GradeInfo;
import com.wenyue.chenmeng.entity.UserInfo;
import com.wenyue.chenmeng.util.GlideImageLoader;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.angmarch.views.NiceSpinner;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 完善资料
 */
@SuppressWarnings("ALL")
@ContentView(R.layout.activity_perfectdata)
public class RegisterPerfectdataActivity extends BaseActivity {
    @ViewInject(R.id.iv_head_portrait)
    private ImageView iv_head_portrait;
    @ViewInject(R.id.tv_gohomenext)
    private TextView tv_gohomenext;
    @ViewInject(R.id.et_nickname)
    private EditText et_nickname;
    @ViewInject(R.id.ns_sex)
    private NiceSpinner ns_sex;
    @ViewInject(R.id.ns_grade)
    private NiceSpinner ns_grade;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private File uploadpicfile = null;       //上传用户头像文件
    private ArrayList<ImageItem> images;
    private String nickName;     //昵称
    private String grade_id;     //年级id
    private Integer user_id;
    private Integer is_sex;

    private List<GradeInfo> list = null;

    private static final int XJ_RESULT = 1;
    private String[] str1 = {"选择性别", "男", "女"};

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("帐号注册:完善资料(3/3)");

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);

        String resval = String.format(Constants.BASIC_MODEL_LIST, "grade");
        HttpHelper.getInstance().get(MyApplication.getURL(resval), null, spin_kit, new BasicModelListXCallBack());

        initPicValue();
        initData();
    }

    private void initData() {
        List<String> dataset1 = new ArrayList<>();
        dataset1.addAll(Arrays.asList(str1));
        ns_sex.attachDataSource(dataset1);
    }

    private void initPicValue() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(500);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(500);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(300);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(300);//保存文件的高度。单位像素
    }

    @Event(value = {R.id.tv_gohomenext, R.id.ll_photo})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.tv_gohomenext:
                nickName = et_nickname.getText().toString().trim();
                String sex = ns_sex.getText().toString().trim();
                if (uploadpicfile == null) {
                    showToastText("请选择头像!");
                    return;
                } else if (nickName.equals("") || nickName == null) {
                    showToastText("请输入您的初始昵称!");
                    return;
                } else if (sex.equals("选择性别")) {
                    showToastText("请选择您的性别!");
                    return;
                } else if (ns_grade.getText().toString().trim().equals("选择年级")) {
                    showToastText("请选择年级!");
                    return;
                }

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("name", nickName);
                switch (sex) {
                    case "男":
                        is_sex = 1;
                        break;
                    case "女":
                        is_sex = 2;
                        break;
                }
                map.put("sex", sex.equals("男") ? "1" : "2");
                map.put("grade", grade_id);
                HashMap<String, File> mapfile = new HashMap<>();
                mapfile.put("head_img", uploadpicfile);
                HttpHelper.getInstance().upLoadFile(MyApplication.getTokenURL(Constants.USER_REGISTER_FINISH), map, mapfile, spin_kit, new mModifyMemberInformation());
                break;
            case R.id.ll_photo:     //上传头像
                Intent intent = new Intent(RegisterPerfectdataActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
                startActivityForResult(intent, XJ_RESULT);
                break;
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
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                switch (requestCode) {
                    case XJ_RESULT:
                        if (images.size() > 0) {
                            String imgpath = images.get(0).path;
                            uploadpicfile = new File(imgpath);
                            HttpHelper.getInstance().mLoadPicture(imgpath, iv_head_portrait);
                        } else {
                            showToastText("没有选择图片");
                        }
                        break;
                }
            } else {
                showToastText("没有数据");
                LogUtils.e("没有数据");
            }
        }
    }

    private class mModifyMemberInformation implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            UserInfo user;
            try {
                user = getHttpResult(result, UserInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                showToastText(e.toString());
                return;
            }
            if (user != null) {
                UserInfo newuser = MyApplication.getDetailFromLocal();
                if (null != newuser) {
                    MyApplication.syncDetail(newuser);
                }

                showToastText("注册成功");
                openActivity(MainActivity.class);
                finish();
            }
        }
    }

    /**
     * 年级列表
     */
    private class BasicModelListXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, GradeInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
            }
            if (list != null) {
                List<String> dataset2 = new ArrayList<>();
                for (GradeInfo gradeInfo : list) {
                    dataset2.add(gradeInfo.getName());
                }
                ns_grade.attachDataSource(dataset2);
                ns_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        grade_id = list.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }
    }
}
