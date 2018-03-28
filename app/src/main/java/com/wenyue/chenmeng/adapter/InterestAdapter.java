package com.wenyue.chenmeng.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wenyue.chenmeng.R;

import java.util.List;


/**
 * 关注列表
 */
public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.MyViewHolder> {
    private List<String> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public InterestAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public InterestAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_interest, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_attention.setBackgroundDrawable(setStatusStytle(R.color.textColor_title));
    }

    @Override
    public int getItemCount() {
//        return list == null ? 0 : list.size();
        return 50;
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
        private RatingBar rb_score;
        private ImageView iv_headImg;
        private TextView tv_nickname, tv_sex, tv_beanVermicelli, tv_realname, tv_authentication, tv_attention;

        MyViewHolder(View itemView) {
            super(itemView);
            iv_headImg = itemView.findViewById(R.id.iv_headImg);
            tv_nickname = itemView.findViewById(R.id.tv_nickname);
            tv_sex = itemView.findViewById(R.id.tv_sex);
            rb_score = itemView.findViewById(R.id.rb_score);
            tv_beanVermicelli = itemView.findViewById(R.id.tv_beanVermicelli);
            tv_realname = itemView.findViewById(R.id.tv_realname);
            tv_authentication = itemView.findViewById(R.id.tv_authentication);
            tv_attention = itemView.findViewById(R.id.tv_attention);

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
