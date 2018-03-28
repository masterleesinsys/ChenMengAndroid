package com.wenyue.chenmeng.adapter;

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
import com.wenyue.chenmeng.entity.ShippingAddressInfo;

import java.util.List;


/**
 * 收货地址列表
 */
@SuppressWarnings("ALL")
public class ShippingAddressAdapter extends RecyclerView.Adapter<ShippingAddressAdapter.MyViewHolder> {
    private List<ShippingAddressInfo> list;
    private Context context;
    private OnItemCompileClickListener onItemCompileClickListener;
    private OnItemDeleteClickListener onItemDeleteClickListener;

    public ShippingAddressAdapter(List<ShippingAddressInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shipping_address, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_compile.setBackgroundDrawable(setStatusStytle(R.color.main_update_color));
        holder.tv_delete.setBackgroundDrawable(setStatusStytle(R.color.main_update_color));

        holder.tv_consignee.setText(list.get(position).getName());
        holder.tv_phone.setText(list.get(position).getMobile());
        holder.tv_shippingAddress.setText(list.get(position).getAddress());

        holder.tv_compile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemCompileClickListener.onItemClickListener(position, list.get(position).getId(),list.get(position).getName(),list.get(position).getMobile(),list.get(position).getAddress());
            }
        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemDeleteClickListener.onItemClickListener(position, list.get(position).getId());
            }
        });
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
        private TextView tv_consignee, tv_phone, tv_compile, tv_shippingAddress, tv_delete;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_consignee = itemView.findViewById(R.id.tv_consignee);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_compile = itemView.findViewById(R.id.tv_compile);
            tv_shippingAddress = itemView.findViewById(R.id.tv_shippingAddress);
            tv_delete = itemView.findViewById(R.id.tv_delete);
        }
    }

    public interface OnItemCompileClickListener {
        void onItemClickListener(int position, String id, String name, String mobile, String address);
    }

    public void setOnItemCompileClickListener(OnItemCompileClickListener onItemCompileClickListener) {
        this.onItemCompileClickListener = onItemCompileClickListener;
    }

    public interface OnItemDeleteClickListener {
        void onItemClickListener(int position, String id);
    }

    public void setOnItemDeleteClickListener(OnItemDeleteClickListener onItemDeleteClickListener) {
        this.onItemDeleteClickListener = onItemDeleteClickListener;
    }
}
