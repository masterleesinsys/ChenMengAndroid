package com.wenyue.chenmeng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.TeacherInfo;
import com.wenyue.chenmeng.util.HttpHelper;

import java.util.List;


/**
 * 名师导航
 */
public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.MyViewHolder> {
    private List<TeacherInfo> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public TeacherAdapter(List<TeacherInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_teacher, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.rb_teacher_grade.setRating(4.5F);
        holder.tv_teacher_name.setText(list.get(position).getName());
        HttpHelper.getInstance().mLoadPicture(MyApplication.IMAGE_HOST + list.get(position).getPhoto(), holder.iv_teacher_photo);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_teacher_photo;
        private TextView tv_teacher_name;
        private RatingBar rb_teacher_grade;

        MyViewHolder(View itemView) {
            super(itemView);
            iv_teacher_photo = itemView.findViewById(R.id.iv_teacher_photo);
            tv_teacher_name = itemView.findViewById(R.id.tv_teacher_name);
            rb_teacher_grade = itemView.findViewById(R.id.rb_teacher_grade);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClickListener(getAdapterPosition(), list.get(getAdapterPosition()).getTeacher_id());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, String teacher_id);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
