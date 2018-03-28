package com.wenyue.chenmeng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenyue.chenmeng.R;

import java.util.List;


/**
 * 商品列表
 */
public class OrderDynamicsAdapter extends RecyclerView.Adapter<OrderDynamicsAdapter.MyViewHolder> {
    private List<String> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public OrderDynamicsAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public OrderDynamicsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_dynamics, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
//        return list == null ? 0 : list.size();
        return 2;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_image;
        private TextView tv_title, tv_content, tv_price, tv_originalCost,tv_number;

        MyViewHolder(View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_originalCost = itemView.findViewById(R.id.tv_originalCost);
            tv_number = itemView.findViewById(R.id.tv_number);

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
