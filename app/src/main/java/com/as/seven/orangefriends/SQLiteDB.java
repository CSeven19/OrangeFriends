package com.as.seven.orangefriends;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Seven on 2020/01/01.
 */

public class SQLiteDB extends SQLiteOpenHelper {
    String sql = null;

    public SQLiteDB(Context context) {
        super(context, "sqlitedb3.db", null, 1);
    }

    public SQLiteDB(Context context, String sql) {
        super(context, "sqlitedb3.db", null, 1);
        this.sql = sql;
    }

    //创建数据库调用这个类 所有建表语句放此处.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // MMCK
        String sql_draw = "CREATE TABLE if not exists t_of_drawlist( id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT DEFAULT '', content TEXT DEFAULT '', image TEXT DEFAULT '', createtime TEXT DEFAULT '', updatetime TEXT DEFAULT '',drawtime TEXT DEFAULT 0)";
        db.execSQL(sql_draw);
        // SMBQ
        String sql_note = "CREATE TABLE if not exists t_of_notelist( id INTEGER PRIMARY KEY AUTOINCREMENT, tag INTEGER DEFAULT 0, mainText TEXT DEFAULT '', alarm TEXT DEFAULT '', createtime TEXT DEFAULT '', updatetime TEXT DEFAULT '')";
        db.execSQL(sql_note);
        // SevenGU
        String sql_sevenGu = "CREATE TABLE if not exists t_of_sevenGu( id INTEGER PRIMARY KEY AUTOINCREMENT, point TEXT DEFAULT '', content TEXT DEFAULT '', milestone TEXT DEFAULT '', createtime TEXT DEFAULT '', updatetime TEXT DEFAULT '')";
        db.execSQL(sql_sevenGu);
    }

    // 更新版本时候使用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

