package com.homeworkremind.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * 显示作业详情对话框
 * Created by Clixin on 2016/6/20.
 */
public class DetailDialogFragment extends DialogFragment implements View.OnClickListener {

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
     * 父布局，用来是EditText失去焦点
     */
    LinearLayout detailLinearLayout;

    /**
     * 是否修改，判断是否保存
     */
    boolean isDataChanged = false;

    /**
     * 通过bundle传过来的数据，记录当前位置，用于删除
     */
    int position;

    /**
     * 构造一个可以传递数据
     * @param bundle 传递的数据
     * @return 返回一个实例
     */
    public static DetailDialogFragment getInstance(Bundle bundle) {
        DetailDialogFragment detailDialogFragment = new DetailDialogFragment();
        detailDialogFragment.setArguments(bundle);
        return detailDialogFragment;
    }


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
        if (view == null) {
            return;
        }
        detailLinearLayout = (LinearLayout) view.findViewById(R.id.detail_linear_layout);
        detailCourse = (TextView) view.findViewById(R.id.detail_course);
        detailEditContentLayout = (TextInputLayout) view.findViewById(R.id.detail_edit_content_layout);
        detailEditContent = detailEditContentLayout.getEditText();
        detailWayOfHandOn = (TextView) view.findViewById(R.id.detail_way_of_hand_on);
        detailDateTextView = (TextView) view.findViewById(R.id.detail_date_text_view);
        detailTimeTextView = (TextView) view.findViewById(R.id.detail_time_text_view);
        detailDelete = (TextView) view.findViewById(R.id.detail_delete);
        detailCancel = (TextView) view.findViewById(R.id.detail_cancel);
        detailSave = (TextView) view.findViewById(R.id.detail_save);
        detailEditContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dataChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        detailDateTextView.setOnClickListener(this);
        detailTimeTextView.setOnClickListener(this);
        detailWayOfHandOn.setOnClickListener(this);
        detailDelete.setOnClickListener(this);
        detailCancel.setOnClickListener(this);
        detailSave.setOnClickListener(this);
    }

    /**
     * 点击事件处理
     * @param view 当前View
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_date_text_view :
                setEditTextDisableFocus();
                showDatePicker();
                dataChanged();
                break;
            case R.id.detail_time_text_view :
                setEditTextDisableFocus();
                showTimePicker();
                dataChanged();
                break;
            case R.id.detail_way_of_hand_on :
                showCheckBox();
                setEditTextDisableFocus();
                dataChanged();
                break;
            case R.id.detail_delete :
                final Homework homework = HomeworkUtil.getCurrentPositionHomework(position);
                HomeworkUtil.removeFormList(position);
                dismiss();
                Snackbar.make(getActivity().getCurrentFocus(), "已删除", Snackbar.LENGTH_LONG).setAction("撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HomeworkUtil.reAddToList(position, homework);
                        HomeworkUtil.freshData();
                    }
                }).show();
                //// TODO: 2016/6/23 没有撤销操作后，直接删除

                break;
            case R.id.detail_cancel :
                dismiss();
                break;
            case R.id.detail_save :
                if(isDataChanged) {
                    save();
                    dismiss();
                } else {
                    dismiss();
                    Snackbar.make(getActivity().getCurrentFocus(), "没有修改需要保存", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 判断是否修改
     */
    private void dataChanged() {
        if(!isDataChanged) {
            isDataChanged = true;
        }
    }

    /**
     * 显示日期选择对话框,手动编辑截止日期
     */
    private void showDatePicker() {
        new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                detailDateTextView.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日" + "\t");
                detailDateTextView.setTextSize(16);
                detailDateTextView.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 显示时间选择对话框，手动修改截止时间
     */
    private void showTimePicker() {
        new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String h = "" + hourOfDay;
                String m = "" + minute;
                if (hourOfDay < 10) {
                    h = "0" + hourOfDay;
                }
                if (minute < 10) {
                    m = "0" + minute;
                }
                detailTimeTextView.setText(h + ":" + m + "\t");
                detailTimeTextView.setTextSize(16);
                detailTimeTextView.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), true).show();
    }

    /**
     * 显示选择提交方式多选
     */
    private void showCheckBox() {
        Bundle bundle = new Bundle();
        bundle.putString("way_of_handon", detailWayOfHandOn.getText().toString());
        CheckBoxDialogFragment checkBoxDialogFragment = CheckBoxDialogFragment.getInstance(bundle);
        checkBoxDialogFragment.setCallBackListener(new CheckBoxDialogFragment.CallBackListener() {
            @Override
            public void onDataChange(String s) {
                detailWayOfHandOn.setText(s);
                detailWayOfHandOn.setTextSize(16);
                detailWayOfHandOn.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
        checkBoxDialogFragment.show(getActivity().getSupportFragmentManager(), "checkBoxDialogFragment");

    }

    /**
     * 让EditText失去焦点，并隐藏输入法
     */
    private void setEditTextDisableFocus() {
        detailLinearLayout.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(detailEditContent.getWindowToken(), 0);
    }

    /**
     * 保存修改Homework
     */
    private void save() {
        Homework homework = HomeworkUtil.getCurrentPositionHomework(position);
        homework.setContent(detailEditContent.getText().toString());
        homework.setWayOfHandOn(detailWayOfHandOn.getText().toString());
        homework.setDeadline(detailDateTextView.getText().toString() + detailTimeTextView.getText().toString());
        SharedPreferencesUtil.saveChangedHomework(getContext(), homework);
        HomeworkUtil.freshData();

    }

}
