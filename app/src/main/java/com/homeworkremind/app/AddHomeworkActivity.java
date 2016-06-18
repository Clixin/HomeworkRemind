package com.homeworkremind.app;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import java.util.Calendar;


public class AddHomeworkActivity extends AppCompatActivity implements View.OnClickListener {

    CheckBox eWordCheckBox;
    CheckBox pWordCheckBox;
    CheckBox viaFileCheckBox;
    CheckBox viaEmailCheckBox;
    TextInputLayout editContentLayout;
    EditText editContent;
    TextView deadlineDate;
    TextView deadlineTime;
    Button backButton;
    Button addButton;
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

    private void initView() {
        eWordCheckBox = (CheckBox)findViewById(R.id.e_word);
        pWordCheckBox = (CheckBox)findViewById(R.id.p_word);
        viaFileCheckBox = (CheckBox)findViewById(R.id.via_file);
        viaEmailCheckBox = (CheckBox)findViewById(R.id.via_email);
        deadlineDate = (TextView)findViewById(R.id.deadline_date);
        deadlineTime = (TextView)findViewById(R.id.deadline_time);
        editContentLayout = (TextInputLayout)findViewById(R.id.edit_content_layout);
        editContent = editContentLayout.getEditText();
        deadlineDate.setText(getCurrentDate());
        deadlineDate.setOnClickListener(this);
        deadlineTime.setOnClickListener(this);
        backButton = (Button)findViewById(R.id.back_button);
        addButton = (Button)findViewById(R.id.add_button);
        backButton.setOnClickListener(this);
        addButton.setOnClickListener(this);




    }

    private String getCurrentDate() {
        c = Calendar.getInstance();
        return "" + c.get(Calendar.YEAR) + "年" +(c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DATE) + "日（点击编辑）";
    }

    private void showViaCheckBox() {
        eWordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    viaFileCheckBox.setVisibility(View.VISIBLE);
                    viaEmailCheckBox.setVisibility(View.VISIBLE);
                } else {
                    viaFileCheckBox.setVisibility(View.GONE);
                    viaEmailCheckBox.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deadline_date :
                showDatePicker();
                break;
            case R.id.deadline_time :
                showTimePicker();
                break;
            case R.id.back_button :
                onBackPressed();
                break;
            case R.id.add_button :
                if(editContent.getText().toString().trim().equals("")) {
                    Snackbar.make(view, "没有填写作业要求，不能发布", Snackbar.LENGTH_LONG).show();
                    break;
                }
                addHomework();
                break;
        }

    }

    private void showDatePicker() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                c.set(year, monthOfYear, dayOfMonth);
                deadlineDate.setText(year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日" + "\t");
            }
        },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                c.set(hourOfDay, minute);
                String h ="" + hourOfDay;
                String m ="" + minute;
                if(hourOfDay < 10) {
                    h = "0" + hourOfDay;
                }
                if (minute < 10) {
                    m = "0" + minute;
                }
                deadlineTime.setText(h + ":" + m + "\t");
            }
        },
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),true).show();
    }

    private void addHomework() {
        Intent intent = new Intent();
        intent.putExtra("homework_content", editContent.getText().toString().trim());
        intent.putExtra("homework_deadline", deadlineDate.getText().toString() + deadlineTime.getText().toString());
        intent.putExtra("homework_handon", getWayOfHandOn());
        setResult(RESULT_OK, intent);
        finish();

    }

    private String getWayOfHandOn() {
        String s = "";
        if(eWordCheckBox.isChecked()) {

            s += eWordCheckBox.getText().toString() + "\t";
            if(viaFileCheckBox.isChecked()) {
                s += viaFileCheckBox.getText().toString() + "\t";
            }

            if(viaEmailCheckBox.isChecked()) {
                s += viaEmailCheckBox.getText().toString() + "\t";
            }
        }
        if(pWordCheckBox.isChecked()) {
            s += pWordCheckBox.getText().toString() + "\t";
        }

        return s;
    }

}
