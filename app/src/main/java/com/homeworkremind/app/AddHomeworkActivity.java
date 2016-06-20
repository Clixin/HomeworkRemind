package com.homeworkremind.app;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * 手动添加作业活动
 */
public class AddHomeworkActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 电子档，复选框
     */
    CheckBox eWordCheckBox;
    /**
     * 纸质档，复选框
     */
    CheckBox pWordCheckBox;
    /**
     * 传文件，复选框（当用户选择电子档后才显示）
     */
    CheckBox viaFileCheckBox;
    /**
     * 发邮件，复选框（当用户选择电子档后才显示）
     */
    CheckBox viaEmailCheckBox;
    /**
     * 编辑文字区域，hint属性智能
     */
    TextInputLayout editContentLayout;
    /**
     * 填写作业要求，嵌套在TextInputLayout里面，hint属性智能
     */
    EditText editContent;
    /**
     * 显示截至日期
     */
    TextView deadlineDate;
    /**
     * 显示截至时间
     */
    TextView deadlineTime;
    /**
     * 返回按钮
     */
    Button backButton;
    /**
     * 确定添加按钮
     */
    Button addButton;
    /**
     * 系统日历，注：其月份是从0开始的
     */
    Calendar c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        showViaCheckBox();

    }

    /**
     * 初始化View，添加监听
     */
    private void initView() {
        eWordCheckBox = (CheckBox) findViewById(R.id.e_word);
        pWordCheckBox = (CheckBox) findViewById(R.id.p_word);
        viaFileCheckBox = (CheckBox) findViewById(R.id.via_file);
        viaEmailCheckBox = (CheckBox) findViewById(R.id.via_email);
        deadlineDate = (TextView) findViewById(R.id.deadline_date);
        deadlineTime = (TextView) findViewById(R.id.deadline_time);
        editContentLayout = (TextInputLayout) findViewById(R.id.edit_content_layout);
        editContent = editContentLayout.getEditText();
        deadlineDate.setText(getCurrentDate());
        deadlineDate.setOnClickListener(this);
        deadlineTime.setOnClickListener(this);
        backButton = (Button) findViewById(R.id.back_button);
        addButton = (Button) findViewById(R.id.add_button);
        backButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

    }

    /**
     * 通过Calendar获得当前日期，显示在View上
     *
     * @return 当前日期
     */
    private String getCurrentDate() {
        c = Calendar.getInstance();
        return "" + c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DATE) + "日（点击编辑）";
    }

    /**
     * 为电子档复选框设置监听，显示or隐藏传文件、发邮件复选框
     */
    private void showViaCheckBox() {
        eWordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    viaFileCheckBox.setVisibility(View.VISIBLE);
                    viaEmailCheckBox.setVisibility(View.VISIBLE);
                } else {
                    viaFileCheckBox.setVisibility(View.GONE);
                    viaEmailCheckBox.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 实现了View.OnClickListener
     *
     * @param view 当前
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deadline_date:
                showDatePicker();
                break;
            case R.id.deadline_time:
                showTimePicker();
                break;
            case R.id.back_button:
                onBackPressed();
                break;
            case R.id.add_button:
                if (editContent.getText().toString().trim().equals("")) {
                    Snackbar.make(view, "没有填写作业要求，不能发布", Snackbar.LENGTH_LONG).show();
                    break;
                }
                addHomework();
                break;
        }

    }

    /**
     * 显示日期选择对话框,手动选择截止日期
     */
    private void showDatePicker() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                deadlineDate.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日" + "\t");
                deadlineDate.setTextSize(16);
                deadlineDate.setTextColor(Color.BLACK);
            }
        },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 显示时间选择对话框，手动选择截止时间
     */
    private void showTimePicker() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

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
                deadlineTime.setText(h + ":" + m + "\t");
                deadlineTime.setTextSize(16);
                deadlineTime.setTextColor(Color.BLACK);
            }
        },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE), true).show();
    }

    /**
     * 组装Homework，并返回给上一个活动
     */
    private void addHomework() {
        Intent intent = new Intent();
        intent.putExtra("homework_content", editContent.getText().toString().trim());
        intent.putExtra("homework_deadline", deadlineDate.getText().toString() + deadlineTime.getText().toString());
        intent.putExtra("homework_handon", getWayOfHandOn());
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 通过判断是否选择，获取复选框内容
     *
     * @return 内容
     */
    private String getWayOfHandOn() {
        String s = "";
        if (eWordCheckBox.isChecked()) {

            s += eWordCheckBox.getText().toString() + "\t";
            if (viaFileCheckBox.isChecked()) {
                s += viaFileCheckBox.getText().toString() + "\t";
            }

            if (viaEmailCheckBox.isChecked()) {
                s += viaEmailCheckBox.getText().toString() + "\t";
            }
        }
        if (pWordCheckBox.isChecked()) {
            s += pWordCheckBox.getText().toString() + "\t";
        }

        return s;
    }

}
