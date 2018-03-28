package com.wenyue.chenmeng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.JournalismInfo;
import com.wenyue.chenmeng.util.HttpHelper;

import java.util.List;


/**
 * 资讯列表
 */
public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.MyViewHolder> {
    private List<JournalismInfo> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public InformationAdapter(List<JournalismInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_information, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HttpHelper.getInstance().mLoadPicture(MyApplication.IMAGE_HOST + list.get(position).getImage(), holder.iv_information);
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_date.setText(list.get(position).getCreate_time());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_information;
        private TextView tv_title, tv_date;

        MyViewHolder(View itemView) {
            super(itemView);
            iv_information = itemView.findViewById(R.id.iv_information);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);

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
