package com.as.seven.orangefriends;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainSevenGU extends AppCompatActivity {

    private LineView lineView;
    private BarView barView;
    //    private PieView pieView;
    private LinearLayout ll1;

    FloatingActionButton fb = null;
    //Save the FAB's active status
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

    private DBManager mDBManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seven_gu);
        // 设置toolbar
        setToolBar();

        init();
        initView();

        // oncreate中执行getHeight仅会返回0
//        try {
//            initData();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
        fabClickListener();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            try {
                initData();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.icon_listview2);
        toolbar.setTitle("Sevenの成長");
        toolbar.setSubtitle("Seven自身の成長を監視して、いい成長しよう");
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

    private void init() {
        fb = findViewById(R.id.sevengu_fab);

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

        fab1 = findViewById(R.id.sevengu_fab_1);
        fab2 = findViewById(R.id.sevengu_fab_2);
        fab3 = findViewById(R.id.sevengu_fab_3);

        //Animations
        show_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getApplication(), R.anim.fab3_hide);

        mDBManager = new DBManager(this);
    }

    private void initView() {
        lineView = findViewById(R.id.line_view);
        lineView.setBackgroundColor(Color.parseColor("#88BCB6"));
        barView = findViewById(R.id.bar_view);
        barView.setBackgroundColor(Color.parseColor("#8FC59B"));
//        pieView = (PieView) findViewById(R.id.pie_view);
//        pieView.setBackgroundColor(Color.parseColor("#9096BD2C"));
        ll1 = findViewById(R.id.sevengu_ll1);
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initOperation();
            }
        });
    }


    // 获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }


    // 获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }


    // 获取上周的开始时间
    @SuppressWarnings("unused")
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }

    // 获取上周的结束时间
    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    // 取出本周的所有数据
    private ArrayList<HashMap<String, String>> getLastDataFromDB() {
        String selection = "createtime>?";
        String[] args = new String[]{new SimpleDateFormat("yyyy-MM-dd").format(getEndDayOfLastWeek())};
        ArrayList<HashMap<String, String>> TableSqlResult = mDBManager.Query("t_of_sevenGu", null, null, "createtime", selection, args, new String[]{"id", "point", "content", "milestone", "createtime", "updatetime"});
        return TableSqlResult;
    }

    private boolean isNotEmpty(String str) {
        return (str != null) && (!str.isEmpty());
    }

    public static String dayForWeek(String pTime) throws Throwable {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date tmpDate = format.parse(pTime);
        Calendar cal = Calendar.getInstance();
        String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
        try {
            cal.setTime(tmpDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];

    }

    private void initData() throws Throwable {
        ArrayList<HashMap<String, String>> items = getLastDataFromDB();
        float int1 = 0;
        float int2 = 0;
        float int3 = 0;
        float int4 = 0;
        float int5 = 0;
        float int6 = 0;
        float int7 = 0;
        for (int i = 0; i < items.size(); i++) {
            int a = Integer.parseInt(dayForWeek(items.get(i).get("createtime") + ""));
            switch (a) {
                case 1:
                    int1 = isNotEmpty(items.get(i).get("point")) ? Float.parseFloat(items.get(i).get("point")) : 0;
                    continue;
                case 2:
                    int2 = isNotEmpty(items.get(i).get("point")) ? Float.parseFloat(items.get(i).get("point")) : 0;
                    continue;
                case 3:
                    int3 = isNotEmpty(items.get(i).get("point")) ? Float.parseFloat(items.get(i).get("point")) : 0;
                    continue;
                case 4:
                    int4 = isNotEmpty(items.get(i).get("point")) ? Float.parseFloat(items.get(i).get("point")) : 0;
                    continue;
                case 5:
                    int5 = isNotEmpty(items.get(i).get("point")) ? Float.parseFloat(items.get(i).get("point")) : 0;
                    continue;
                case 6:
                    int6 = isNotEmpty(items.get(i).get("point")) ? Float.parseFloat(items.get(i).get("point")) : 0;
                    continue;
                case 7:
                    int7 = isNotEmpty(items.get(i).get("point")) ? Float.parseFloat(items.get(i).get("point")) : 0;
                    continue;
            }
        }

        List<Pillar> pillars = new ArrayList<Pillar>();
        pillars.add(new Pillar(int1, "Monday", "#90F45443", "#90E20904"));
        pillars.add(new Pillar(int2, "Tuesday", "#90FFCC33", "#90FBA30B"));
        pillars.add(new Pillar(int3, "Wednesday", "#90235B66", "#9005202A"));
        pillars.add(new Pillar(int4, "Thursday", "#90016B88", "#90235B66"));
        pillars.add(new Pillar(int5, "Friday", "#9096BD2C", "#90E20904"));
        pillars.add(new Pillar(int6, "Saturday", "#90FFCC33", "#90235B66"));
        pillars.add(new Pillar(int7, "Sunday", "#90ffffff", "#90E20904"));
        barView.setPillars(pillars);

        List<LinePoint> points = new ArrayList<LinePoint>();
        points.add(new LinePoint(int1, "Monday"));
        points.add(new LinePoint(int2, "Tuesday"));
        points.add(new LinePoint(int3, "Wednesday"));
        points.add(new LinePoint(int4, "Thursday"));
        points.add(new LinePoint(int5, "Friday"));
        points.add(new LinePoint(int6, "Saturday"));
        points.add(new LinePoint(int7, "Sunday"));
        lineView.setPoints(points);

//        List<Pie> pies = new ArrayList<Pie>();
//        pies.add(new Pie(int1, "#90F45443"));
//        pies.add(new Pie(int2, "#90FFCC33"));
//        pies.add(new Pie(int3, "#90235B66"));
//        pies.add(new Pie(int4, "#90016B88"));
//        pies.add(new Pie(int5, "#9096BD2C"));
//        pies.add(new Pie(int6, "#90FFCC33"));
//        pies.add(new Pie(int7, "#90ffffff"));
//        pieView.setPies(pies);
    }

    public void addBarValue(View v) {
//        barView.getPillars().get(0).value -= 10;
//        barView.getPillars().get(1).value += 10;
//        barView.getPillars().get(2).value += 1;
        barView.setLongClickable(true);
        barView.reloadData();
    }

    public void addLineValue(View v) {
//        LinePoint p = lineView.getPoint(6);
//        p.setValue(p.getValue() + 10);
        lineView.reloadData();
    }

//    public void addPieValue(View v) {
//        Pie p = pieView.getPie(1);
//        p.setValue(p.getValue() + 50);
//        pieView.reloadData();
//        ArrayList s = new ArrayList();
//    }

    private void initOperation() {
        addBarValue(barView);
        addLineValue(lineView);
//        addPieValue(pieView);
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

    private void fabClickListener() {
        // note一览
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainSevenGU.this, SevenGUShowDariesActivity.class);
                startActivity(it);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainSevenGU.this, "次の功能をご待ちください", Toast.LENGTH_LONG).show();
                try {
                    if(exportDaries()) {
                        Toast.makeText(MainSevenGU.this, "エクスポート成功、myGrowUp.txtをご覧してください", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(MainSevenGU.this, "エクスポート失敗、データがありません", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        // 增加note
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainSevenGU.this, SevenGUAddDairyActivity.class);
                startActivity(it);
            }
        });
    }

    private ArrayList<HashMap<String, String>> getAllDatas() {
        return mDBManager.Query("t_of_sevenGu", null, null, "createtime DESC", null, null, new String[]{"id", "point", "content", "milestone", "createtime", "updatetime"});
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean exportDaries() throws IOException {
        ArrayList<HashMap<String, String>> datas = getAllDatas();
        if (null!=datas && 0!=datas.size()) {
            // 数据写入文件，并保存到外部存储空间.
//        File str = this.getFilesDir(); // 内部存储
            verifyStoragePermissions(this);
            File str = Environment.getExternalStorageDirectory();
            Path outputDir = Paths.get(str.getAbsolutePath() + "/OrangeFriends");
            if (!(Files.exists(outputDir))) {
                Files.createDirectories(outputDir);
            }
            Path outputFile = Paths.get(str.getAbsolutePath() + "/OrangeFriends" + "/myGrowUp.txt");
            if (!(Files.exists(outputFile))) {
                Files.createFile(outputFile);
            }
            // 组合输出字段
            StringBuilder sb = new StringBuilder();
            for (HashMap<String, String> map : datas) {
                sb.append(map.get("createtime")).append(":").append(map.get("content")).append(";").append(map.get("point")).append(null != map.get("milestone") && !(map.get("milestone").isEmpty()) ? map.get("milestone") : "").append("\n");
            }
            Files.write(outputFile, sb.toString().getBytes());
            return true;
        } else {
            return false;
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
