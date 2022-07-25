package com.example.homeworknote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.Date;

public class DB {
    private static String DATABASE_NAME="notes.db";
    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_TABLE="notes";
    private static final String NOTES_TABLE_CREATE =
            "CREATE TABLE notes(_id INTEGER PRIMARY KEY,title TEXT, created INTEGER);";
    private static Context mCtx = null;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;




    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {//CAN CREATE TABLE
            db.execSQL(NOTES_TABLE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //若資料庫有改版的話, LIKE DATABASE_VERSION=2; WE STILL NEED TO OVERRIDE THIS METHOD, IT'S PEREMPTORY
        }
    }

    public DB(Context ctx) {
        mCtx = ctx;
    }

    public void openDB() {
        dbHelper = new DatabaseHelper(mCtx); //他只是一個協助者, 負責幫忙開啟資料庫
        db=dbHelper.getWritableDatabase();//建立或是開啟資料庫,若不存在就是建立
    }

    public Cursor readAll(){
        return db.rawQuery("SELECT*FROM "+DATABASE_TABLE,null);
    }

    public boolean insert(String title, String created) throws SQLException {
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("created", created);
        return db.insertOrThrow(DATABASE_TABLE,null,cv)>0;
    }

    public int delete(long id){
        return db.delete(DATABASE_TABLE,"_id= "+id,null);
    }

    public int update(String title, String created,long id){
        ContentValues cv = new ContentValues();
        cv.put("title",title);
        cv.put("created",created);
        return db.update(DATABASE_TABLE,cv,"_id="+id,null);
    }
//    public void update(Revise revise, long id) {
//        ContentValues cv = new ContentValues();
//        cv.put("title",title);
//        cv.put("created",created);
//        return db.update(DATABASE_TABLE,cv,"_id="+id,null);
//    }
}
