package com.wenyue.chenmeng.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenyue.chenmeng.R;

import java.util.List;


/**
 * 订单动态列表
 */
public class OrderDynamicAdapter extends RecyclerView.Adapter<OrderDynamicAdapter.MyViewHolder> {
    private List<String> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public OrderDynamicAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public OrderDynamicAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_dynamic, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.rv_commodity.setLayoutManager(new LinearLayoutManager(context));
        OrderDynamicsAdapter orderDynamicsAdapter = new OrderDynamicsAdapter(context);
        holder.rv_commodity.setAdapter(orderDynamicsAdapter);
        orderDynamicsAdapter.setOnItemClickListener(new OrderDynamicsAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }
        });
    }

    @Override
    public int getItemCount() {
//        return list == null ? 0 : list.size();
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_order_number, tv_time, tv_status, tv_show;
        private RecyclerView rv_commodity;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_order_number = itemView.findViewById(R.id.tv_order_number);
            tv_time = itemView.findViewById(R.id.tv_time);
            rv_commodity = itemView.findViewById(R.id.rv_commodity);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_show = itemView.findViewById(R.id.tv_show);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClickListener(getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
