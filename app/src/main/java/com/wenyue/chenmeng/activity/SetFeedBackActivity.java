package com.wenyue.chenmeng.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.skydoves.elasticviews.ElasticAction;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

/**
 * 建议反馈
 */
@SuppressWarnings("ALL")
@ContentView(R.layout.activity_set_feed)
public class SetFeedBackActivity extends BaseActivity {
    @ViewInject(R.id.et_setfeedback)
    private EditText et_setfeedback;
    @ViewInject(R.id.tv_setfeedback_determine)
    private TextView tv_setfeedback_determine;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("建议反馈");
    }

    @Event(value = {R.id.tv_setfeedback_determine})
    private void onClick(View view) {
        ElasticAction.doAction(view, 400, 0.85f, 0.85f);
        switch (view.getId()) {
            case R.id.tv_setfeedback_determine:
                if (et_setfeedback.getText().toString().trim().equals("")) {
                    showToastText("内容不能为空");
                    return;
                }
                HashMap<String, Object> params = new HashMap<>();
                params.put("type", "1");
                params.put("content", et_setfeedback.getText().toString().trim());

                HttpHelper.getInstance().post(MyApplication.getTokenURL(Constants.FEEDBACK_SUBMIT), params, spin_kit, new onFeedBackSubmitCallback());
                break;

        }
    }

    private class onFeedBackSubmitCallback implements HttpHelper.XCallBack {
        public void onResponse(String result) {
            String user = null;
            try {
                user = getHttpResult(result, String.class);
            } catch (Exception e) {
                LogUtils.e(e.toString());
                return;
            }

            if (user != null) {
                et_setfeedback.setText("");
                showToastText("建议提交成功，感谢您的反馈");
                LogUtils.e("建议提交成功，感谢您的反馈");
                finish();
            } else {
                showToastText("建议提交失败，请联系技术人员");
                LogUtils.e("建议提交失败，请联系技术人员");
                finish();
            }
        }
    }
}
