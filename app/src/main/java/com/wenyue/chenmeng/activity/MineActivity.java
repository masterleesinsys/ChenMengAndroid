package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.UserInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * 我的
 */
@ContentView(R.layout.activity_mine)
public class MineActivity extends BaseActivity {
    @ViewInject(R.id.iv_headImg)
    private ImageView iv_headImg;
    @ViewInject(R.id.iv_message)
    private ImageView iv_message;
    @ViewInject(R.id.rl_upgradeVip)
    private RelativeLayout rl_upgradeVip;
    @ViewInject(R.id.tv_user)
    private TextView tv_user;
    @ViewInject(R.id.tv_vip)
    private TextView tv_vip;
    @ViewInject(R.id.tv_number_attention)
    private TextView tv_number_attention;
    @ViewInject(R.id.tv_number_collect)
    private TextView tv_number_collect;
    @ViewInject(R.id.tv_number_soFar)
    private TextView tv_number_soFar;
    @ViewInject(R.id.tv_expirationTime)
    private TextView tv_expirationTime;
    @ViewInject(R.id.ll_number_collect)
    private LinearLayout ll_number_collect;
    @ViewInject(R.id.ll_number_attention)
    private LinearLayout ll_number_attention;
    @ViewInject(R.id.ll_number_soFar)
    private LinearLayout ll_number_soFar;
    @ViewInject(R.id.ll_myOrder)
    private LinearLayout ll_myOrder;
    @ViewInject(R.id.ll_iPlayToAdmire)
    private LinearLayout ll_iPlayToAdmire;
    @ViewInject(R.id.ll_myPromotion)
    private LinearLayout ll_myPromotion;
    @ViewInject(R.id.ll_myVideos)
    private LinearLayout ll_myVideos;
    @ViewInject(R.id.ll_myFootprint)
    private LinearLayout ll_myFootprint;
    @ViewInject(R.id.ll_shippingAddress)
    private LinearLayout ll_shippingAddress;
    @ViewInject(R.id.ll_morningTeacherEducation)
    private LinearLayout ll_morningTeacherEducation;
    @ViewInject(R.id.ll_systemSettings)
    private LinearLayout ll_systemSettings;
    @ViewInject(R.id.sv_mine)
    private ScrollView sv_mine;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @Override
    protected void initView() {
        rl_upgradeVip.setBackgroundDrawable(mDrawableStyle(R.color.translucent_white_25, 15));

        OverScrollDecoratorHelper.setUpOverScroll(sv_mine);

        getUser();
    }

    private void getUser() {
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.MEMBER_PROFILE), null, spin_kit, new HttpHelper.XCallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String result) {
                LogUtils.e(result);
                UserInfo user = null;
                try {
                    user = getHttpResult(result, UserInfo.class);
                } catch (Exception e) {
                    showToastText(e.toString());
                    return;
                }
                if (user != null) {
                    HttpHelper.getInstance().mLoadCirclePicture(MyApplication.IMAGE_HOST + user.getMember().getIcon(), iv_headImg);

                    tv_user.setText(user.getName() == null ? "" : user.getName());
                    tv_number_attention.setText("" + user.getTeacher_collected_count());
                    tv_number_collect.setText("" + user.getVideo_collected_count());
                    tv_number_soFar.setText("" + user.getBalance());
                    tv_vip.setText("" + user.getMember().getLevel_text());
                    tv_expirationTime.setText("" + user.getMember().getVip_expire_date() + "到期");
                }
            }
        });
    }

    @Event(value = {R.id.ll_systemSettings, R.id.tv_expirationTime, R.id.tv_user, R.id.iv_headImg, R.id.iv_message, R.id.rl_upgradeVip, R.id.ll_myOrder, R.id.ll_myPromotion, R.id.ll_morningTeacherEducation, R.id.ll_iPlayToAdmire, R.id.ll_myVideos, R.id.ll_topUp
            , R.id.ll_shippingAddress, R.id.ll_number_attention, R.id.ll_number_soFar, R.id.ll_number_collect})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.ll_systemSettings:    //系统设置
                openActivity(SetActivity.class);
                break;
            case R.id.tv_expirationTime:
            case R.id.tv_user:
            case R.id.iv_headImg:       //个人信息
                openNeedLoginActivity(MyInfoActivity.class);
                break;
            case R.id.rl_upgradeVip:    //升级&购买VIP
                openNeedLoginActivity(UpgradeActivity.class);
                break;
            case R.id.iv_message:       //消息
                openNeedLoginActivity(MessageActivity.class);
                break;
            case R.id.ll_number_collect:    //我的收藏
                openNeedLoginActivity(MyCollectActivity.class);
                break;
            case R.id.ll_number_attention:  //我的关注
                openNeedLoginActivity(MyInterestActivity.class);
                break;
            case R.id.ll_topUp: //充值
                openNeedLoginActivity(RechargeActivity.class);
                break;
            case R.id.ll_number_soFar:  //收益金
                openNeedLoginActivity(BalanceActivity.class);
                break;
            case R.id.ll_myOrder:   //我的订单
                openNeedLoginActivity(OrderDynamicActivity.class);
                break;
            case R.id.ll_iPlayToAdmire:     //我的打赏
                openNeedLoginActivity(GiveARewardActivity.class);
                break;
            case R.id.ll_myPromotion:   //我的推广
                openNeedLoginActivity(MyPromotionActivity.class);
                break;
            case R.id.ll_myVideos:  //我的视频
                break;
            case R.id.ll_shippingAddress:   //收货地址
                openNeedLoginActivity(ShippingAddressActivity.class);
                break;
            case R.id.ll_morningTeacherEducation:   //晨师教育
                openActivity(EducationActivity.class);
                break;
        }
    }
}
