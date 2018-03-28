package com.wenyue.chenmeng.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.MallAdapter;
import com.wenyue.chenmeng.entity.StoreProductInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商城
 */
@ContentView(R.layout.activity_mall)
public class MallActivity extends BaseActivity {
    @ViewInject(R.id.tv_course) //教程
    private TextView tv_course;

    @ViewInject(R.id.tv_testPaper)  //试卷
    private TextView tv_testPaper;

    @ViewInject(R.id.tv_readingMaterial)    //读物
    private TextView tv_readingMaterial;

    @ViewInject(R.id.tv_screen) //筛选
    private TextView tv_screen;

    @ViewInject(R.id.rv_mall)
    private RecyclerView rv_mall;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private int unChecked_color = 0;
    private int checked_color = 0;
    private static final int SCREEN_RESULT = 1;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK_QUERY);
        showBackButton(false);
        setCaption("鸿阅商城");
        setTitleBarImageResource(R.drawable.ico_shopping_def_xq);
        setOnTitleBarRightImgListener(new onTitleBarRightImgListener() {
            @Override
            public void onTitleBarRightImgListener() {
                openActivity(ShoppingTrolleyActivity.class);
            }
        });

        unChecked_color = ContextCompat.getColor(this, R.color.textColor_scarch);
        checked_color = ContextCompat.getColor(this, R.color.main_update_color);

        tv_course.setTextColor(checked_color);

        initRecycler();
    }

    private void initRecycler() {
        mInitRecyclerView(rv_mall, 2, 2);

        Map<String, String> map = new HashMap<>();
        map.put("grade", "");
        map.put("subject", "");
        map.put("category", "1");
//        map.put("orderby", ""); //排序字段：id   grade_price   sales
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.STORE_PRODUCT_LIST), map, spin_kit, new StoreProductListXCallBack());
    }

    @Event(value = {R.id.tv_course, R.id.tv_testPaper, R.id.tv_readingMaterial, R.id.tv_screen})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Map<String, String> map = null;
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_course:    //教程
                clearColor();
                tv_course.setTextColor(checked_color);
                map = new HashMap<>();
                map.put("grade", "");
                map.put("subject", "");
                map.put("category", "1");
//                map.put("orderby", ""); //排序字段：id   grade_price   sales
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.STORE_PRODUCT_LIST), map, spin_kit, new StoreProductListXCallBack());
                break;
            case R.id.tv_testPaper:    //试卷
                clearColor();
                tv_testPaper.setTextColor(checked_color);
                map = new HashMap<>();
                map.put("grade", "");
                map.put("subject", "");
                map.put("category", "4");
//                map.put("orderby", ""); //排序字段：id   grade_price   sales
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.STORE_PRODUCT_LIST), map, spin_kit, new StoreProductListXCallBack());
                break;
            case R.id.tv_readingMaterial:    //读物
                clearColor();
                tv_readingMaterial.setTextColor(checked_color);
                map = new HashMap<>();
                map.put("grade", "");
                map.put("subject", "");
                map.put("category", "5");
//                map.put("orderby", ""); //排序字段：id   grade_price   sales
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.STORE_PRODUCT_LIST), map, spin_kit, new StoreProductListXCallBack());
                break;
            case R.id.tv_screen:    //筛选
                intent = new Intent(this, ScreenActivity.class);
                startActivityForResult(intent, SCREEN_RESULT);
                break;
        }
    }

    /**
     * 筛选
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                String grade_id = data.getStringExtra("grade_id");
                String subject_id = data.getStringExtra("subject_id");
                String category_id = data.getStringExtra("category_id");

                Map<String, String> map = new HashMap<>();
                map.put("grade", grade_id);
                map.put("subject", subject_id);
                map.put("category", category_id);
//                map.put("orderby", ""); //排序字段：id   grade_price   sales
                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.STORE_PRODUCT_LIST), map, spin_kit, new StoreProductListXCallBack());
                break;
        }
    }

    private void clearColor() {
        tv_course.setTextColor(unChecked_color);
        tv_testPaper.setTextColor(unChecked_color);
        tv_readingMaterial.setTextColor(unChecked_color);
    }

    /**
     * 商城列表
     */
    private class StoreProductListXCallBack implements HttpHelper.XCallBack {
        private List<StoreProductInfo> list = null;

        @Override
        public void onResponse(String result) {
            try {
                String data = getHttpResultList(result);
                list = JSON.parseArray(data, StoreProductInfo.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
            }
            if (list != null) {
                MallAdapter mallAdapter = new MallAdapter(list, MallActivity.this);
                rv_mall.setAdapter(mallAdapter);
                mallAdapter.setOnItemClickListener(new MallAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        Intent intent = new Intent();
                        intent.putExtra("order_id", list.get(position).getId());
                        openActivity(intent,MallDetailActivity.class);
                    }
                });
            }
        }
    }
}
