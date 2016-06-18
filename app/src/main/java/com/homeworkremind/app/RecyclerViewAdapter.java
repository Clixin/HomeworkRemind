package com.homeworkremind.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * 自己继承的RecyclerVIew.Adapter，有极高的自定义性
 * Created by Clixin on 2016/6/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


    /**
     * 填充数据
     */
    List<HomeWork> mList;

    /**
     * 构造函数，传递数据
     *
     * @param mList 数据
     */
    public RecyclerViewAdapter(List<HomeWork> mList) {
        this.mList = mList;
    }


    /**
     * 当RecyclerView创建后，自动创建其ViewHolder（需增加重写）
     *
     * @param parent   父容器
     * @param viewType
     * @return 动态加载自己的ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_base_swipe_item, parent, false);
        return new ViewHolder(v);
    }

    /**
     * ViewHolder与Adapter绑定，这里执行具体逻辑，该显示什么
     *
     * @param holder   自己的ViewHolder
     * @param position 当前游标位置
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cDeadlineText.setText(mList.get(position).getDeadline());
        holder.cCourseNameText.setText(mList.get(position).getCourse());
        holder.cHandOnText.setText(mList.get(position).getWayOfHandOn());
        holder.cHomeworkContentText.setText(mList.get(position).getContent());

    }

    /**
     * @return 返回有多少数据
     */
    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 内部类
     * 自己继承的ViewHolder，用来缓存View，以及节约资源
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView cDeadline;
        TextView cDeadlineText;
        TextView cCourseName;
        TextView cCourseNameText;
        TextView cHandOn;
        TextView cHandOnText;
        TextView cHomeworkContent;
        TextView cHomeworkContentText;


        public ViewHolder(View itemView) {
            super(itemView);
            cDeadline = (TextView) itemView.findViewById(R.id.card_deadline);
            cDeadlineText = (TextView) itemView.findViewById(R.id.text_card_deadline);
            cCourseName = (TextView) itemView.findViewById(R.id.course_name);
            cCourseNameText = (TextView) itemView.findViewById(R.id.text_course_name);
            cHandOn = (TextView) itemView.findViewById(R.id.hand_on);
            cHandOnText = (TextView) itemView.findViewById(R.id.text_hand_on);
            cHomeworkContent = (TextView) itemView.findViewById(R.id.homework_content);
            cHomeworkContentText = (TextView) itemView.findViewById(R.id.text_homework_content);
        }
    }
}