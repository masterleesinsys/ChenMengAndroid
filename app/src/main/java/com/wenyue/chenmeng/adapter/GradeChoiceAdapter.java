package com.wenyue.chenmeng.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.wenyue.chenmeng.R;
import com.wenyue.chenmeng.entity.GradesList;

import java.util.List;

/**
 * 年级列表(暂弃用)
 */
@SuppressWarnings({"UnusedAssignment", "NumberEquality"})
public class GradeChoiceAdapter extends BaseExpandableListAdapter {
    private List<GradesList> gradeList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public GradeChoiceAdapter(List<GradesList> gradeList, Context context) {
        this.gradeList = gradeList;
        this.context = context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return gradeList.get(groupPosition).getSubList().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = convertView;
        CHildHolder holder = null;
        if (view == null) {
            holder = new CHildHolder();
            view = LayoutInflater.from(context).inflate(R.layout.addorselection_child, null);
            holder.tv_service_classify_name = view.findViewById(R.id.tv_service_classify_name);
            view.setTag(holder);
        } else {
            holder = (CHildHolder) view.getTag();
        }
        holder.tv_service_classify_name.setText(gradeList.get(groupPosition).getSubList().get(childPosition));
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return gradeList.get(groupPosition).getSubList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return gradeList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return gradeList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        GroupHolder holder = null;
        if (view == null) {
            holder = new GroupHolder();
            view = LayoutInflater.from(context).inflate(R.layout.addorselection_group, null);
            holder.tv_service_classify_name = view.findViewById(R.id.tv_service_classify_name);
            view.setTag(holder);
        } else {
            holder = (GroupHolder) view.getTag();
        }
        holder.tv_service_classify_name.setText(gradeList.get(groupPosition).getGradeName());
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public interface OnItemClickListener {
        void onItemClickListener(String name, Integer id, Double price, Integer service_id, String content, CheckBox checkBox);
    }

    private class GroupHolder {
        private TextView tv_service_classify_name;
    }

    private class CHildHolder {
        private TextView tv_service_classify_name;
    }
}
