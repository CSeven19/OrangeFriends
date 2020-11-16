package com.as.seven.orangefriends;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainMMCK extends AppCompatActivity {

    private DBManager mDBManager = null;
    FloatingActionButton fb = null;
    //Save the FAB's active status
    //false -> fab = close
    //true -> fab = open
    private boolean FAB_Status = false;
    //Animations
    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;
    ImageView iv1 = null;
    ImageView iv2 = null;
    TextView tv1 = null;
    TextView tv2 = null;
    LinearLayout ll1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mmck);

        // 设置toolbar
        setToolBar();

        // init
        init();

        // 循环动画
        startCircleAnimation();

        showMMCKcontent();

        fabClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showMMCKcontent();
    }

    private void init() {
        fb = findViewById(R.id.fab);

        // floatingbar动画
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FAB_Status == false) {
                    //Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }
            }
        });

        fab1 = findViewById(R.id.fab_1);
        fab2 = findViewById(R.id.fab_2);
        fab3 = findViewById(R.id.fab_3);

        //Animations
        show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);

        iv1 = findViewById(R.id.mmck_iv1);
        iv2 = findViewById(R.id.mmck_iv2);
        tv1 = findViewById(R.id.mmck_tv2);
        tv2 = findViewById(R.id.mmck_tv3);
        ll1 = findViewById(R.id.mmck_ll1);

        initIv1();

    }

    private void initIv1() {
        int ran1 = new Random(System.currentTimeMillis()).nextInt(14);
        switch (ran1) {
            case 0:
                iv1.setImageResource(R.drawable.card1);
                break;
            case 1:
                iv1.setImageResource(R.drawable.card2);
                break;
            case 2:
                iv1.setImageResource(R.drawable.card3);
                break;
            case 3:
                iv1.setImageResource(R.drawable.card4);
                break;
            case 4:
                iv1.setImageResource(R.drawable.card5);
                break;
            case 5:
                iv1.setImageResource(R.drawable.card6);
                break;
            case 6:
                iv1.setImageResource(R.drawable.card7);
                break;
            case 7:
                iv1.setImageResource(R.drawable.card8);
                break;
            case 8:
                iv1.setImageResource(R.drawable.card9);
                break;
            case 9:
                iv1.setImageResource(R.drawable.card10);
                break;
            case 10:
                iv1.setImageResource(R.drawable.card11);
                break;
            case 11:
                iv1.setImageResource(R.drawable.card12);
                break;
            case 12:
                iv1.setImageResource(R.drawable.card13);
                break;
            case 13:
                iv1.setImageResource(R.drawable.card14);
                break;
        }
    }


    @SuppressLint("RestrictedApi")
    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.icon_listview1);
        toolbar.setTitle("美美のカード");
        toolbar.setSubtitle("ある意味のカードを作って、揺って、従って");
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

    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab_3);
        fab3.setClickable(true);
    }


    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab_3);
        fab3.setClickable(false);
    }

    private void startCircleAnimation() {
        // 图片上小移动
        ObjectAnimator ob1 = ObjectAnimator.ofFloat(iv1, "TranslationY", -100);
        ob1.setDuration(2000);
        ob1.setRepeatCount(-1);
        ob1.setRepeatMode(ValueAnimator.REVERSE);
        ob1.start();
        // 阴影放大缩小
        ObjectAnimator ob2 = ObjectAnimator.ofFloat(iv2, "scaleX", 0.5f);
        ob2.setDuration(2000);
        ob2.setRepeatCount(-1);
        ob2.setRepeatMode(ValueAnimator.REVERSE);
        ob2.start();
    }

    private void fabClickListener() {
        // 卡牌一览
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMMCK.this, MMCKShowCardActivity.class));
            }
        });
        // 抽卡
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMMCK.this, MMCKshadowCardActivity.class));
            }
        });
        // 增加卡牌
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMMCK.this, MMCKAddCardActivity.class));
            }
        });
    }

    // 展示美美抽卡这周内容
    private void showMMCKcontent() {
        mDBManager = new DBManager(this);
//        // 创建表
//        String sql = "CREATE TABLE if not exists t_of_drawlist( id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT DEFAULT '', content TEXT DEFAULT '', image TEXT DEFAULT '', createtime TEXT DEFAULT '', updatetime TEXT DEFAULT '',drawtime INTEGER DEFAULT 0)";
//        mDBManager.CreateTable(this, sql);
//        // 获取抽卡最近时间的卡片
//        String selection = "drawtime=?";
//        String[] args = new String[]{"(select max(drawtime) from t_of_drawlist)"};
//        ArrayList<HashMap<String, String>> TableSqlResult = mDBManager.Query("t_of_drawlist", null, null, null, selection, args, new String[] {"title", "content", "image", "createtime", "updatetime", "drawtime"});
        // 测试数据
        ArrayList<HashMap<String, String>> TableSqlResult = mDBManager.Query("t_of_drawlist", null, null, null, null, null, new String[]{"title", "content", "image", "createtime", "updatetime", "drawtime"});
        if (null != TableSqlResult && 0 != TableSqlResult.size()) {
            HashMap<String, String> maxMap = new HashMap<>();
            long max = 0;
            for (HashMap<String, String> row : TableSqlResult) {
                if (Long.parseLong(row.get("drawtime")) > max) {
                    max = Long.parseLong(row.get("drawtime"));
                    maxMap = row;
                }
            }
            tv1.setText(maxMap.get("title"));
            tv2.setText(maxMap.get("content"));
        } else {
            tv1.setText("");
            tv2.setText("");
        }
    }
}
//}
