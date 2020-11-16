package com.as.seven.orangefriends;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBManager {
    SQLiteDB sqldb;
    DBManager() {
    }
    DBManager(Context context) {
        this.sqldb = new SQLiteDB(context);
    }
    // 1 CRUD
    // 1.1 init create table.
    public SQLiteDB CreateTable(Context context, String sql){
        // 创建table.
        this.sqldb = new SQLiteDB(context, sql);
        return sqldb;
    }

    public SQLiteDB DeleteTable(Context context, String sql){
        // 创建table.
        this.sqldb = new SQLiteDB(context, sql);
        return sqldb;
    }

    // 1.2 insert
    public void Insert(String tablename, ContentValues cv) {
        SQLiteDatabase sqlwriter = sqldb.getWritableDatabase();
        sqlwriter.insert(tablename, null, cv);
        sqlwriter.close();
    }

//    // 1.3 query
//    public ArrayList<ArrayList<HashMap<String, String>>> Query(String tablename, String gb, String having, String ob, String... columnnames) {
//        ArrayList<ArrayList<HashMap<String, String>>> result = new ArrayList<ArrayList<HashMap<String, String>>>();
//        ArrayList<HashMap<String, String>> row = null;
//        // 新建可读数据库对象
//        SQLiteDatabase DBReader = sqldb.getReadableDatabase();
//        // 获取游标c
//        Cursor c = DBReader.query(tablename, columnnames, null, null, gb, having, ob);
//        // 循环下一个
//        if (c.moveToNext()) {
//            row = new ArrayList<HashMap<String, String>>();
//            for (String column : columnnames) {
//                HashMap<String, String> tempHM = new HashMap<String, String>();
//                tempHM.put(column, c.getString(c.getColumnIndex(column)));
//                row.add(tempHM);
//            }
//            result.add(row);
//        }
//        DBReader.close();
//        return result;
//    }

    // 1.3.1 条件检索
    public ArrayList<HashMap<String, String>> Query(String tablename, String gb, String having, String ob, String selection, String[] selectionargs, String[] columnnames) {
        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> row = null;
        // 新建可读数据库对象
        SQLiteDatabase DBReader = sqldb.getReadableDatabase();
        // 获取游标c
        Cursor c = DBReader.query(tablename, columnnames, selection, selectionargs, gb, having, ob);
        // 循环下一个
//        if (c.moveToNext()) {
        while (c.moveToNext()) {
            row = new HashMap<String, String>();
            for (String column : columnnames) {
                row.put(column, c.getString(c.getColumnIndex(column)));
            }
            result.add(row);
        }
        DBReader.close();
        return result;
    }

    // 1.4 delete
    public void Delete(String tablename, String whereClause, String[] params) {
        SQLiteDatabase sqlwriter = sqldb.getWritableDatabase();
        sqlwriter.delete(tablename, whereClause, params);
        sqlwriter.close();
    }

    // 1.5 update
    public void Update(String tablename, ContentValues cv, String whereClause, String[] whereArgs) {
        SQLiteDatabase sqlwriter = sqldb.getWritableDatabase();
        sqlwriter.update(tablename, cv, whereClause,whereArgs);
        sqlwriter.close();
    }

//    public boolean IsTableExist(Context context, String tablename) {
////        this.sqldb = new SQLiteDB(context, "CREATE TABLE user( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT DEFAULT ' ', sex TEXT DEFAULT ' ', logincounts TEXT DEFAULT 0)");
//        this.sqldb = new SQLiteDB(context);
//        SQLiteDatabase DBReader = sqldb.getReadableDatabase();
//        Cursor c = DBReader.query(tablename, null, null, null, null, null, null);
//        if (c.moveToNext()) {
//            return true;
//        }
//        return false;
//    }
}
