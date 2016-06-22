package com.homeworkremind.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 主活动 ，用于显示作业列表，Launch Activity
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * 用于储存Homework
     */
    static List<HomeWork> mList;

    /**
     * 用于判断初始化界面，只用加载一次
     */
    boolean initOnce = false;

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
        Log.d(TAG, "onCreate: 启动起来了");


        initData();
        initView();
    }

    /**
     * 初始化数据，这里仅作于调试用，将来数据是从本地或者服务器上获取
     */
    private void initData() {
        mList = new ArrayList<HomeWork>();
        HomeWork homeWork_1 = new HomeWork("2016年6月17日", "文化产业概论", "QQ文件", "期末论文，300字");
        HomeWork homeWork_2 = new HomeWork("2016年6月18日", "比较文学", "邮箱", "期末论文，300字");
        mList.add(homeWork_1);
        mList.add(homeWork_2);
        HomeworkUtil.getInstance();
        mList = HomeworkUtil.loadFormFile(this, mList);
        Log.d(TAG, "initData: " + mList.size());
    }

    /**
     * 初始化View
     */
    private void initView() {
        if(initOnce) {
            return;
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.base_swipe_list);
        mAdapter = new RecyclerViewAdapter(mList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        initOnce = true;
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
                    HomeworkUtil.freshData(mList, data);
                    HomeworkUtil.saveIntoFile(this, mList, data);
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
