package com.as.seven.orangefriends;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MMCKshadowCardActivity extends AppCompatActivity {

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

    ImageView iv1;
    RotateAnimation rotate;
    private ShakeSensor mShakeSensor = null;
    private DBManager mDBManager = null;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmckshadow_card);

        // 设置toolbar
        setToolBar();

        // init
        init();

//        startShadowAnimation();
    }

    @SuppressLint("RestrictedApi")
    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.icon_listview1);
        toolbar.setTitle("美美のカード");
        toolbar.setSubtitle("カードを引く");
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
        iv1 = findViewById(R.id.mmck_shadowcard_iv1);
        mDBManager = new DBManager(this);
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

        mShakeSensor = new ShakeSensor(MMCKshadowCardActivity.this, 4000);
        mShakeSensor.setShakeListener(new ShakeSensor.OnShakeListener() {
//            @Override
//            public void onShakeStart(SensorEvent event) {
//                startShadowAnimation();
//            }

            @Override
            public void onShakeComplete(SensorEvent event) {
                startShadowAnimation();
                ArrayList<HashMap<String, String>> datas = getAllDatas();
                if (null != datas && 0 != datas.size()) {
                    Random rand = new Random();
                    rand.setSeed(new Date().getTime());
                    int randNum = rand.nextInt(datas.size());
                    Toast.makeText(MMCKshadowCardActivity.this, datas.get(randNum).get("title") + "：が選択された", Toast.LENGTH_LONG).show();
                    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
                    service.schedule(new Runnable() {
                        @Override
                        public void run() {
                            rotate.cancel();
                        }
                    }, 1l, TimeUnit.SECONDS);
                    updateDrawList(datas.get(randNum));
                } else {
                    Toast.makeText(MMCKshadowCardActivity.this, "くじがありません", Toast.LENGTH_LONG).show();
                }
            }
        });
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

    public void startShadowAnimation() {
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv1,"rotation",120f);
//        objectAnimator.setDuration(2000);
//        objectAnimator.setRepeatCount(-1);
//        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
//        objectAnimator.start();


        rotate = new RotateAnimation(0f, 6f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(100);//设置动画持续周期
        rotate.setRepeatCount(-1);//设置重复次数
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setStartOffset(10);//执行前的等待时间
        rotate.setRepeatMode(ValueAnimator.REVERSE);
//        iv1.setAnimation(rotate);
        iv1.startAnimation(rotate);
    }

    /**
     * @Title: onResume
     * @Description:
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        mShakeSensor.register();
        super.onResume();
    }

    /**
     * @Title: onStop
     * @Description:
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
        mShakeSensor.unregister();
        super.onStop();
    }

    private ArrayList<HashMap<String, String>> getAllDatas() {
        return mDBManager.Query("t_of_drawlist", null, null, "updatetime DESC", null, null, new String[]{"id", "title", "content", "image", "updatetime"});
    }

    private void updateDrawList(HashMap<String, String> drawObject) {
        String selection = "id=?";
        String[] args = new String[]{drawObject.get("id")};
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        contentValues.put("drawtime", date2time(new Date()));
        mDBManager.Update("t_of_drawlist", contentValues, selection, args);
    }

    private String date2time(Date date) {
        return date.getTime() + "";
    }
}
