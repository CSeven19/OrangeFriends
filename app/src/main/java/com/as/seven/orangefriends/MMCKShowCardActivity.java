package com.as.seven.orangefriends;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MMCKShowCardActivity extends AppCompatActivity {

//    private boolean FAB_Status = false;
//    //Animations
//    FloatingActionButton fb = null;
//    Animation show_fab_1;
//    Animation hide_fab_1;
//    Animation show_fab_2;
//    Animation hide_fab_2;
//    Animation show_fab_3;
//    Animation hide_fab_3;
//    FloatingActionButton fab1;
//    FloatingActionButton fab2;
//    FloatingActionButton fab3;


    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private DBManager mDBManager = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmckshow_card);

        // 设置toolbar
        setToolBar();

        // init
        init();

        // 卡牌展现
        showCards();
    }

    @SuppressLint("RestrictedApi")
    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.icon_listview1);
        toolbar.setTitle("美美のカード");
        toolbar.setSubtitle("カードを展示する");
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_48dp2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        //设置回退键的单机点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //调用菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); // 参数1为布局文件(menu.xml)
        // 溢出菜单项左侧显示图标
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                startActivity(new Intent(this, MainMMCK.class));
                break;
            case R.id.item2:
                startActivity(new Intent(this, MainSevenGU.class));
                break;
            case R.id.item3:
                startActivity(new Intent(this, MainSMBQ.class));
            default:
                break;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {
//        fb = findViewById(R.id.mmck_addcard_fab);
//
//        // floatingbar动画
//        fb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (FAB_Status == false) {
//                    //Display FAB menu
//                    expandFAB();
//                    FAB_Status = true;
//                } else {
//                    //Close FAB menu
//                    hideFAB();
//                    FAB_Status = false;
//                }
//            }
//        });
//
//        fab1 = findViewById(R.id.mmck_addcard_fab_1);
//        fab2 = findViewById(R.id.mmck_addcard_fab_2);
//        fab3 = findViewById(R.id.mmck_addcard_fab_3);
//
//        //Animations
//        show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
//        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
//        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
//        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
//        show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
//        hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);

        mDBManager = new DBManager(this);
        recyclerView = findViewById(R.id.mmck_showcard_rv1);
    }

//    private void expandFAB() {
//
//        //Floating Action Button 1
//        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
//        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
//        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
//        fab1.setLayoutParams(layoutParams);
//        fab1.startAnimation(show_fab_1);
//        fab1.setClickable(true);
//
//        //Floating Action Button 2
//        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
//        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
//        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
//        fab2.setLayoutParams(layoutParams2);
//        fab2.startAnimation(show_fab_2);
//        fab2.setClickable(true);
//
//        //Floating Action Button 3
//        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
//        layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
//        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);
//        fab3.setLayoutParams(layoutParams3);
//        fab3.startAnimation(show_fab_3);
//        fab3.setClickable(true);
//    }
//
//
//    private void hideFAB() {
//
//        //Floating Action Button 1
//        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
//        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
//        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
//        fab1.setLayoutParams(layoutParams);
//        fab1.startAnimation(hide_fab_1);
//        fab1.setClickable(false);
//
//        //Floating Action Button 2
//        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
//        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
//        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
//        fab2.setLayoutParams(layoutParams2);
//        fab2.startAnimation(hide_fab_2);
//        fab2.setClickable(false);
//
//        //Floating Action Button 3
//        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
//        layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
//        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
//        fab3.setLayoutParams(layoutParams3);
//        fab3.startAnimation(hide_fab_3);
//        fab3.setClickable(false);
//    }

    // 展示美美抽卡一览
    private void showCards() {
        List<Integer> data = new ArrayList<Integer>();
        List<String> data_tv = new ArrayList<>();
        DBManager mDBManager = new DBManager(this);
        final ArrayList<HashMap<String, String>> TableSqlResult = mDBManager.Query("t_of_drawlist", null, null, null, null, null, new String[]{"id", "title", "content", "image", "createtime", "updatetime", "drawtime"});
        if (null != TableSqlResult) {
            for (HashMap<String, String> row : TableSqlResult) {
                switch (row.get("image")) {
                    case "card1":
                        data.add(R.drawable.card1);
                        break;
                    case "card2":
                        data.add(R.drawable.card2);
                        break;
                    case "card3":
                        data.add(R.drawable.card3);
                        break;
                    case "card4":
                        data.add(R.drawable.card4);
                        break;
                    case "card5":
                        data.add(R.drawable.card5);
                        break;
                    case "card6":
                        data.add(R.drawable.card6);
                        break;
                    case "card7":
                        data.add(R.drawable.card7);
                        break;
                    case "card8":
                        data.add(R.drawable.card8);
                        break;
                    case "card9":
                        data.add(R.drawable.card9);
                        break;
                    case "card10":
                        data.add(R.drawable.card10);
                        break;
                    case "card11":
                        data.add(R.drawable.card11);
                        break;
                    case "card12":
                        data.add(R.drawable.card12);
                        break;
                    case "card13":
                        data.add(R.drawable.card13);
                        break;
                    case "card14":
                        data.add(R.drawable.card14);
                        break;
                }
                data_tv.add(row.get("title"));
            }
        }
        adapter = new RecyclerViewAdapter(this, data, data_tv);
        adapter.setLongClickListener(new RecyclerViewAdapter.OnLongClickListener() {
            @Override
            public boolean onLongClick(int position) {
                deleteItemById(TableSqlResult.get(position).get("id") != null ? TableSqlResult.get(position).get("id") : "");
                return true;
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void deleteItemById(String id) {
        if (!id.isEmpty()) {
            String selection = "id=?";
            String[] args = new String[]{id};
            mDBManager.Delete("t_of_drawlist", selection, args);
            showCards();
        }
    }
}
