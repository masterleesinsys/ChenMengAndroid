package com.wenyue.chenmeng.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.GradeInfo;

import java.util.List;


/**
 * 年级&学科列表
 */
public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {
    private List<GradeInfo> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private int mSelectedPos = -1;  //保存当前选中的position
    private int type = 0;   //年级1，学科2

    public SubjectAdapter(List<GradeInfo> list, Context context, int type) {
        this.list = list;
        this.context = context;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grade_subject, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//        holder.tv_item.setText(list.get(position));
//
//        holder.itemView.setSelected(selectedPosition == position);
//        if (selectedPosition == position) {
//            holder.layout.setBackgroundResource(R.color.colorPrimaryDark);
//            holder.tv_item.setTextColor(ContextCompat.getColor(context, R.color.white));
//        } else {
//            holder.layout.setBackgroundResource(R.color.white);
//            holder.tv_item.setTextColor(ContextCompat.getColor(context, R.color.black));
//        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.onItemClickListener(holder.getAdapterPosition());
//                selectedPosition = position; //选择的position赋值给参数，
//                notifyItemChanged(selectedPosition);//刷新当前点击item
//            }
//        });
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position, List<Object> payloads) {
        holder.tv_item.setText(list.get(position).getName());

        if (payloads.isEmpty()) {    //payloads即有效负载，当首次加载或调用notifyDatasetChanged() ,notifyItemChange(int position)进行刷新时，payloads为empty 即空
            holder.layout.setBackgroundResource(R.color.white);
            holder.tv_item.setTextColor(ContextCompat.getColor(context, R.color.textColor_default));
        } else {    //当调用notifyItemChange(int position, Object payload)进行布局刷新时，payloads不会empty ，所以真正的布局刷新应该在这里实现
            holder.layout.setBackgroundResource(R.color.white);
            holder.tv_item.setTextColor(ContextCompat.getColor(context, R.color.textColor_default));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedPos != position) {     //当前选中的position和上次选中不是同一个position 执行
                    holder.layout.setBackgroundResource(R.color.screen_fillColor);
                    holder.tv_item.setTextColor(ContextCompat.getColor(context, R.color.white));
                    if (mSelectedPos != -1) {       //判断是否有效 -1是初始值 即无效 第二个参数是Object 随便传个int 这里只是起个标志位
                        notifyItemChanged(mSelectedPos, 0);
                    }
                    mSelectedPos = position;
                }
                onItemClickListener.onItemClickListener(position, list.get(position).getId());
            }
        });
    }

    public void updateData(List<GradeInfo> airPortModels) {
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
        private TextView tv_item;
        private LinearLayout layout;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
            layout = itemView.findViewById(R.id.layout);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, String id);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
