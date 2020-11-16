package com.as.seven.orangefriends;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    TextView tv1 = null;
    TextView tv2 = null;
    TextView tv3 = null;
    LinearLayout ll = null;
    CardView cv1 = null;
    CardView cv2 = null;
    CardView cv3 = null;
    Toolbar tb = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init
        init();

        // 设置toolbar
        setToolBar();

        // 渐显动画
        showLLSlowly();
    }

    private void init() {
        tv1 = findViewById(R.id.id_num1_right);
        tv2 = findViewById(R.id.id_num2_right);
        tv3 = findViewById(R.id.id_num3_right);
        ll = findViewById(R.id.activitymain);
        cv1 = findViewById(R.id.ma_cv1);
        cv2 = findViewById(R.id.ma_cv2);
        cv3 = findViewById(R.id.ma_cv3);
        tb = findViewById(R.id.toolbar);
        // 初始化右侧日期.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        String mDate = sdf.format(date);
        tv1.setText(mDate);
        tv2.setText(mDate);
        tv3.setText(mDate);

        // 跳转事件+动画
        jump2project();
    }

    @SuppressLint("RestrictedApi")
    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.icon_menu1);
        toolbar.setTitle("三つの選択である");
        toolbar.setSubtitle("さ、選択しよう");
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

    private void jump2project() {

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startShareAnimation(cv2, cv3, cv1);
                startActivity(new Intent(MainActivity.this, MainMMCK.class));
            }
        });
        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startShareAnimation(cv1, cv3, cv2);
                startActivity(new Intent(MainActivity.this, MainSevenGU.class));
            }
        });
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startShareAnimation(cv1, cv2, cv3);
                startActivity(new Intent(MainActivity.this, MainSMBQ.class));
            }
        });
    }

    private void showLLSlowly() {
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(1200);
        final LayoutAnimationController lac = new LayoutAnimationController(aa, 0.5f);
        ll.setLayoutAnimation(lac);
    }

    private void startShareAnimation(View cv1, View cv2, final View cv3) {
        // 渐隐
        ObjectAnimator ba = ObjectAnimator.ofFloat(cv1, "alpha", 1, 1, 0);
        ba.setDuration(1000);
        ObjectAnimator ba2 = ObjectAnimator.ofFloat(cv2, "alpha", 1, 1, 0);
        ba2.setDuration(1000);
        ObjectAnimator ba3 = ObjectAnimator.ofFloat(cv3, "translationY", -cv3.getY() + tb.getHeight());
        ba3.setDuration(1100);
        ba3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                switch (cv3.getId()) {
                    case R.id.ma_cv1:
                        startActivity(new Intent(MainActivity.this, MainMMCK.class));
                        break;
                    case R.id.ma_cv2:
                        startActivity(new Intent(MainActivity.this, MainSevenGU.class));
                        break;
                    case R.id.ma_cv3:
                        startActivity(new Intent(MainActivity.this, MainSMBQ.class));
                        break;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.playTogether(ba, ba2, ba3); //设置动画
        animatorSet.setDuration(1100);  //设置动画时间
        animatorSet.start(); //启动
    }
}
