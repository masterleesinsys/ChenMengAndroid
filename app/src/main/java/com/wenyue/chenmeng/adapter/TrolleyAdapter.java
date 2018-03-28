package com.wenyue.chenmeng.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.wenyue.chenmeng.MyApplication;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.TrolleyInfo;
import com.wenyue.chenmeng.util.HttpHelper;

import java.util.List;

/**
 * 购物车列表
 */
public class TrolleyAdapter extends RecyclerView.Adapter<TrolleyAdapter.MyViewHolder> {
    private List<TrolleyInfo> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private int mSelectedPos = -1;  //保存当前选中的position

    public TrolleyAdapter(List<TrolleyInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trolley, parent, false);
        return new MyViewHolder(view);
    }

    public void updateData(List<TrolleyInfo> airPortModels) {
        if (null != list) {
            mSelectedPos = -5;
            list = null;
            list = airPortModels;
            notifyDataSetChanged();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        HttpHelper.getInstance().mLoadPicture(MyApplication.IMAGE_HOST + list.get(position).getProduct_image(), holder.iv_store);
        holder.tv_title.setText(list.get(position).getProduct_text());
        holder.tv_number.setText("X"+list.get(position).getQuantily());
        holder.tv_price.setText("￥" + list.get(position).getPrice());

//        holder.itemView.setSelected(mSelectedPos == position);
//        if (mSelectedPos == position) {
//            holder.cb_trolley.setChecked(true);
//        } else {
//            holder.cb_trolley.setChecked(false);
//        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickListener.onItemClickListener(holder.getAdapterPosition());
//                mSelectedPos = position; //选择的position赋值给参数，
//                notifyItemChanged(mSelectedPos);//刷新当前点击item
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cb_trolley;
        private ImageView iv_store;
        private TextView tv_title, tv_number, tv_price;

        MyViewHolder(View itemView) {
            super(itemView);
            iv_store = itemView.findViewById(R.id.iv_store);
            cb_trolley = itemView.findViewById(R.id.cb_trolley);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_number = itemView.findViewById(R.id.tv_number);
            tv_price = itemView.findViewById(R.id.tv_price);

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
