package com.homeworkremind.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<HomeWork> mList;
    RecyclerView mRecyclerView;
    RecyclerViewAdapter mAdapter;

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

    private void initData() {
        mList = new ArrayList<HomeWork>();
        HomeWork homeWork_1 = new HomeWork("2016年6月17日", "文化产业概论", "QQ文件", "期末论文，300字");
        HomeWork homeWork_2 = new HomeWork("2016年6月18日", "比较文学", "邮箱", "期末论文，300字");
        mList.add(homeWork_1);
        mList.add(homeWork_2);
        Log.d(TAG, "initData: " + mList.size());
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.base_swipe_list);
        mAdapter = new RecyclerViewAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1 :
                if(resultCode == RESULT_OK) {
                    HomeWork homeWork = new HomeWork();
                    homeWork.setContent(data.getStringExtra("homework_content"));
                    homeWork.setDeadline(data.getStringExtra("homework_deadline"));
                    homeWork.setWayOfHandOn(data.getStringExtra("homework_handon"));
                    mList.add(homeWork);
                    mAdapter.notifyDataSetChanged();

                    break;
                }

        }
    }


}
