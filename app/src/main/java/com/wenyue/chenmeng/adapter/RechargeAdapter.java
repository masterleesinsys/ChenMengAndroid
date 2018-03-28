package com.wenyue.chenmeng.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenyue.chenmeng.R;

import java.util.List;

/**
 * 充值金额列表
 */
public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.MyViewHolder> {
    private List<String> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private int mSelectedPos = -1;  //保存当前选中的position
    private int type = 0;

    public RechargeAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subject_choice, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position, List<Object> payloads) {
        holder.tv_subject.setText(list.get(position));

        if (payloads.isEmpty()) {    //payloads即有效负载，当首次加载或调用notifyDatasetChanged() ,notifyItemChange(int position)进行刷新时，payloads为empty 即空
            holder.tv_subject.setBackgroundResource(R.drawable.rb_invite_unchecked);
            holder.tv_subject.setTextColor(ContextCompat.getColor(context, R.color.rb_unchecked));
        } else {    //当调用notifyItemChange(int position, Object payload)进行布局刷新时，payloads不会empty ，所以真正的布局刷新应该在这里实现
            holder.tv_subject.setBackgroundResource(R.drawable.rb_invite_unchecked);
            holder.tv_subject.setTextColor(ContextCompat.getColor(context, R.color.rb_unchecked));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedPos != position) {     //当前选中的position和上次选中不是同一个position 执行
                    holder.tv_subject.setBackgroundResource(R.drawable.rb_invite_selected);
                    holder.tv_subject.setTextColor(ContextCompat.getColor(context, R.color.rb_selected));
                    if (mSelectedPos != -1) {       //判断是否有效 -1是初始值 即无效 第二个参数是Object 随便传个int 这里只是起个标志位
                        notifyItemChanged(mSelectedPos, 0);
                    }
                    mSelectedPos = position;
                }
                onItemClickListener.onItemClickListener(position);
            }
        });
    }

    public void updateData(List<String> airPortModels) {
        if (null != list) {
            mSelectedPos = -5;
            list = null;
            list = airPortModels;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_subject;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_subject = itemView.findViewById(R.id.tv_subject);

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
