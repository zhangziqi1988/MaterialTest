package com.example.materialtest;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    //初始化Fruits数组
    private Fruit[] fruits = {new Fruit("Apple", R.drawable.apple), new Fruit("Banana", R.drawable.banana),
            new Fruit("Orange", R.drawable.orange),
            new Fruit("Watermelon", R.drawable.watermelon),
            new Fruit("Pear", R.drawable.pear),
            new Fruit("Grape", R.drawable.grape),
            new Fruit("Pineapple", R.drawable.pineapple),
            new Fruit("Strawberry", R.drawable.strawberry),
            new Fruit("Cherry", R.drawable.cherry),
            new Fruit("Mango", R.drawable.mango)

    };
    private List<Fruit> fruitList = new ArrayList<>();//声明水果的list
    private FruitAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//获取ToolbarID
        setSupportActionBar(toolbar);//加载Toolbar
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);//获取DrawLayoutID
        ActionBar actionBar = getSupportActionBar();//获取Actionbar的实例，实际上Actionbar是由Toolbar实现的
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//将Toolbar左边的返回按钮显示出来
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//设置返回按钮的图标
        }
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);//获取navigationViewID
        navView.setCheckedItem(R.id.nav_call);//设置默认选中的菜单项
        //处理菜单项的点击事件
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_call:
                        Toast.makeText(MainActivity.this,"You clicked call",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_friends:
                        Toast.makeText(MainActivity.this,"You clicked friends",Toast.LENGTH_SHORT).show();

                }
//                mDrawerLayout.closeDrawers();
                return true;

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);//获取FloatingActionButton的ID
        //处理悬浮按钮的点击事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar:可交互提示
                Snackbar.make(v, "Data deleted", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Data restored", Toast.LENGTH_SHORT).show();
                    }
                }).show();
//                Toast.makeText(MainActivity.this,"FAB clicked",Toast.LENGTH_SHORT).show();
            }
        });

        initFruits();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);//获取RecyclerVIew的ID
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);//设置GridLayoutManager为两列
        recyclerView.setLayoutManager(layoutManager);//加载布局管理器
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);//加载适配器

        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);//获取ID
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);//设置进度条颜色
        //设置下拉刷新的事件
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
//                try {
//                    refreshFruits1();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }


        });
    }

    private void refreshFruits1() throws InterruptedException {
        sleep(5000);
        initFruits();
        adapter.notifyDataSetChanged();
        swipeRefresh.setRefreshing(false);
    }

    private void refreshFruits() {

        //开启子线程，在子线程中操作。此时用户可以进行别的操作，不会阻塞主进程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(5000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruits() {
        fruitList.clear();
        for(int i = 0 ; i< 50;i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    //加载Toolbar菜单文件
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    //Toolbar的子项处理点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            //返回按钮
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
            default:
                break;

        }
        return true;

    }
}
