package com.wenyue.chenmeng.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.CourseInfo;
import com.wenyue.chenmeng.util.HttpHelper;

import java.util.List;


/**
 * 视频列表
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyViewHolder> {
    private List<CourseInfo> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public VideoListAdapter(List<CourseInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recen_updates, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HttpHelper.getInstance().mLoadPicture(MyApplication.IMAGE_HOST + list.get(position).getThumb(), holder.iv_course_experience);
        holder.tv_course.setText(list.get(position).getGrade_text() + " " + list.get(position).getSubject_text() + " (" + list.get(position).getPress_text() + ")");
        holder.tv_update.setText(list.get(position).getPublic_time() + "更新");
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_course_experience;
        private TextView tv_course, tv_update;

        MyViewHolder(View itemView) {
            super(itemView);
            iv_course_experience = itemView.findViewById(R.id.iv_course_experience);
            tv_course = itemView.findViewById(R.id.tv_course);
            tv_update = itemView.findViewById(R.id.tv_update);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClickListener(getAdapterPosition(), String.valueOf(list.get(getAdapterPosition()).getId()));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, String id);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
