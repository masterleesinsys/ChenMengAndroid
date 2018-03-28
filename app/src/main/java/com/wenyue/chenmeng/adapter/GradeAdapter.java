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
import com.wenyue.chenmeng.entity.GradeInfo;

import java.util.List;


/**
 * 年级列表
 */
public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.MyViewHolder> {
    private List<GradeInfo> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private int mSelectedPos = -1;  //保存当前选中的position

    public GradeAdapter(List<GradeInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grade, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position, List<Object> payloads) {
        if (payloads.isEmpty()) {    //payloads即有效负载，当首次加载或调用notifyDatasetChanged() ,notifyItemChange(int position)进行刷新时，payloads为empty 即空
            holder.view.setVisibility(View.GONE);
            holder.tv_grade.setTextColor(ContextCompat.getColor(context, R.color.textColor_scarch));
            holder.tv_grade.setText(list.get(position).getName());
        } else {    //当调用notifyItemChange(int position, Object payload)进行布局刷新时，payloads不会empty ，所以真正的布局刷新应该在这里实现
            holder.view.setVisibility(View.GONE);
            holder.tv_grade.setTextColor(ContextCompat.getColor(context, R.color.textColor_scarch));
            holder.tv_grade.setText(list.get(position).getName());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedPos != position) {     //当前选中的position和上次选中不是同一个position 执行
                    holder.view.setVisibility(View.VISIBLE);
                    holder.tv_grade.setTextColor(ContextCompat.getColor(context, R.color.main_update_color));
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
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_grade;
        private View view;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_grade = itemView.findViewById(R.id.tv_grade);
            view = itemView.findViewById(R.id.view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClickListener(getAdapterPosition(), list.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, String grade_id);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
