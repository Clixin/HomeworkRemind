package com.homeworkremind.app;


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

import java.util.List;

/**
 * 主活动 ，用于显示作业列表，Launch Activity
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * 用于储存Homework
     */
    static List<Homework> mList;

    /**
     * 可滚动View，极高自定义
     */
    RecyclerView mRecyclerView;

    /**
     * 可滚动View的适配器
     */
    static RecyclerViewAdapter mAdapter;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.base_swipe_list);
        mAdapter = new RecyclerViewAdapter(mList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 初始化数据，从本地获取数据
     */
    private void initData() {
        mList = SharedPreferencesUtil.loadHomeworks(this, getCurrentFocus());
        Log.d(TAG, "initData: " + mList.size());
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

                    Homework homework = new Homework();
                    homework.setContent(data.getStringExtra("homework_content"));
                    homework.setDeadline(data.getStringExtra("homework_deadline"));
                    homework.setWayOfHandOn(data.getStringExtra("homework_handon"));

                    SharedPreferencesUtil.saveHomework(this, homework);//保存进SharePreferences
                    HomeworkUtil.freshData(mList, homework);
                    break;
                }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab :
                Intent intent = new Intent(MainActivity.this, AddHomeworkActivity.class);
                startActivityForResult(intent, 1);
                break;

        }

    }

}
