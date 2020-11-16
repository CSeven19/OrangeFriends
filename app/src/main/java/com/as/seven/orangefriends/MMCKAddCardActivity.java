package com.as.seven.orangefriends;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MMCKAddCardActivity extends AppCompatActivity {
    //    FloatingActionButton fb = null;
    //Save the FAB's active status
    //false -> fab = close
    //true -> fab = open
//    private boolean FAB_Status = false;
    int cardNum = 0;
//    //Animations
//    Animation show_fab_1;
//    Animation hide_fab_1;
//    Animation show_fab_2;
//    Animation hide_fab_2;
//    Animation show_fab_3;
//    Animation hide_fab_3;
//    FloatingActionButton fab1;
//    FloatingActionButton fab2;
//    FloatingActionButton fab3;

    TextView tv1 = null;
    ImageView iv1 = null;

    EditText et1 = null;
    EditText et2 = null;

    Button bt1 = null;
    Button bt2 = null;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmckadd_card);

        // 设置toolbar
        setToolBar();

        // init
        init();

//        fabClickListener();
    }

    @SuppressLint("RestrictedApi")
    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.icon_listview1);
        toolbar.setTitle("美美のカード");
        toolbar.setSubtitle("カードを作って");
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

        tv1 = findViewById(R.id.mmck_addCard_tv1);
        tv1.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        iv1 = findViewById(R.id.mmck_addCard_iv1);
        initIv1();

        bt1 = findViewById(R.id.mmck_addCard_bt1);
        bt2 = findViewById(R.id.mmck_addCard_bt2);

        et1 = findViewById(R.id.mmck_addCard_et1);
        et2 = findViewById(R.id.mmck_addCard_et2);

        clearCard();
        saveCard();

    }


    private void initIv1() {
        int ran1 = new Random(System.currentTimeMillis()).nextInt(14);
        switch (ran1) {
            case 0:
                iv1.setImageResource(R.drawable.cardlist1);
                cardNum = 1;
                break;
            case 1:
                iv1.setImageResource(R.drawable.cardlist2);
                cardNum = 2;
                break;
            case 2:
                iv1.setImageResource(R.drawable.cardlist3);
                cardNum = 3;
                break;
            case 3:
                iv1.setImageResource(R.drawable.cardlist4);
                cardNum = 4;
                break;
            case 4:
                iv1.setImageResource(R.drawable.cardlist5);
                cardNum = 5;
                break;
            case 5:
                iv1.setImageResource(R.drawable.cardlist6);
                cardNum = 6;
                break;
            case 6:
                iv1.setImageResource(R.drawable.cardlist7);
                cardNum = 7;
                break;
            case 7:
                iv1.setImageResource(R.drawable.cardlist8);
                cardNum = 8;
                break;
            case 8:
                iv1.setImageResource(R.drawable.cardlist9);
                cardNum = 9;
                break;
            case 9:
                iv1.setImageResource(R.drawable.cardlist10);
                cardNum = 10;
                break;
            case 10:
                iv1.setImageResource(R.drawable.cardlist11);
                cardNum = 11;
                break;
            case 11:
                iv1.setImageResource(R.drawable.cardlist12);
                cardNum = 12;
                break;
            case 12:
                iv1.setImageResource(R.drawable.cardlist13);
                cardNum = 13;
                break;
            case 13:
                iv1.setImageResource(R.drawable.cardlist14);
                cardNum = 14;
                break;
        }
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
//
//    private void fabClickListener() {
//        // 卡牌一览
//        fab1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MMCKAddCardActivity.this, "fab1", Toast.LENGTH_LONG).show();
//            }
//        });
//        // 抽卡
//        fab2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MMCKAddCardActivity.this, "fab2", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private void clearCard() {
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et1.setText("");
                et2.setText("");
            }
        });
    }

    private void saveCard() {
//        String sql = "INSERT INTO t_of_drawlist(title, content, image, createtime, updatetime) VALUES("
//                + et1.getText() + "," + et2.getText() + ","
//                + "card" + cardNum + "," + new SimpleDateFormat("yyy-MM-dd").format(new Date()) + ","
//                + new SimpleDateFormat("yyy-MM-dd").format(new Date());
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager mDBManager = new DBManager(MMCKAddCardActivity.this);
                if (null != et1.getText() && !("".equalsIgnoreCase(et1.getText().toString()))) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.clear();
                    contentValues.put("title", et1.getText().toString());
                    contentValues.put("content", et2.getText().toString());
                    contentValues.put("image", "card" + cardNum);
                    contentValues.put("createtime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    contentValues.put("updatetime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    mDBManager.Insert("t_of_drawlist", contentValues);
                    finish();
                }
            }
        });
    }

}
