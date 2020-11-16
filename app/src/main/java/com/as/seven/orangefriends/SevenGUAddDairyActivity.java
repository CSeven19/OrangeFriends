package com.as.seven.orangefriends;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

public class SevenGUAddDairyActivity extends AppCompatActivity {

    private FloatingActionButton fab1;
    private RotationRatingBar rrb1;
    private EditText et1;
    private String createtime;
    private String content;
    private String milestone;
    private Float point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_guadd_dairy);

        // 设置toolbar
        setToolBar();

        init();

        Intent it = getIntent();
        getInformationFromMain(it);

        initEditView();
    }

    @SuppressLint("RestrictedApi")
    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.icon_listview2);
        toolbar.setTitle("Sevenの成長");
        toolbar.setSubtitle("日記と自分の評価を追加して");
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
        fab1 = findViewById(R.id.sevengu_add_fab);
        rrb1 = findViewById(R.id.rotationratingbar_main);
        et1 = findViewById(R.id.sevengu_add_et1);

        // 点击保存时存储日记
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rate =  rrb1!=null?rrb1.getRating():0;
                String content = et1.getText().toString();
                if (rate == 0 || content.isEmpty()) {
                    Toast.makeText(SevenGUAddDairyActivity.this, "评级或日记为空", Toast.LENGTH_SHORT).show();
                } else {
                    save2db(rate+"", content);
                }
            }
        });
    }

    private void initEditView() {
        if (null != createtime && (!(createtime.isEmpty()))) {
            rrb1.setRating(point);
            et1.setText(content);
        }
    }

    private void save2db(String rate, String content) {
        String milestone = content.substring(content.lastIndexOf(";")+1);
        if (!milestone.contains("over")) {
            milestone = null;
        }
        // 查询历史记录，如果有则更新，没有则新建
        DBManager mDBManager = new DBManager(this);
        String selection_today = "createtime=?";
        String[] args_today = new String[]{new SimpleDateFormat("yyyy-MM-dd").format(new Date())};
        ArrayList<HashMap<String, String>> TableSqlResult_today = mDBManager.Query("t_of_sevenGu", null, null, null, selection_today, args_today, new String[]{"id", "point", "content", "milestone", "createtime", "updatetime"});
        if ((null != createtime && (!(createtime.isEmpty()))) || (0!=TableSqlResult_today.size())) {
            // 更新
            String selection = "createtime=?";
            String[] args = new String[]{(createtime!=null&& (!(createtime.isEmpty())))?createtime:TableSqlResult_today.get(0).get("createtime")};
            ContentValues contentValues = new ContentValues();
            contentValues.clear();
            contentValues.put("point", rate);
            contentValues.put("content", content);
            contentValues.put("updatetime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            if (null != milestone) {
                contentValues.put("milestone", milestone);
            }
            mDBManager.Update("t_of_sevenGu", contentValues, selection, args);
        } else {
            // 新建
            ContentValues contentValues = new ContentValues();
            contentValues.clear();
            contentValues.put("point", rate);
            contentValues.put("content", content);
            contentValues.put("createtime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            contentValues.put("updatetime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            if (null != milestone) {
                contentValues.put("milestone", milestone);
            }
            mDBManager.Insert("t_of_sevenGu", contentValues);
        }
        finish();
    }

    //read Intent information from main_activity
    private void getInformationFromMain(Intent it) {
        createtime = it.getStringExtra("createtime");
        content = it.getStringExtra("content");
        milestone = it.getStringExtra("milestone");
        point = it.getStringExtra("point")!=null?Float.parseFloat(it.getStringExtra("point")):0;
    }
}
