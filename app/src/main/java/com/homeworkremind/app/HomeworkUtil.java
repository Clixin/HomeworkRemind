package com.homeworkremind.app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Homework处理工具
 * 增删改
 *
 * Created by Clixin on 2016/6/21.
 */
public class HomeworkUtil {


    /**
     * 记录作业总数，用来从文件中恢复
     */
    private static int count;

    /**
     * 实例
     */
    private static HomeworkUtil instance = null;

    /**
     * 打印Tag
     */
    private static final String TAG = "HomeworkUtil";

    /**
     * 私有化构造方法，防止外部创建实例
     */
    private HomeworkUtil() {}

    /**
     * 获取实例
     * @return 一个实例
     */
    public static HomeworkUtil getInstance(){
        if(instance == null) {
            instance = new HomeworkUtil();
        }
        return instance;
    }

    /**
     * 添加到列表
     *
     * @param homework 要添加的对象
     */
    public static void addToList(Homework homework) {
        MainActivity.mList.add(homework);
    }

    /**
     * 重新添加进list，执行撤销操作后执行
     * @param position 从哪删的，就从哪出现
     * @param homework 对象
     */
    public static void reAddToList(int position, Homework homework) {
        MainActivity.mList.add(position, homework);
    }


    /**
     * 从列表移除
     * @param position 对象在列表中的下标
     *
     */
    public static void removeFormList(int position) {
        MainActivity.mList.remove(position);
        MainActivity.mAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新数据
     * @param mList 当前列表
     * @param homework 数据
     */
    public static void freshData(List<Homework> mList, Homework homework) {
        addToList(homework);
        Log.d(TAG, "freshData: " + mList.size());
        freshData();
    }

    /**
     * 提醒Adapter刷新数据
     */
    public static void freshData() {
        MainActivity.mAdapter.notifyDataSetChanged();
    }

    /**
     * 得到当前位置的homework
     * @param position 位置
     * @return 返回一个homework
     */
    public static Homework getCurrentPositionHomework(int position) {
        return MainActivity.mList.get(position);
    }


}
