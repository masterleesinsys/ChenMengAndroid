package com.wenyue.chenmeng.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.StoreProductInfo;
import com.wenyue.chenmeng.util.HttpHelper;
import com.wenyue.chenmeng.util.LogUtils;

import java.util.List;


/**
 * 商城
 */
public class MallAdapter extends RecyclerView.Adapter<MallAdapter.MyViewHolder> {
    private List<StoreProductInfo> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public MallAdapter(List<StoreProductInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mall, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            //添加删除线
            holder.tv_originalCost.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            holder.tv_explain.setText(list.get(position).getName());
            holder.tv_price.setText("￥" + list.get(position).getGuide_price());
            holder.tv_originalCost.setText("￥" + list.get(position).getGuide_price());
            HttpHelper.getInstance().mLoadPicture(MyApplication.IMAGE_HOST + (list.get(position).getImages() == null ? "" : list.get(position).getImages().get(0)), holder.iv_photo);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_photo;
        private TextView tv_explain, tv_price, tv_originalCost;

        MyViewHolder(View itemView) {
            super(itemView);
            iv_photo = itemView.findViewById(R.id.iv_photo);
            tv_explain = itemView.findViewById(R.id.tv_explain);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_originalCost = itemView.findViewById(R.id.tv_originalCost);

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
