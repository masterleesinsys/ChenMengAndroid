package com.wenyue.chenmeng.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.activity.VideoDetailActivity;
import com.wenyue.chenmeng.activity.VideoListActivity;
import com.wenyue.chenmeng.entity.VideoHomeInfo;


/**
 * 首页视频列表
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private VideoHomeInfo videoHomeInfo;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public MainAdapter(VideoHomeInfo videoHomeInfo, Context context) {
        this.videoHomeInfo = videoHomeInfo;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.rv_courseExperience.setNestedScrollingEnabled(false);
        holder.rv_courseExperience.setLayoutManager(new LinearLayoutManager(context));
        CourseExperienceAdapter courseExperienceAdapter = new CourseExperienceAdapter(videoHomeInfo.getPreview(), context);
        holder.rv_courseExperience.setAdapter(courseExperienceAdapter);
        courseExperienceAdapter.setOnItemClickListener(new CourseExperienceAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra("id", String.valueOf(videoHomeInfo.getPreview().get(position).getId()));
                context.startActivity(intent);
            }
        });

        holder.rv_recentUpdates.setNestedScrollingEnabled(false);
        holder.rv_recentUpdates.setLayoutManager(new GridLayoutManager(context, 2));
        RecenUpdatesAdapter recenUpdatesAdapter = new RecenUpdatesAdapter(videoHomeInfo.getLast_update(), context);
        holder.rv_recentUpdates.setAdapter(recenUpdatesAdapter);
        recenUpdatesAdapter.setOnItemClickListener(new RecenUpdatesAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra("id", String.valueOf(videoHomeInfo.getLast_update().get(position).getId()));
                context.startActivity(intent);
            }
        });

        holder.rv_recommendedCourse.setNestedScrollingEnabled(false);
        holder.rv_recommendedCourse.setLayoutManager(new GridLayoutManager(context, 2));
        RecommendedCourseAdapter recommendedCourseAdapter = new RecommendedCourseAdapter(videoHomeInfo.getRecommend(), context);
        holder.rv_recommendedCourse.setAdapter(recommendedCourseAdapter);
        recommendedCourseAdapter.setOnItemClickListener(new RecommendedCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra("id", String.valueOf(videoHomeInfo.getRecommend().get(position).getId()));
                context.startActivity(intent);
            }
        });

        holder.ll_courseExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoListActivity.class);
                intent.putExtra("title", "课程体验");
                intent.putExtra("type", "preview");
                context.startActivity(intent);
            }
        });

        holder.ll_recentUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoListActivity.class);
                intent.putExtra("title", "最近更新");
                intent.putExtra("type", "preview");
                context.startActivity(intent);
            }
        });

        holder.ll_recommendedCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoListActivity.class);
                intent.putExtra("title", "推荐课程");
                intent.putExtra("type", "preview");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoHomeInfo != null ? 1 : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout ll_courseExperience, ll_recentUpdates, ll_recommendedCourse;
        private RecyclerView rv_courseExperience, rv_recentUpdates, rv_recommendedCourse;

        MyViewHolder(View itemView) {
            super(itemView);
            ll_courseExperience = itemView.findViewById(R.id.ll_courseExperience);
            rv_courseExperience = itemView.findViewById(R.id.rv_courseExperience);
            ll_recentUpdates = itemView.findViewById(R.id.ll_recentUpdates);
            rv_recentUpdates = itemView.findViewById(R.id.rv_recentUpdates);
            ll_recommendedCourse = itemView.findViewById(R.id.ll_recommendedCourse);
            rv_recommendedCourse = itemView.findViewById(R.id.rv_recommendedCourse);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onItemClickListener.onItemClickListener(getAdapterPosition());
//                }
//            });
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
