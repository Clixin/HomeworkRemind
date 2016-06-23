package com.homeworkremind.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * 单独保存ID，最后根据id读取或者删除操作
 *
 * Created by Clixin on 2016/6/23.
 */
public class SharedPreferencesUtil {

    private static final String TAG = "SharedPreferencesUtil";

    /**
     * 保存ID文件名
     */
    private static final String Save_Id_xml_Name = "homework_id";

    /**
     * 记录有多少数量Homework，用来判断是否从文件读取
     */
    private static int homeworkCount = 0;

    /**
     * 私有化构造方法，防止创建实例
     */
    private SharedPreferencesUtil() {}

    /**
     * 保存Homework的Id，统一保存在一个文件里
     * @param context
     * @param id
     */
    private static void saveHomeworkId(Context context,int id) {
        SharedPreferences preferences = context.getSharedPreferences(Save_Id_xml_Name,
                Context.MODE_APPEND);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id_" + id, id);
        editor.apply();
        homeworkCount ++;
        Log.d(TAG, "saveHomeworkId: ID保存成功" + "存储id：" + id);
    }

    /**
     * 保存Homework
     *
     * @param context 上下文
     * @param homework 需要保存的homework
     */
    public static void saveHomework(Context context, Homework homework) {
        int id = homework.getId();
        saveHomeworkId(context, id);
        String xmlName = "homework_" + id;
        SharedPreferences preferences = context.getSharedPreferences(xmlName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("homework_content", homework.getContent());
        editor.putString("homework_wayofhandon", homework.getWayOfHandOn());
        editor.putString("homework_deadline", homework.getDeadline());
        editor.putString("homework_course", homework.getCourse());
        editor.apply();
        Log.d(TAG, "saveHomework: 保存成功" + "存储id：" + id);
    }

    /**
     * 保存修改的Homework ，与上面那个方法不同的是，不用再保存id
     * @param context 上下文
     * @param homework 需要保存的
     */
    public static void saveChangedHomework(Context context, Homework homework) {
        String xmlName = "homework_" + homework.getId();
        SharedPreferences preferences = context.getSharedPreferences(xmlName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("homework_content", homework.getContent());
        editor.putString("homework_wayofhandon", homework.getWayOfHandOn());
        editor.putString("homework_deadline", homework.getDeadline());
        editor.putString("homework_course", homework.getCourse());
        editor.apply();
        Log.d(TAG, "saveHomework: 保存成功" + "存储id：" + homework.getId());
    }

    /**
     * 删除操作
     * @param context 上下文
     * @param homework 需要删除的
     */
    public static void deleteHomework(Context context, Homework homework) {
        int id = homework.getId();
        removeHomeworkId(context, id);
        String xmlName = "homework_" + id;
        SharedPreferences preferences = context.getSharedPreferences(xmlName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        Log.d(TAG, "deleteHomework: 删除成功");

    }

    /**
     * 删除Homework后，并且移除其的id
     * @param context 上下文
     * @param id 移除的id
     */
    private static void removeHomeworkId(Context context, int id) {
        SharedPreferences preferences = context.getSharedPreferences(Save_Id_xml_Name,
                Context.MODE_APPEND);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("id_" +id);
        editor.apply();
        homeworkCount --;
        Log.d(TAG, "saveHomeworkId: ID移除成功");
    }

    /**
     * 加载id
     * @param context 上下文
     * @return 返回一个list
     */
    private static List<String> loadHomeworkId(Context context) {
        if (homeworkCount <= 0) {
            Log.d(TAG, "loadHomeworks: " + homeworkCount);
            return null;
        }
        SharedPreferences preferences = context.getSharedPreferences(Save_Id_xml_Name,
                Context.MODE_APPEND);
        Map<String, ?> valueMaps = preferences.getAll();
        List<String> id = new ArrayList<>();
        for (Map.Entry<String, ?> entry : valueMaps.entrySet()) {
            id.add(entry.getValue().toString());
        }
        return id;
    }

    /**
     * 根据保存的id，读取全部Homework
     * @param context 上下文
     */
    public static List<Homework> loadHomeworks(Context context, View view) {
        List<Homework> list = new ArrayList<>();
        if (homeworkCount <= 0) {
            Log.d(TAG, "loadHomeworks: " + homeworkCount);
            //Snackbar.make(view, "还没有作业，快添加吧",Snackbar.LENGTH_SHORT).show();
        } else {
            List<String> id = loadHomeworkId(context);
            for (String s : id) {
                list.add(loadHomework(context, s));
            }
            Log.d(TAG, "loadHomeworks: 加载完成");
        }
        return list;
    }

    /**
     * 根据id读取保存的homework,组装起来
     *
     * @param context 上下文
     * @param id ID
     * @return 返回一个Homework
     */
    public static Homework loadHomework(Context context, String id) {
        String xmlName = "homework_" + id;
        SharedPreferences preferences = context.getSharedPreferences(xmlName,
                Context.MODE_PRIVATE);
        Homework homework = new Homework(Integer.valueOf(id),
                preferences.getString("homework_deadline", ""),
                preferences.getString("homework_course", ""),
                preferences.getString("homework_wayofhandon", ""),
                preferences.getString("homework_content", ""));
        return homework;
    }

}
