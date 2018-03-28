package com.wenyue.chenmeng.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.CashRegisterInfo;

import java.util.List;

/**
 * 提现
 */
public class CashRegisterAdapter extends RecyclerView.Adapter<CashRegisterAdapter.MyViewHolder> {
    private List<CashRegisterInfo> list;
    private Context context;

    public CashRegisterAdapter(List<CashRegisterInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(List<CashRegisterInfo> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cash_register, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_cash_withdrawal.setText("提现金额￥" + list.get(position).getTotal_fee() + "");
//        switch (list.get(position).getStatus()) {
//            case 0:
//                holder.tv_cash_withdrawal_status.setText("未处理");
//                break;
//            case 1:
//                holder.tv_cash_withdrawal_status.setText("提现中");
//                break;
//            case 2:
//                holder.tv_cash_withdrawal_status.setText("已完成");
//                break;
//            case 3:
//                holder.tv_cash_withdrawal_status.setText("成功");
//                break;
//        }
        holder.tv_cash_withdrawal_status.setBackgroundDrawable(setStatusStytle(R.color.main_update_color));

//        if (list.get(position).getStatus() == 3) {
        holder.tv_withdrawals_surplus.setText("剩余￥" + list.get(position).getBalance_before() + "元");
//        } else {
//            holder.tv_withdrawals_surplus.setText("剩余￥" + list.get(position).getBalance_after() + "元");
//        }
        holder.tv_withdrawals_date.setText(list.get(position).getAccount_name());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    /**
     * 设置状态按钮圆角及边框颜色
     *
     * @param status_color 颜色值
     * @return
     */
    private GradientDrawable setStatusStytle(@ColorRes int status_color) {
        int roundRadius = 6; // 3dp 圆角半径
        int fillColor = ContextCompat.getColor(context, status_color);//内部填充颜色
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        return gd;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_cash_withdrawal, tv_cash_withdrawal_status, tv_withdrawals_surplus, tv_withdrawals_date;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_cash_withdrawal = itemView.findViewById(R.id.tv_cash_withdrawal);
            tv_cash_withdrawal_status = itemView.findViewById(R.id.tv_cash_withdrawal_status);
            tv_withdrawals_surplus = itemView.findViewById(R.id.tv_withdrawals_surplus);
            tv_withdrawals_date = itemView.findViewById(R.id.tv_withdrawals_date);
        }
    }
}
