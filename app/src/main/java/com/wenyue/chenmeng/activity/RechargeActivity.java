package com.wenyue.chenmeng.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.RechargeAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 充值
 */
@ContentView(R.layout.activity_recharge)
public class RechargeActivity extends BaseActivity {
    @ViewInject(R.id.ll_alipay)
    private LinearLayout ll_alipay; //支付宝
    @ViewInject(R.id.iv_alipay_check)
    private ImageView iv_alipay_check;
    @ViewInject(R.id.ll_wechat)
    private LinearLayout ll_wechat; //微信
    @ViewInject(R.id.iv_wechat_check)
    private ImageView iv_wechat_check;
    @ViewInject(R.id.tv_recharge_affirm)
    private TextView tv_recharge_affirm;    //确认
    @ViewInject(R.id.et_else)
    private EditText et_else;   //其他金额
    @ViewInject(R.id.rv_grade)
    private RecyclerView rv_grade;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String rechargeAmount = "";   //充值金额
    private String modeOfPayment = "";    //支付方式

    private RechargeAdapter rechargeAdapter;
    private String[] strings = {"50元", "100元", "200元", "500元", "1000元", "2000元",};
    private List<String> list;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("充值");

        iv_alipay_check.setImageResource(R.drawable.ico_choose_def);
        iv_wechat_check.setImageResource(R.drawable.ico_choose);
        modeOfPayment = "支付宝";

        mInitRecyclerView(rv_grade, 3, 0);
        list = new ArrayList<>();
        Collections.addAll(list, strings);
        rechargeAdapter = new RechargeAdapter(list, RechargeActivity.this);
        rv_grade.setAdapter(rechargeAdapter);
        rechargeAdapter.setOnItemClickListener(new RechargeAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                rechargeAmount = list.get(position).substring(0, list.get(position).length() - 1);
            }
        });
    }

    @Event(value = {R.id.et_else, R.id.ll_alipay, R.id.ll_wechat, R.id.tv_recharge_affirm})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        String else_rechargeAmount = et_else.getText().toString().trim();
        switch (view.getId()) {
            case R.id.ll_alipay:    //支付宝
                modeOfPayment = "支付宝";
                iv_alipay_check.setImageResource(R.drawable.ico_choose_def);
                iv_wechat_check.setImageResource(R.drawable.ico_choose);
                break;
            case R.id.ll_wechat:    //微信
                modeOfPayment = "微信";
                iv_alipay_check.setImageResource(R.drawable.ico_choose);
                iv_wechat_check.setImageResource(R.drawable.ico_choose_def);
                break;
            case R.id.tv_recharge_affirm:   //确认
                if ("".equals(else_rechargeAmount) && "".equals(rechargeAmount)) {
                    showToastText("请选择支付金额");
                    return;
                }
                break;
            case R.id.et_else:
                if (null != rechargeAdapter && null != list)
                    rechargeAdapter.updateData(list);
                break;
        }
    }
}
