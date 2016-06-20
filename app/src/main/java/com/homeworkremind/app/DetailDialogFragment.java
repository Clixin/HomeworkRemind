package com.homeworkremind.app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * 显示作业详情对话框
 * Created by Clixin on 2016/6/20.
 */
public class DetailDialogFragment extends DialogFragment implements View.OnClickListener{

    /**
     * 左上角，详情
     */
    TextView detailTextView;
    /**
     * 右上角，课程名
     */
    TextView detailCourse;
    /**
     * 可编辑的作业要求，需要编辑权限
     */
    EditText detailEditContent;
    /**
     * EditText父布局
     */
    TextInputLayout detailEditContentLayout;
    /**
     * 显示提交方式
     */
    TextView detailWayOfHandOn;
    /**
     * 显示日期
     */
    TextView detailDateTextView;
    /**
     * 显示时间
     */
    TextView detailTimeTextView;
    /**
     * 删除按钮
     */
    TextView detailDelete;
    /**
     * 保存按钮
     */
    TextView detailSave;
    /**
     * 取消按钮
     */
    TextView detailCancel;

    /**
     * 通过bundle传过来的数据，记录当前位置，用于删除
     */
    int position;

    /**
     * Fragment生命周期函数，
     *
     * @param inflater 加载布局
     * @param container 父布局
     * @param savedInstanceState 传递的数据
     * @return 返回view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homework_detail, container);
        init(view);
        detailEditContent.setText(getArguments().getString("content"));
        dealTime(getArguments().getString("deadline"));
        detailWayOfHandOn.setText(getArguments().getString("wayofhandon"));
        detailCourse.setText(getArguments().getString("course"));
        position = getArguments().getInt("current_position");

        return view;
    }

    /**
     * 处理时间
     * 由于homework的deadline是一个字符串，所以在这里拆成日期和时间
     * 通过截取后6位固定字符
     * @param s homework的deadline
     */
    private void dealTime(String s) {
        Log.d("Detail", "" + s.length());
        String date = s.substring(0,s.length() - 6);
        String time = s.substring(s.length() - 6);
        detailDateTextView.setText(date);
        detailTimeTextView.setText(time);
    }

    /**
     * 生命周期，在这里定义FragmentDialog的大小，xml里无效，不知道为什么
     */
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * 初始化组件
     * @param view 传入的view
     */
    private void init(View view) {
        if(view == null) {
            return;
        }
        detailTextView = (TextView) view.findViewById(R.id.detail_text_view);
        detailCourse = (TextView) view.findViewById(R.id.detail_course);
        detailEditContentLayout = (TextInputLayout) view.findViewById(R.id.detail_edit_content_layout);
        detailEditContent = detailEditContentLayout.getEditText();
        detailWayOfHandOn = (TextView) view.findViewById(R.id.detail_way_of_hand_on);
        detailDateTextView = (TextView) view.findViewById(R.id.detail_date_text_view);
        detailTimeTextView = (TextView) view.findViewById(R.id.detail_time_text_view);
        detailDelete = (TextView) view.findViewById(R.id.detail_delete);
        detailCancel = (TextView) view.findViewById(R.id.detail_cancel);
        detailSave = (TextView) view.findViewById(R.id.detail_save);
        detailDelete.setOnClickListener(this);
        detailCancel.setOnClickListener(this);
        detailSave.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_delete :
                dismiss();
                Snackbar.make(view, "已删除", Snackbar.LENGTH_LONG).setAction("撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //// TODO: 2016/6/20 撤销操作
                    }
                }).show();

                break;
            case R.id.detail_cancel :
                dismiss();
                break;
            case R.id.detail_save :
                //// TODO: 2016/6/20  保存操作，需要权限
                break;
        }
    }
}
