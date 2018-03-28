package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.github.ybq.android.spinkit.SpinKitView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.MainAdapter;
import com.wenyue.chenmeng.entity.AdvertInfo;
import com.wenyue.chenmeng.entity.VideoHomeInfo;
import com.wenyue.chenmeng.service.VerUpdateService;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;
import com.wenyue.chenmeng.util.StringUtils;
import com.wenyue.chenmeng.util.Tools;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 视频
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.xrv_main)
    private XRecyclerView xrv_main;
    @ViewInject(R.id.srl_main)
    private SwipeRefreshLayout srl_main;    //下拉刷新

    private LinearLayout ll_scarch; //搜索
    private RelativeLayout rl_message; //消息
    private ConvenientBanner ban_main; //轮播
    private RelativeLayout rl_course;   //课程
    private RelativeLayout rl_topTeacher;   //名师
    private RelativeLayout rl_answerQuestions;   //答疑
    private RelativeLayout rl_playback;   //回放
    private RelativeLayout rl_boutique;   //精品

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @Override
    protected void initView() {
        initRefresh();
        initRecycler();

        //终端访问记录
//        postVisit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.ADVERT_LIST) + "&loc=1", null, spin_kit, new onAdvertList());

        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.VIDEO_HOME), null, spin_kit, new VideoHomeXCallBack());

//        versionUpdating();

        ban_main.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        ban_main.stopTurning();
    }

    private void initRecycler() {
        mInitRecyclerView(xrv_main, 0);
        xrv_main.setLoadingMoreEnabled(false);
        xrv_main.setPullRefreshEnabled(false);
        View view = View.inflate(this, R.layout.head_main, null);
        xrv_main.addHeaderView(view);

        ll_scarch = view.findViewById(R.id.ll_scarch);
        rl_message = view.findViewById(R.id.rl_message);
        ban_main = view.findViewById(R.id.ban_main);
        rl_course = view.findViewById(R.id.rl_course);
        rl_topTeacher = view.findViewById(R.id.rl_topTeacher);
        rl_answerQuestions = view.findViewById(R.id.rl_answerQuestions);
        rl_playback = view.findViewById(R.id.rl_playback);
        rl_boutique = view.findViewById(R.id.rl_boutique);

        ll_scarch.setOnClickListener(this);
        rl_course.setOnClickListener(this);
        rl_topTeacher.setOnClickListener(this);
        rl_answerQuestions.setOnClickListener(this);
        rl_playback.setOnClickListener(this);
        rl_boutique.setOnClickListener(this);
        rl_message.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.ll_scarch:    //搜索
                openActivity(SearchActivity.class);
                break;
            case R.id.rl_message:   //消息
                openActivity(MessageActivity.class);
                break;
            case R.id.rl_course:    //课程
                intent = new Intent();
                intent.putExtra("title", "课程同步");
                intent.putExtra("type", "sync");
                openActivity(intent, VideoListActivity.class);
                break;
            case R.id.rl_topTeacher:    //名师
                intent = new Intent();
                intent.putExtra("title", "名师培优");
                intent.putExtra("type", "teach");
                openActivity(intent, VideoList2Activity.class);
                break;
            case R.id.rl_answerQuestions:    //答疑
                intent = new Intent();
                intent.putExtra("title", "互动答疑");
                intent.putExtra("type", "ask");
                openActivity(intent, VideoList2Activity.class);
                break;
            case R.id.rl_playback:    //回放
                intent = new Intent();
                intent.putExtra("title", "课程回放");
                intent.putExtra("type", "replay");
                openActivity(intent, VideoListActivity.class);
                break;
            case R.id.rl_boutique:    //精品
                intent = new Intent();
                intent.putExtra("title", "精品课程");
                intent.putExtra("type", "featured");
                openActivity(intent, VideoListActivity.class);
                break;
        }
    }

    /**
     * 首页视频列表
     */
    private class VideoHomeXCallBack implements HttpHelper.XCallBack {
        private VideoHomeInfo videoHomeInfo = null;

        @Override
        public void onResponse(String result) {
            try {
                videoHomeInfo = getHttpResult(result, VideoHomeInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }
            if (null != videoHomeInfo) {
                MainAdapter mainAdapter = new MainAdapter(videoHomeInfo, MainActivity.this);
                xrv_main.setAdapter(mainAdapter);
            }
        }
    }

    /**
     * 广告
     */
    @SuppressWarnings("unchecked")
    private class onAdvertList implements HttpHelper.XCallBack {
        private List<AdvertInfo> list = null;

        @Override
        public void onResponse(String result) {
            try {
                String json = getHttpResultList(result);
                list = JSON.parseArray(json, AdvertInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }

            if (list != null) {
                final List<String> takeTurnLists = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    takeTurnLists.add(list.get(i).getFile());
                }
                //        banner轮播广告
                ban_main.setCanLoop(true);
                ban_main.setPageIndicator(new int[]{R.drawable.ico_h_yuandian, R.drawable.ico_b_yuandian});
                ban_main.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

                final List<String> images = new ArrayList<>();
                images.addAll(takeTurnLists);

                ban_main.setPages(new CBViewHolderCreator() {
                    @Override
                    public Object createHolder() {
                        return new MyBannerHolder();
                    }
                }, images);
                ban_main.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent();
                        try {
                            if (StringUtils.isNumeric(list.get(position).getTarget_url(), true)) {
                                intent.putExtra("is_read", "is_read");
                                intent.putExtra("id", list.get(position).getId());
                                openActivity(intent, NewsDetailActivity.class);
                            } else {
                                intent.putExtra("object", list.get(position).getTarget_url());
                                intent.putExtra("name", list.get(position).getName());
                                openActivity(intent, AdDetailsActivity.class);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showToastText(e.toString());
                            LogUtils.e(e.toString());
                        }
                    }
                });
            }
        }
    }


    private void initRefresh() {
        srl_main.setColorSchemeColors(getResources().getColor(R.color.main_update_color));
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        srl_main.setDistanceToTriggerSync(600);
        // 设定下拉圆圈的背景
        srl_main.setProgressBackgroundColorSchemeColor(Color.WHITE);
        // 设置圆圈的大小
        srl_main.setSize(SwipeRefreshLayout.DEFAULT);

        //设置下拉刷新的监听
        srl_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                @SuppressLint("DefaultLocale") String resval = String.format(Constants.ADVERT_LIST, "?loc=1");
                HttpHelper.getInstance().get(MyApplication.getTokenURL(resval), null, spin_kit, new onAdvertList());

                HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.VIDEO_HOME), null, spin_kit, new VideoHomeXCallBack());

//                versionUpdating();

                srl_main.setRefreshing(false);
            }
        });
    }

    //判断版本更新
    private void versionUpdating() {
        //只有requsetUrl service 是必须值 其他参数都有默认值，可选
        VersionParams.Builder builder = new VersionParams.Builder()
//                .setHttpHeaders(httpHeaders)
//                .setRequestMethod(requestMethod)
//                .setRequestParams(httpParams)

                .setRequestUrl(MyApplication.getURL(Constants.VERSION_APP))
                .setService(VerUpdateService.class);

        stopService(new Intent(this, VerUpdateService.class));
        builder.setPauseRequestTime(0L);
        builder.setDownloadAPKPath(MyApplication.ROOT_PATH);
        AllenChecker.startVersionCheck(this, builder.build());
    }


    private void postVisit() {
        // �?��统计日志
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        String resolution = width + "*" + height;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        System.out.println(df.format(MyApplication.getStartTime()));

        HashMap<String, Object> params = new HashMap<>();
        params.put("platform", "android");
        params.put("device_UID", Tools.getMacAddress(mContext));
        params.put("start_time", df.format(MyApplication.getStartTime()));
        params.put("network_type", Tools.getNetWorkType(Tools.checkNet(mContext)));
        params.put("ip", Tools.getLocalIpAddress(mContext));
        params.put("device_machine", android.os.Build.MODEL);
        params.put("resolution", resolution);
        params.put("os_version", android.os.Build.VERSION.RELEASE);
        params.put("app_version", Tools.getVersionName(mContext));
        params.put("end_time", df.format(new Date()));
        params.put("app", "0");
        HttpHelper.getInstance().post(MyApplication.getTokenURL(Constants.SYSTEM_VISIT), params, new onVistiSubmitCallback());
    }

    private class onVistiSubmitCallback implements HttpHelper.XCallBack {
        public void onResponse(String result) {
            String user = null;
            try {
                user = getHttpResult(result, String.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                return;
            }

            if (user != null) {
                LogUtils.d("终端访问日志: 访问日志提交成功");
            } else {
                LogUtils.d("终端访问日志: 访问日志提交失败");
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
