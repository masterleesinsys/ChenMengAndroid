package com.wenyue.chenmeng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenyue.chenmeng.R;

import java.util.List;


/**
 * 我的收藏  商品
 */
public class CollectCommodityAdapter extends RecyclerView.Adapter<CollectCommodityAdapter.MyViewHolder> {
    private List<String> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public CollectCommodityAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public CollectCommodityAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
//        return list == null ? 0 : list.size();
        return 50;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date, tv_msg, tv_time, tv_content, tv_detail;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_msg = itemView.findViewById(R.id.tv_msg);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_detail = itemView.findViewById(R.id.tv_detail);

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
