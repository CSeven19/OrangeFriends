package com.as.seven.orangefriends;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SevenGUShowDariesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    //list to store all the memo
    private List<OneRecord> memolist = new ArrayList<>();

    //adapter
    RecordAdapter adapter;

    //main ListView
    ListView lv;

    private DBManager mDBManager = null;

    private static int EDIT_CODE = 2;
    private static int ADD_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven_gushow_daries);
        // 设置toolbar
        setToolBar();

        // init
        init();

//        deleteTable();

        initTable();

        // 载入历史数据
        loadHistoryData();

        // init memolist
        initMemoList();
    }

    @SuppressLint("RestrictedApi")
    private void setToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.icon_listview2);
        toolbar.setTitle("SevenGroupUpのレコード");
        toolbar.setSubtitle("レコードの一覧");
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

    private void init() {
    }

    private void deleteTable() {
        mDBManager = new DBManager();
        String sql = "DROP TABLE if exists t_of_sevenGu";
        mDBManager.DeleteTable(this, sql);
    }

    private void initTable() {
        mDBManager = new DBManager(this);
    }

    private void initMemoList() {
        adapter = new RecordAdapter(SevenGUShowDariesActivity.this, R.layout.memo_list, memolist);
        lv = findViewById(R.id.sevengulv1);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
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

    private void loadHistoryData() {
        ArrayList<HashMap<String, String>> TableSqlResult = mDBManager.Query("t_of_sevenGu", null, null, "createtime Desc", null, null, new String[]{"id", "point", "content", "milestone", "createtime", "updatetime"});
        if (null != TableSqlResult && 0 != TableSqlResult.size()) {
            memolist.clear();
            for (HashMap<String, String> row : TableSqlResult) {
                OneRecord temp = new OneRecord(row.get("id"), row.get("point"), row.get("content"), row.get("milestone"), row.get("createtime"), row.get("updatetime"));
                memolist.add(temp);
            }
        }
    }

    //get current date in XX/XX format
    private String getCurrentDate(Calendar c) {
        return c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH);
    }

    //get current time in XX:XX format
    private String getCurrentTime(Calendar c) {
        String current_time = "";
        if (c.get(Calendar.HOUR_OF_DAY) < 10)
            current_time = current_time + "0" + c.get(Calendar.HOUR_OF_DAY);
        else current_time = current_time + c.get(Calendar.HOUR_OF_DAY);
        current_time = current_time + ":";
        if (c.get(Calendar.MINUTE) < 10) current_time = current_time + "0" + c.get(Calendar.MINUTE);
        else current_time = current_time + c.get(Calendar.MINUTE);
        return current_time;
    }

    // 点击后编辑
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent(this, SevenGUAddDairyActivity.class);
        OneRecord memo = memolist.get(position);
        transportInformationToEdit(it, memo);
        startActivityForResult(it, EDIT_CODE);
    }

    private Memo getMemoWithNum(int num) {
        String selection = "id=?";
        String[] args = new String[]{num + ""};
        ArrayList<HashMap<String, String>> TableSqlResult = mDBManager.Query("t_of_sevenGu", null, null, null, selection, args, new String[]{"id", "tag", "alarm", "mainText", "updatetime"});
        Memo memo = new Memo();
        if (null != TableSqlResult) {
            for (HashMap<String, String> row : TableSqlResult) {
                memo.setId(Integer.parseInt(row.get("id")));
                memo.setTag(Integer.parseInt(row.get("tag")));
                memo.setAlarm(row.get("alarm"));
                memo.setMainText(row.get("mainText"));
                memo.setTextDate(row.get("updatetime").substring(0, 10));
                memo.setTextTime(row.get("updatetime").substring(10));
                break;
            }
        }
        return memo;
    }

    private void transportInformationToEdit(Intent it, OneRecord record) {
        it.putExtra("id", record.getId());
        it.putExtra("point", record.getPoint());
        it.putExtra("content", record.getContent());
        it.putExtra("milestone", record.getMilestone());
        it.putExtra("createtime", record.getCreatetime());
        it.putExtra("updatetime", record.getUpdatetime());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        int n = memolist.size();
        String selection = "id=?";
        String[] args = new String[]{memolist.get(position).getId()};
        mDBManager.Delete("t_of_sevenGu", selection, args);
        memolist.remove(position);
        adapter.notifyDataSetChanged();
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 新增或更新后刷新listview.
        if (requestCode == ADD_CODE || requestCode == EDIT_CODE) {
            loadHistoryData();
            adapter.notifyDataSetChanged();
        }
    }
}
