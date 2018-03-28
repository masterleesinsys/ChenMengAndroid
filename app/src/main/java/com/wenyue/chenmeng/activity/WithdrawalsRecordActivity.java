package com.wenyue.chenmeng.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.github.ybq.android.spinkit.SpinKitView;
import com.wenyue.chenmeng.Constants;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.adapter.CashRegisterAdapter;
import com.wenyue.chenmeng.entity.CashRegisterInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 提现记录
 */
@ContentView(R.layout.activity_withdrawals_record)
public class WithdrawalsRecordActivity extends BaseActivity {
    @ViewInject(R.id.rv_withdrawals_record)
    private RecyclerView rv_withdrawals_record;

    @ViewInject(R.id.spin_kit)
    private SpinKitView spin_kit;
    @ViewInject(R.id.not_info)
    private View not_info;

    @Override
    protected void initView() {
        setStyle(STYLE_BACK);
        setCaption("提现记录");

        mInitRecyclerView(rv_withdrawals_record, 2);
        HttpHelper.getInstance().get(MyApplication.getTokenURL(Constants.FINANCE_DRAWING_LIST), null, spin_kit, new onMemberWithdrawCashListXCallBack());
    }

    /**
     * 提现记录
     */
    private class onMemberWithdrawCashListXCallBack implements HttpHelper.XCallBack {
        private List<CashRegisterInfo> cashRegisterInfoList = null;

        @Override
        public void onResponse(String result) {
            try {
                String json = getHttpResult(result, String.class);
                cashRegisterInfoList = JSON.parseArray(json, CashRegisterInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
                showToastText(e.toString());
                LogUtils.e(e.toString());
            }
            if (null != cashRegisterInfoList && cashRegisterInfoList.size() > 0) {
                not_info.setVisibility(View.GONE);
                CashRegisterAdapter cashRegisterAdapter = new CashRegisterAdapter(cashRegisterInfoList, WithdrawalsRecordActivity.this);
                rv_withdrawals_record.setAdapter(cashRegisterAdapter);
            } else {
                not_info.setVisibility(View.VISIBLE);
            }
        }
    }
}
