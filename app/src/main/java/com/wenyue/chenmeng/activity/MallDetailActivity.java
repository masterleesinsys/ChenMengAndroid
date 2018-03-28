package com.wenyue.chenmeng.activity;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.MallDetailInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

/**
 * 商品详情
 */
@SuppressWarnings("ALL")
@ContentView(R.layout.activity_mall_detail)
public class MallDetailActivity extends BaseActivity {
    @ViewInject(R.id.tv_number)
    private TextView tv_number; //轮播图数量
    @ViewInject(R.id.sv_mall_detail)
    private ScrollView sv_mall_detail;

    @ViewInject(R.id.tv_introduce)
    private TextView tv_introduce;  //详细介绍
    @ViewInject(R.id.tv_price)
    private TextView tv_price;  //折扣价
    @ViewInject(R.id.tv_originalCost)
    private TextView tv_originalCost;  //原价
    @ViewInject(R.id.tv_title)
    private TextView tv_title;  //标题
    @ViewInject(R.id.tv_detail)
    private TextView tv_detail;  //介绍
    @ViewInject(R.id.ban_mall)
    private ConvenientBanner ban_mall;

    @ViewInject(R.id.iv_collect)
    private ImageView iv_collect;
    @ViewInject(R.id.tv_collect)
    private TextView tv_collect;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    private String order_id = "";   //订单id
    private Boolean collected = false;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("商品详情");

        tv_number.setBackgroundDrawable(mDrawableStyle(R.color.mall_number, 10));
        OverScrollDecoratorHelper.setUpOverScroll(sv_mall_detail);

        if (getIntent().hasExtra("order_id")) {
            order_id = getIntent().getStringExtra("order_id");

            String resval = String.format(Constants.STORE_PRODUCT, order_id);
            HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), null, spin_kit, new StoreProductXCallBack());
        }
    }

    @Event(value = {R.id.ll_shoppingTrolley, R.id.tv_purchase, R.id.tv_addToShoppingCart, R.id.ll_collect})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        if ("".equals(order_id)) {
            showToastText("未查询到该商品信息");
            return;
        }
        switch (view.getId()) {
            case R.id.ll_collect:   //收藏
                if (collected) {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", order_id);
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
                                new AlertView("取消收藏", "取消收藏成功", null, new String[]{"知道了"}, null, MallDetailActivity.this, AlertView.Style.Alert, null).setOnDismissListener(new OnDismissListener() {
                                    @Override
                                    public void onDismiss(Object o) {
                                        String resval = String.format(Constants.STORE_PRODUCT, order_id);
                                        HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), null, spin_kit, new StoreProductXCallBack());
                                    }
                                }).show();
                            }
                        }
                    });
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("type", "0");
                    map.put("object", order_id);
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
                                new AlertView("添加收藏", "添加收藏成功", null, new String[]{"知道了"}, null, MallDetailActivity.this, AlertView.Style.Alert, null).setOnDismissListener(new OnDismissListener() {
                                    @Override
                                    public void onDismiss(Object o) {
                                        String resval = String.format(Constants.STORE_PRODUCT, order_id);
                                        HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), null, spin_kit, new StoreProductXCallBack());
                                    }
                                }).show();
                            }
                        }
                    });
                }
                break;
            case R.id.ll_shoppingTrolley:   //购物车
                openActivity(ShoppingTrolleyActivity.class);
                break;
            case R.id.tv_purchase:  //立即购买
                openActivity(OrdersForGoodsActivity.class);
                break;
            case R.id.tv_addToShoppingCart: //加入购物车
                Map<String, Object> map = new HashMap<>();
                map.put("quantily", 1);
                map.put("product_id", order_id);
                HttpHelper.getInstance().post(MyApplication.getTokenURL(Constants.STORE_CART_ADD), map, spin_kit, new HttpHelper.XCallBack() {
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
                            new AlertView("加入购物车", "加入购物车成功", null, new String[]{"知道了"}, null, MallDetailActivity.this, AlertView.Style.Alert, null).show();
                        }
                    }
                });
                break;
        }
    }

    /**
     * 商品详情
     */
    private class StoreProductXCallBack implements HttpHelper.XCallBack {
        MallDetailInfo mallDetailInfo = null;

        @Override
        public void onResponse(String result) {
            try {
                mallDetailInfo = getHttpResult(result, MallDetailInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }
            if (null != mallDetailInfo) {
                collected = mallDetailInfo.isCollected();

                if (collected) {
                    iv_collect.setImageResource(R.drawable.ico_collected_xq);
                    tv_collect.setText("已收藏");
                } else {
                    iv_collect.setImageResource(R.drawable.ico_collection_xq);
                    tv_collect.setText("收藏");
                }

                tv_introduce.setText(Html.fromHtml(mallDetailInfo.getDescription()));
                tv_price.setText("￥" + mallDetailInfo.getDiscount_price());
                tv_originalCost.setText("原价：￥" + mallDetailInfo.getGuide_price());
                tv_title.setText(mallDetailInfo.getName());
                tv_detail.setText(mallDetailInfo.getCategory_text());

                tv_number.setText("1/" + mallDetailInfo.getImages().size());

                ban_mall.setCanLoop(false); //不可循环
//                ban_mall.setPageIndicator(new int[]{R.drawable.ico_h_yuandian, R.drawable.ico_b_yuandian});
//                ban_mall.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

                final List<String> images = new ArrayList<>();
                images.addAll(mallDetailInfo.getImages());

                ban_mall.setPages(new CBViewHolderCreator() {
                    @Override
                    public Object createHolder() {
                        return new MyBannerHolder();
                    }
                }, images);
                ban_mall.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        tv_number.setText((position + 1) + "/" + mallDetailInfo.getImages().size());
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        }
    }

    private class MyBannerHolder implements Holder<String> {
        private ImageView iv_image;

        @Override
        public View createView(Context context) {
            iv_image = new ImageView(context);
            iv_image.setScaleType(ImageView.ScaleType.FIT_XY);
            return iv_image;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            HttpHelper.getInstance().mLoadPicture(MyApplication.IMAGE_HOST + data, iv_image);
        }
    }
}
