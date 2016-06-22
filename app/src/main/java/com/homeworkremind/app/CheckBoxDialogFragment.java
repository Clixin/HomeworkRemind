package com.homeworkremind.app;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


/**
 * 用于出现修改提交方式复选框的对话框
 * Created by Clixin on 2016/6/22.
 */
public class CheckBoxDialogFragment extends DialogFragment implements View.OnClickListener{

    CheckBox changeEWord;
    CheckBox changePWord;
    CheckBox changeViaFile;
    CheckBox changeViaEmail;
    TextView changeConfirm;
    TextView changeCancel;
    CallBackListener callBackListener;

    /**
     * 构造一个可以传递数据
     * @param bundle 传递的数据
     * @return 返回一个实例
     */
    public static CheckBoxDialogFragment getInstance(Bundle bundle) {
        CheckBoxDialogFragment checkBoxDialogFragment = new CheckBoxDialogFragment();
        checkBoxDialogFragment.setArguments(bundle);
        return checkBoxDialogFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkbox_dialog, container);
        init(view);
        String[] checked = getArguments().getString("way_of_handon").split("\t");
        showCheckBoxChecked(checked);

        return view;
    }

    /**
     * 遍历数组，判断是否选中状态
     * @param checked 数组
     */
    private void showCheckBoxChecked(String[] checked) {
        for(int i = 0; i < checked.length; i++) {

            if(checked[i].equals(changeEWord.getText().toString())) {
                changeEWord.setChecked(true);
            }
            if(checked[i].equals(changePWord.getText().toString())) {
                changePWord.setChecked(true);
            }
            if(checked[i].equals(changeViaEmail.getText().toString())) {
                changeViaEmail.setChecked(true);
            }
            if(checked[i].equals(changeViaFile.getText().toString())) {
                changeViaFile.setChecked(true);
            }
        }
    }

    private void init(View view) {
        if(view == null) {
            return;
        }
        changeEWord = (CheckBox) view.findViewById(R.id.change_e_word);
        changePWord = (CheckBox) view.findViewById(R.id.change_p_word);
        changeViaFile = (CheckBox) view.findViewById(R.id.change_via_file);
        changeViaEmail= (CheckBox) view.findViewById(R.id.change_via_email);
        changeConfirm = (TextView) view.findViewById(R.id.change_confirm);
        changeCancel = (TextView) view.findViewById(R.id.change_cancel);
        changeConfirm.setOnClickListener(this);
        changeCancel.setOnClickListener(this);
        final int color =changeViaFile.getCurrentTextColor();
        changeEWord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) {
                    changeViaFile.setTextColor(Color.BLACK);
                    changeViaEmail.setTextColor(Color.BLACK);
                    changeViaFile.setClickable(true);
                    changeViaEmail.setClickable(true);
                } else {
                    changeViaFile.setTextColor(color);
                    changeViaEmail.setTextColor(color);
                    changeViaFile.setClickable(false);
                    changeViaEmail.setClickable(false);
                    changeViaFile.setChecked(false);
                    changeViaEmail.setChecked(false);
                }
            }
        });

    }

    /**
     * 通过判断是否选择，获取复选框内容
     *
     * @return 内容
     */
    private String getWayOfHandOn() {
        String s = "";
        if (changeEWord.isChecked()) {

            s += changeEWord.getText().toString() + "\t";
            if (changeViaFile.isChecked()) {
                s += changeViaFile.getText().toString() + "\t";
            }

            if (changeViaEmail.isChecked()) {
                s += changeViaEmail.getText().toString() + "\t";
            }
        }
        if (changePWord.isChecked()) {
            s += changePWord.getText().toString() + "\t";
        }

        return s;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_confirm :
                callBackListener.onDataChange(getWayOfHandOn());
                dismiss();
                break;
            case R.id.change_cancel :
                dismiss();
                break;
        }

    }

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    interface CallBackListener {
        void onDataChange(String s);
    }


}
