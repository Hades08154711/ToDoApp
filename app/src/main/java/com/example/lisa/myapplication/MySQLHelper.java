package com.example.lisa.myapplication;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lisa on 15.03.2016.
 */
public class MySQLHelper extends SQLiteOpenHelper {
    private static final String TABLE_USER = "user";
    private static final String TABLE_TODO = "todo";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_NAME = "Name";
    private static final String DATABASE_NAME = "myNewDatabase.db";
    private static final int DATABASE_VERSION = 1;
    // Create Database SQLite String
    private static final String DATABASE_CREATE_USER =
            "create table "
                    + TABLE_USER
                    + "(Id integer primary key autoincrement, "
                    + "Name text,"
                    +"Username text,"
                    +"Password text"
                    + ")";

    private static final String DATABASE_CREATE_TODO=
            "create table "
                    + TABLE_TODO
                    + "(Id integer primary key autoincrement, "
                    + "Title text,"
                    +"Info text,"
                    +"Date date,"
                    +"Time time"
                    + ")";

    public MySQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {
        try {
            database.execSQL(DATABASE_CREATE_USER);
            database.execSQL(DATABASE_CREATE_TODO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
