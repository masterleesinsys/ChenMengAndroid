package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.RechargeAdapter;
import com.wenyue.chenmeng.entity.VideoDetailInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * 视频详情
 */
@ContentView(R.layout.activity_video_detail)
public class VideoDetailActivity extends BaseActivity {
    @ViewInject(R.id.ll_collect)
    private LinearLayout ll_collect;    //收藏
    @ViewInject(R.id.sv_video_detail)
    private ScrollView sv_video_detail;
    @ViewInject(R.id.ll_giveAReward)
    private LinearLayout ll_giveAReward;    //打赏
    @ViewInject(R.id.ll_sellingPrice)
    private LinearLayout ll_sellingPrice;    //售价
    @ViewInject(R.id.ll_youOrdered)
    private LinearLayout ll_youOrdered;    //已购
    @ViewInject(R.id.ll_order)
    private LinearLayout ll_order;    //订购课程

    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_courseDescription)
    private TextView tv_courseDescription;  //课程介绍
    @ViewInject(R.id.rl_teacher)
    private RelativeLayout rl_teacher;  //教师
    @ViewInject(R.id.iv_headImg)
    private ImageView iv_headImg;  //教师头像
    @ViewInject(R.id.tv_name)
    private TextView tv_name;  //教师姓名

    @ViewInject(R.id.iv_collect_add)
    private ImageView iv_collect_add;
    @ViewInject(R.id.tv_collect_add)
    private TextView tv_collect_add;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private AlertView mAlertView = null;
    private EditText et_else;
    private RecyclerView rv_prices;
    private LinearLayout ll_alipay;
    private LinearLayout ll_wechat;
    private LinearLayout ll_balance;
    private ImageView iv_alipay_check;
    private ImageView iv_wechat_check;
    private ImageView iv_balance_check;

    private RechargeAdapter rechargeAdapter;
    private String[] strings = {"50元", "100元", "200元", "500元", "1000元", "2000元",};
    private List<String> list;
    private String rechargeAmount = "";   //充值金额
    private String modeOfPayment = "";    //支付方式

    private String id = ""; //视频id
    private String teacher_id = ""; //教师id

    private Boolean collected = false;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("视频详情");

        OverScrollDecoratorHelper.setUpOverScroll(sv_video_detail);

        ll_collect.setBackgroundDrawable(mDrawableStyle(R.color.full_transparency, 3, 1, R.color.textColor_hint));

        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
            String resval = String.format(Constants.VIDEO, id);
            HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), null, spin_kit, new VideoXCallBack());
        }
    }

    @Event(value = {R.id.ll_giveAReward, R.id.rl_teacher, R.id.ll_collect})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_giveAReward:  //打赏
                myAlertView();
                break;
            case R.id.rl_teacher:   //教师
                if ("".equals(teacher_id)) {
                    showToastText("未获取到教师信息");
                    return;
                }
                intent = new Intent();
                intent.putExtra("teacher_id", teacher_id);
                openActivity(intent, TeacherDetailActivity.class);
                break;
            case R.id.ll_collect:   //收藏
                if ("".equals(id)) {
                    showToastText("视频不存在");
                    return;
                }
                if (collected) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", id);
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
                                new AlertView("取消收藏", "取消收藏成功", null, new String[]{"知道了"}, null, VideoDetailActivity.this, AlertView.Style.Alert, null).setOnDismissListener(new OnDismissListener() {
                                    @Override
                                    public void onDismiss(Object o) {
                                        String resval = String.format(Constants.VIDEO, id);
                                        HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), null, spin_kit, new VideoXCallBack());
                                    }
                                }).show();
                            }
                        }
                    });
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("type", "0");
                    map.put("object", id);
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
                                new AlertView("添加收藏", "添加收藏成功", null, new String[]{"知道了"}, null, VideoDetailActivity.this, AlertView.Style.Alert, null).setOnDismissListener(new OnDismissListener() {
                                    @Override
                                    public void onDismiss(Object o) {
                                        String resval = String.format(Constants.VIDEO, id);
                                        HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), null, spin_kit, new VideoXCallBack());
                                    }
                                }).show();
                            }
                        }
                    });
                }
                break;
        }
    }

    private void myAlertView() {
        //拓展窗口
        @SuppressLint("InflateParams") ViewGroup extView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alert_give_areward, null);
        et_else = extView.findViewById(R.id.et_else);
        rv_prices = extView.findViewById(R.id.rv_prices);
        ll_alipay = extView.findViewById(R.id.ll_alipay);
        ll_wechat = extView.findViewById(R.id.ll_wechat);
        ll_balance = extView.findViewById(R.id.ll_balance);
        iv_alipay_check = extView.findViewById(R.id.iv_alipay_check);
        iv_wechat_check = extView.findViewById(R.id.iv_wechat_check);
        iv_balance_check = extView.findViewById(R.id.iv_balance_check);

        iv_alipay_check.setImageResource(R.drawable.ico_choose_def);
        iv_wechat_check.setImageResource(R.drawable.ico_choose);
        iv_balance_check.setImageResource(R.drawable.ico_choose);
        modeOfPayment = "支付宝";

        ll_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_alipay_check.setImageResource(R.drawable.ico_choose_def);
                iv_wechat_check.setImageResource(R.drawable.ico_choose);
                iv_balance_check.setImageResource(R.drawable.ico_choose);
                modeOfPayment = "支付宝";
            }
        });

        ll_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_alipay_check.setImageResource(R.drawable.ico_choose);
                iv_wechat_check.setImageResource(R.drawable.ico_choose_def);
                iv_balance_check.setImageResource(R.drawable.ico_choose);
                modeOfPayment = "微信";
            }
        });

        ll_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_alipay_check.setImageResource(R.drawable.ico_choose);
                iv_wechat_check.setImageResource(R.drawable.ico_choose);
                iv_balance_check.setImageResource(R.drawable.ico_choose_def);
                modeOfPayment = "余额";
            }
        });

        mInitRecyclerView(rv_prices, 3, 0);
        list = new ArrayList<>();
        Collections.addAll(list, strings);
        rechargeAdapter = new RechargeAdapter(list, VideoDetailActivity.this);
        rv_prices.setAdapter(rechargeAdapter);
        rechargeAdapter.setOnItemClickListener(new RechargeAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                rechargeAmount = list.get(position).substring(0, list.get(position).length() - 1);
            }
        });

        mAlertView = new AlertView("打赏视频", null, "取消", null, new String[]{"打赏"}, this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                final String etPrice = et_else.getText().toString();
                if (position == 0) {
                    if ("".equals(etPrice) && "".equals(rechargeAmount)) {
                        showToastText("请选择支付金额");
                        return;
                    } else if (!"".equals(etPrice)) {
                        rechargeAmount = etPrice;
                    }
                    showToastText(rechargeAmount);
//                    Map<String, Object> map;
//                    switch (modeOfPayment) {
//                        case "支付宝":
//                            map = new HashMap<>();
//                            map.put("out_trade_no", id);
//                            map.put("total_fee", 0.01);
//                            HttpHelper.getInstance().post(MyApplication.getTokenURL(Constants.PAY_CREATE_ALIPAY), map, spin_kit, new PayCreateAlipayXCallBack());
//                            break;
//                        case "微信":
//                            map = new HashMap<>();
//                            map.put("out_trade_no", id);
//                            map.put("total_fee", "0.01");
//                            HttpHelper.getInstance().post(MyApplication.getTokenURL(Constants.PAY_CREATE_WXPAY), map, spin_kit, new PayCreateWxpayXCallBack());
//                            break;
//                        case "余额":
//                            break;
//                    }
                }
            }
        });
        mAlertView.show();
        mAlertView.addExtView(extView);
    }

    /**
     * 视频详情
     */
    private class VideoXCallBack implements HttpHelper.XCallBack {
        @Override
        public void onResponse(String result) {
            VideoDetailInfo videoDetailInfo = null;
            try {
                videoDetailInfo = getHttpResult(result, VideoDetailInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }
            if (null != videoDetailInfo) {
                teacher_id = videoDetailInfo.getTeacher().getTeacher_id();

                ll_youOrdered.setVisibility(View.GONE);
                ll_sellingPrice.setVisibility(View.VISIBLE);
                ll_order.setVisibility(View.VISIBLE);
                ll_giveAReward.setVisibility(View.VISIBLE);

                collected = videoDetailInfo.isCollected();
                if (collected) { //是否收藏
//                    ll_youOrdered.setVisibility(View.VISIBLE);
//                    ll_sellingPrice.setVisibility(View.GONE);
//                    ll_order.setVisibility(View.GONE);
//                    ll_giveAReward.setVisibility(View.VISIBLE);

                    iv_collect_add.setImageResource(R.drawable.ico_collected_xq);
                    tv_collect_add.setText("已收藏");
                } else { //未购买
//                    ll_youOrdered.setVisibility(View.GONE);
//                    ll_sellingPrice.setVisibility(View.VISIBLE);
//                    ll_order.setVisibility(View.VISIBLE);
//                    ll_giveAReward.setVisibility(View.VISIBLE);

                    iv_collect_add.setImageResource(R.drawable.ico_collection_xq);
                    tv_collect_add.setText("收藏");
                }

                tv_title.setText(videoDetailInfo.getSub_featured_category_text());
                tv_courseDescription.setText(videoDetailInfo.getDescription());
                HttpHelper.getInstance().mLoadCirclePicture(MyApplication.IMAGE_HOST + videoDetailInfo.getTeacher().getPhoto(), iv_headImg);
            }
        }
    }
}
