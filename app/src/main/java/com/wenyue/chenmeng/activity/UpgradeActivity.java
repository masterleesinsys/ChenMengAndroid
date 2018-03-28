package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.UpgradeVipAdapter;
import com.wenyue.chenmeng.entity.UpGeadeVipList;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * VIP升级&购买
 */
@ContentView(R.layout.activity_upgrade)
public class UpgradeActivity extends BaseActivity {
    @ViewInject(R.id.rv_vip)
    private RecyclerView rv_vip;
    @ViewInject(R.id.tv_payment)
    private TextView tv_payment;
    @ViewInject(R.id.tv_cost)
    private TextView tv_cost;
    @ViewInject(R.id.tv_periodOfValidity)
    private TextView tv_periodOfValidity;

    private List<UpGeadeVipList> list = new ArrayList<>();
    private UpGeadeVipList upGeadeVipList;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("VIP升级/购买VIP");

        initRecycler();
    }

    private void initRecycler() {
        mInitRecyclerView(rv_vip, 2);

        addData();

        UpgradeVipAdapter upgradeVipAdapter = new UpgradeVipAdapter(list, this);
        rv_vip.setAdapter(upgradeVipAdapter);
        upgradeVipAdapter.setOnItemClickListener(new UpgradeVipAdapter.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClickListener(int position) {
                String costHtml = "";
                switch (position) {
                    case 0:
                        tv_cost.setVisibility(View.VISIBLE);
                        tv_periodOfValidity.setVisibility(View.VISIBLE);
                        costHtml = "<font color='#333333'>费用：</font><font color='#e62129'>￥1980元   VIP1/1年</font>";
                        tv_cost.setText(Html.fromHtml(costHtml));
                        tv_periodOfValidity.setText("有效期：2019年01月23日");
                        tv_payment.setText("确认去付款(VIP1 1980元/年)");
                        break;
                    case 1:
                        tv_cost.setVisibility(View.VISIBLE);
                        tv_periodOfValidity.setVisibility(View.VISIBLE);
                        costHtml = "<font color='#333333'>费用：</font><font color='#e62129'>￥2980元   VIP2/1年</font>";
                        tv_cost.setText(Html.fromHtml(costHtml));
                        tv_periodOfValidity.setText("有效期：2019年01月23日");
                        tv_payment.setText("确认去付款(VIP2 2980元/年)");
                        break;
                    case 2:
                        tv_cost.setVisibility(View.VISIBLE);
                        tv_periodOfValidity.setVisibility(View.VISIBLE);
                        costHtml = "<font color='#333333'>费用：</font><font color='#e62129'>￥3980元   VIP3/1年</font>";
                        tv_cost.setText(Html.fromHtml(costHtml));
                        tv_periodOfValidity.setText("有效期：2019年01月23日");
                        tv_payment.setText("确认去付款(VIP3 3980元/年)");
                        break;
                }
            }
        });
    }

    private void addData() {
        upGeadeVipList = new UpGeadeVipList(R.drawable.bg_vip_1, "VIP1", "名师培优   互动答疑   课程同步   精品直播", "预约授课(120/课时)   名师答疑(30/课时)", "教育商城(9.5折)   课程回放(30/课时)", "1980元/年");
        list.add(upGeadeVipList);
        upGeadeVipList = new UpGeadeVipList(R.drawable.bg_vip_2, "VIP2", "名师培优   互动答疑   课程同步   精品直播", "预约授课(90/课时)   名师答疑(15/课时)", "教育商城(9折)", "2980元/年");
        list.add(upGeadeVipList);
        upGeadeVipList = new UpGeadeVipList(R.drawable.bg_vip_3, "VIP3", "名师培优   互动答疑   课程同步   精品直播", "预约授课(60/课时)   名师答疑(10/课时)", "教育商城(8.5折)", "3980元/年");
        list.add(upGeadeVipList);
    }
}
