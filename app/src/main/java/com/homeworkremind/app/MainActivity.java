package com.homeworkremind.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 主活动 ，用于显示作业列表，Launch Activity
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 用于储存Homework
     */
    List<HomeWork> mList;

    /**
     * 可滚动View，极高自定义
     */
    RecyclerView mRecyclerView;

    /**
     * 可滚动View的适配器
     */
    RecyclerViewAdapter mAdapter;

    /**
     * 用于计数
     */
    private static int count = 0;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddHomeworkActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        Log.d(TAG, "onCreate: 启动起来了");

        initData();
        initRecyclerView();
    }

    /**
     * 初始化数据，这里仅作于调试用，将来数据是从服务器上获取
     */
    private void initData() {
        mList = new ArrayList<HomeWork>();
        HomeWork homeWork_1 = new HomeWork("2016年6月17日", "文化产业概论", "QQ文件", "期末论文，300字");
        HomeWork homeWork_2 = new HomeWork("2016年6月18日", "比较文学", "邮箱", "期末论文，300字");
        mList.add(homeWork_1);
        mList.add(homeWork_2);
        loadFormFile();
        Log.d(TAG, "initData: " + mList.size());
    }

    /**
     * 初始化View
     */
    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.base_swipe_list);
        mAdapter = new RecyclerViewAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 加载右上角的设置
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 为设置选项的各个Item添加响应事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 返回startActivityForResult的结果
     *
     * @param requestCode 请求代码，要求唯一性
     * @param resultCode  结果代码，
     * @param data        返回的数据，intent类型
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    freshData(data);
                    saveIntoFile(data);
                    break;
                }
        }
    }

    /**
     * 刷新数据
     *
     * @param data 数据
     */
    private void freshData(Intent data) {
        HomeWork homeWork = new HomeWork();
        homeWork.setContent(data.getStringExtra("homework_content"));
        homeWork.setDeadline(data.getStringExtra("homework_deadline"));
        homeWork.setWayOfHandOn(data.getStringExtra("homework_handon"));
        mList.add(homeWork);
        count = mList.size();
        Log.d(TAG, "freshData: " + mList.size());
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 存储进内部储存
     *
     * @param data 数据
     */
    private void saveIntoFile(Intent data) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(data.getStringExtra("homework_content")).append("\n")
                .append(data.getStringExtra("homework_deadline")).append("\n")
                .append(data.getStringExtra("homework_handon")).append("\n");
        Log.d(TAG, stringBuilder.toString());
        FileOutputStream out;
        BufferedWriter writer = null;
        String fileName = "hw_" + count;
        try {
            out = openFileOutput(fileName, Context.MODE_PRIVATE);
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

    /**
     * 从文件读取数据
     */
    private void loadFormFile() {
        FileInputStream in;
        BufferedReader reader = null;
        String filename;
        HomeWork homework;
        for (int i = 1; i <= count; i++) {
            filename = "hw_" + i;
            try {
                in = openFileInput(filename);
                reader = new BufferedReader(new InputStreamReader(in));
                homework = new HomeWork();
                homework.setContent(reader.readLine());
                Log.d(TAG, "loadFormFile: " + homework.getContent());
                homework.setDeadline(reader.readLine());
                Log.d(TAG, "loadFormFile: " + homework.getDeadline());
                homework.setWayOfHandOn(reader.readLine());
                Log.d(TAG, "loadFormFile: " + homework.getWayOfHandOn());
                mList.add(homework);
            } catch (FileNotFoundException e) {
                Log.d("TAG", "文件读取失败，原因：文件" + filename + "不存在");
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
    }


}
