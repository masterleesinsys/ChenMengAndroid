package com.wenyue.chenmeng.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.service.CustomVersionDialogActivity;
import com.wenyue.chenmeng.service.VerUpdateService;
import com.wenyue.chenmeng.util.CleanMessageUtils;
import com.wenyue.chenmeng.util.LogUtils;
import com.wenyue.chenmeng.util.Tools;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * 系统设置
 */
@ContentView(R.layout.activity_set)
public class SetActivity extends BaseActivity {
    @ViewInject(R.id.ll_set_clearcache)
    private LinearLayout ll_set_clearcache; //清除缓存
    @ViewInject(R.id.tv_set_clearcache)
    private TextView tv_set_clearcache;
    @ViewInject(R.id.ll_set_suggestedfeedback)
    private LinearLayout ll_set_suggestedfeedback;  //建议反馈
    @ViewInject(R.id.ll_set_versionupdate)
    private LinearLayout ll_set_versionupdate;  //版本更新
    @ViewInject(R.id.tv_set_versionupdate)
    private TextView tv_set_versionupdate;      //版本号
    @ViewInject(R.id.switch_video)
    private Switch switch_video;    //仅在wifi下观看视频

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("系统设置");

        try {
            //版本号
            tv_set_versionupdate.setText("版本号:" + Tools.getVersionName(this) + "");
            //缓存
            File file = new File(MyApplication.ROOT_PATH);
            tv_set_clearcache.setText(CleanMessageUtils.getFormatSize(CleanMessageUtils.getFolderSize(file)));
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(e.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressLint("SetTextI18n")
    @Event(value = {R.id.ll_set_clearcache, R.id.ll_set_suggestedfeedback, R.id.ll_set_versionupdate})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.ll_set_clearcache:    //清除缓存
                if (!tv_set_clearcache.getText().toString().trim().equals("0M")) {
                    Tools.deleteDir(MyApplication.ROOT_PATH);
                    showToastText("应用缓存已全部清除");

                    tv_set_clearcache.setText("0M");
                }
                break;
            case R.id.ll_set_suggestedfeedback:     //建议反馈
                openActivity(SetFeedBackActivity.class);
                break;
            case R.id.ll_set_versionupdate:     //版本更新
                //只有requsetUrl service 是必须值 其他参数都有默认值，可选
                VersionParams.Builder builder = new VersionParams.Builder()
//                .setHttpHeaders(httpHeaders)
//                .setRequestMethod(requestMethod)
//                .setRequestParams(httpParams)
                        .setRequestUrl(MyApplication.getURL(Constants.VERSION_APP))
                        .setService(VerUpdateService.class);

                stopService(new Intent(this, VerUpdateService.class));
                MyApplication.DIALOG_NEW_VER = true;
                builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
                builder.setPauseRequestTime(0L);
                builder.setDownloadAPKPath(MyApplication.ROOT_PATH);
                AllenChecker.startVersionCheck(this, builder.build());
                break;
        }
    }
}
