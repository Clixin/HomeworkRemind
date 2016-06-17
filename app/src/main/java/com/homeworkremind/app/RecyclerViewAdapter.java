package com.homeworkremind.app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Clixin on 2016/6/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    List<HomeWork> mList;

    public RecyclerViewAdapter(List<HomeWork> mList) {
        this.mList = mList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_base_swipe_item, parent,false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cDeadlineText.setText(mList.get(position).getDeadline());
        holder.cCourseNameText.setText(mList.get(position).getCourse());
        holder.cHandOnText.setText(mList.get(position).getWayOfHandOn());
        holder.cHomeworkContentText.setText(mList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView cDeadline;
        TextView cDeadlineText;
        TextView cCourseName;
        TextView cCourseNameText;
        TextView cHandOn;
        TextView cHandOnText;
        TextView cHomeworkContent;
        TextView cHomeworkContentText;


        public ViewHolder (View itemView) {
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
