package com.wenyue.chenmeng.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.UpGeadeVipList;

import java.util.List;


/**
 * 升级VIP&购买
 */
public class UpgradeVipAdapter extends RecyclerView.Adapter<UpgradeVipAdapter.MyViewHolder> {
    private List<UpGeadeVipList> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private int mSelectedPos = -1;  //保存当前选中的position

    public UpgradeVipAdapter(List<UpGeadeVipList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_upgrade, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position, List<Object> payloads) {
        if (payloads.isEmpty()) {    //payloads即有效负载，当首次加载或调用notifyDatasetChanged() ,notifyItemChange(int position)进行刷新时，payloads为empty 即空
            holder.tv_purchase.setTextColor(ContextCompat.getColor(context, R.color.main_update_color));
            holder.tv_purchase.setBackgroundResource(R.drawable.purchase);
        } else {    //当调用notifyItemChange(int position, Object payload)进行布局刷新时，payloads不会empty ，所以真正的布局刷新应该在这里实现
            holder.tv_purchase.setTextColor(ContextCompat.getColor(context, R.color.main_update_color));
            holder.tv_purchase.setBackgroundResource(R.drawable.purchase);
        }

        holder.iv_bg.setBackgroundResource(list.get(position).getImage());
        holder.tv_vip.setText(list.get(position).getVip());
        holder.tv_two.setText(list.get(position).getTwo());
        holder.tv_three.setText(list.get(position).getThree());
        holder.tv_price.setText(list.get(position).getPrice());

        holder.tv_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedPos != position) {     //当前选中的position和上次选中不是同一个position 执行
                    holder.tv_purchase.setTextColor(ContextCompat.getColor(context, R.color.white));
                    holder.tv_purchase.setBackgroundResource(R.drawable.purchase_checked);
                    if (mSelectedPos != -1) {       //判断是否有效 -1是初始值 即无效 第二个参数是Object 随便传个int 这里只是起个标志位
                        notifyItemChanged(mSelectedPos, 0);
                    }
                    mSelectedPos = position;
                }
                onItemClickListener.onItemClickListener(position);
            }
        });
    }

    public void updateData(List<UpGeadeVipList> airPortModels) {
        if (null != list) {
            mSelectedPos = -5;
            list = null;
            list = airPortModels;
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_bg;
        private TextView tv_vip, tv_one, tv_two, tv_three, tv_price, tv_purchase;

        MyViewHolder(View itemView) {
            super(itemView);
            iv_bg = itemView.findViewById(R.id.iv_bg);
            tv_vip = itemView.findViewById(R.id.tv_vip);
            tv_one = itemView.findViewById(R.id.tv_one);
            tv_two = itemView.findViewById(R.id.tv_two);
            tv_three = itemView.findViewById(R.id.tv_three);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_purchase = itemView.findViewById(R.id.tv_purchase);

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
