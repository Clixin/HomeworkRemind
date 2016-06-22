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
     * @param mList 当前列表
     * @param homeWork 要添加的对象
     */
    public static void addToList(List<HomeWork> mList, HomeWork homeWork) {
        mList.add(homeWork);
    }

    /**
     * 从列表移除
     * @param position 对象在列表中的下标
     * @return
     */
    public static boolean removeFormList(int position) {
        MainActivity.mList.remove(position);
        MainActivity.mAdapter.notifyDataSetChanged();
        deleteFromFile(position);
       // MainActivity.mAdapter.showSnackBar();
        return true;
    }

    /**
     * 刷新数据
     * @param mList 当前列表
     * @param data 数据
     */
    public static void freshData(List<HomeWork> mList, Intent data) {
        HomeWork homeWork = new HomeWork();
        homeWork.setContent(data.getStringExtra("homework_content"));
        homeWork.setDeadline(data.getStringExtra("homework_deadline"));
        homeWork.setWayOfHandOn(data.getStringExtra("homework_handon"));
        addToList(mList, homeWork);
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
     * 从文件读取数据
     */
    public static List<HomeWork> loadFormFile(Context context, List<HomeWork> mList) {
        FileInputStream in;
        BufferedReader reader = null;
        String fileName;
        HomeWork homework;
        for (int i = 1; i <= count; i++) {
            fileName = "hw_" + i;
            try {
                in = context.openFileInput(fileName);
                reader = new BufferedReader(new InputStreamReader(in));
                homework = new HomeWork();
                homework.setContent(reader.readLine());
                homework.setDeadline(reader.readLine());
                homework.setWayOfHandOn(reader.readLine());
                addToList(mList, homework);
            } catch (FileNotFoundException e) {
                Log.d(TAG, "文件读取失败，原因：文件" + fileName + "不存在");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mList;
    }

    public static void deleteFromFile(int position) {
        String fileName = "hw_" + position;
        File file = new File(fileName);
        if (file.exists())
            file.delete();

    }

    /**
     * 存储进内部储存
     *
     * @param data 数据
     */
    public static void saveIntoFile(Context context, List<HomeWork> mList, Intent data) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(data.getStringExtra("homework_content")).append("\n")
                .append(data.getStringExtra("homework_deadline")).append("\n")
                .append(data.getStringExtra("homework_handon")).append("\n");
        Log.d(TAG, stringBuilder.toString());
        FileOutputStream out;
        BufferedWriter writer = null;
        count = mList.size();
        String fileName = "hw_" + count;
        try {
            out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(stringBuilder.toString());
            Log.d(TAG, "saveIntoFile: 文件创建成功");
        } catch (FileNotFoundException e) {
            Log.d(TAG, "sameIntoFile: 文件创建失败");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static HomeWork getCurrentPositionHomework(int position) {
        return MainActivity.mList.get(position);
    }


}
