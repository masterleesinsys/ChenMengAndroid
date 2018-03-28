package com.wenyue.chenmeng.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.github.ybq.android.spinkit.SpinKitView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.UserInfo;
import com.wenyue.chenmeng.util.GlideImageLoader;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 我的资料
 */
@SuppressWarnings("ALL")
@ContentView(R.layout.activity_my_info)
public class MyInfoActivity extends BaseActivity {
    @ViewInject(R.id.ll_nyinfo_modifyavatar)
    private LinearLayout ll_nyinfo_modifyavatar;    //修改头像

    @ViewInject(R.id.iv_myinfo_headportrait)
    private ImageView iv_myinfo_headportrait;   //头像

    @ViewInject(R.id.ll_myinfo_modifynickname)
    private LinearLayout ll_myinfo_modifynickname;  //修改昵称
    @ViewInject(R.id.tv_myinfo_modifynickname)
    private TextView tv_myinfo_modifynickname;

    @ViewInject(R.id.ll_myinfo_sex)
    private LinearLayout ll_myinfo_sex;     //性别
    @ViewInject(R.id.tv_myinfo_sex)
    private TextView tv_myinfo_sex;

    @ViewInject(R.id.ll_grade)
    private LinearLayout ll_grade;    //年级
    @ViewInject(R.id.tv_grade)
    private TextView tv_grade;

    @ViewInject(R.id.ll_myinfo_location)
    private LinearLayout ll_myinfo_location;    //所在地
    @ViewInject(R.id.tv_myinfo_location)
    private TextView tv_myinfo_location;

    @ViewInject(R.id.ll_myinfo_setpassword)
    private LinearLayout ll_myinfo_setpassword;     //账户安全
    @ViewInject(R.id.tv_myinfo_setpassword)
    private TextView tv_myinfo_setpassword;     //修改密码

    @ViewInject(R.id.ll_myinfo_realname)
    private LinearLayout ll_myinfo_realname;       //实名认证
    @ViewInject(R.id.tv_myinfo_realname)
    private TextView tv_myinfo_realname;
    @ViewInject(R.id.iv_myinfo_go)
    private ImageView iv_myinfo_go;     //未认证&已认证

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    /**
     * 会员信息
     */
    private String name = null, grade = null, area = null;
    private Integer sex = 0;

    public static final int XJ_RESULT = 1;
    private ArrayList<ImageItem> images;

    private UserInfo user = null;
    private File head_img = null;

    private AlertView mAlertView = null;
    private EditText etName;
    private InputMethodManager imm = null;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("我的资料");

        initPicValue();
    }

    @Override
    protected void onResume() {
        getUserInfo();
        super.onResume();
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

    private void getUserInfo() {
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.MEMBER_PROFILE), null, spin_kit, new HttpHelper.XCallBack() {
            @Override
            public void onResponse(String result) {
                try {
                    user = getHttpResult(result, UserInfo.class);
                } catch (Exception e) {
                    showToastText(e.toString());
                    return;
                }
                if (user != null) {
                    name = user.getName() == null ? "" : user.getName();
                    HttpHelper.getInstance().mLoadCirclePicture(MyApplication.IMAGE_HOST + user.getMember().getIcon(), iv_myinfo_headportrait);

                    tv_myinfo_modifynickname.setText(name);
                    if (sex == 1) {
                        tv_myinfo_sex.setText("男");
                    } else {
                        tv_myinfo_sex.setText("女");
                    }

                    tv_grade.setText(user.getMember().getGrade_text());
                    tv_myinfo_location.setText(area);
                }
            }
        });
    }

    @Event(value = {R.id.ll_nyinfo_modifyavatar, R.id.ll_myinfo_modifynickname, R.id.ll_myinfo_location, R.id.ll_myinfo_setpassword, R.id.ll_myinfo_realname})
    private void onClick(View view) {
        Intent intent = null;
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.ll_nyinfo_modifyavatar:   //修改头像
                intent = new Intent(MyInfoActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, images);
                startActivityForResult(intent, XJ_RESULT);
                break;
            case R.id.ll_myinfo_modifynickname:     //修改昵称
                EditAlertView(tv_myinfo_modifynickname);
                break;
            case R.id.ll_myinfo_setpassword:    //修改密码
                openActivity(PasswordModiActivity.class);
                break;
            case R.id.ll_myinfo_realname:   //注销退出
                logout();
                break;
        }
    }

    /**
     * 从相机/相册中获取图片
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
                            head_img = new File(imgpath);

                            HashMap<String, File> map = new HashMap<String, File>();
                            if (head_img != null) {
                                map.put("head_img", head_img);
                            }
                            HttpHelper.getInstance().upLoadFile(MyApplication.getTokenURL(Constants.MEMBER_PROFILE_SAVE), null, map, spin_kit, new mModifyMemberInformation());
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

    private void logout() {
        mAlertView = null;
        mAlertView = new AlertView("注销账户", "是否确认退出当前账户?", "取消", new String[]{"确定"}, null, MyInfoActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position >= 0) {
                    MyApplication.userLogout();

//                    //取消微信授权
//                    platform = SHARE_MEDIA.WEIXIN;
//                    if (null != mShareAPI) {
//                        mShareAPI.deleteOauth(InfoActivity.this, platform, authListener);
//                    }
                    MyApplication.is_exit = true;
                    openActivity(LoginActivity.class);
                } else {
                    mAlertView.dismiss();
                }
            }
        });
        mAlertView.show();
    }

    private void EditAlertView(final TextView textView) {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //拓展窗口
        ViewGroup extView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alertext_form, null);
        etName = (EditText) extView.findViewById(R.id.etName);
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                //输入框出来则往上移动
                boolean isOpen = imm.isActive();
                mAlertView.setMarginBottom(isOpen && focus ? 230 : 0);
            }
        });
        mAlertView = new AlertView("修改昵称", "请输入新的昵称", "取消", null, new String[]{"确定"}, this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                final String etNameStr = etName.getText().toString();
                if (position == 0) {
                    if (TextUtils.isEmpty(etNameStr)) {
                        closeSoftKeyboard();
                        showToastText("输入内容不能为空");
                        return;
                    } else if (tv_myinfo_modifynickname.getText().toString().trim().equals(etNameStr)) {
                        closeSoftKeyboard();
                        showToastText("请输入新的昵称");
                        return;
                    }
                    closeSoftKeyboard();

                    tv_myinfo_modifynickname.setText(etNameStr);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name", etNameStr);
                    HttpHelper.getInstance().upLoadFile(MyApplication.getTokenURL(Constants.MEMBER_PROFILE_SAVE), map, null, spin_kit, new mModifyMemberInformation());
                } else {
                    closeSoftKeyboard();
                    mAlertView.dismiss();
                }
            }
        });
        mAlertView.show();
        mAlertView.addExtView(extView);
    }

    /**
     * 关闭软键盘
     */
    private void closeSoftKeyboard() {
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mAlertView != null && mAlertView.isShowing()) {
                mAlertView.dismiss();
                return true;
            } else {
                return super.dispatchKeyEvent(event);
            }
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    /**
     * 修改昵称，头像
     */
    private class mModifyMemberInformation implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            String reault;
            try {
                reault = getHttpResult(result, String.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                return;
            }
            if (reault != null) {
                getUserInfo();
            }
        }
    }
}
