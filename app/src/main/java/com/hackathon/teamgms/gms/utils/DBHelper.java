package com.hackathon.teamgms.gms.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hello_DE on 2017-02-18.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = SQLiteOpenHelper.class.getSimpleName();

    public final static String TABLE_QUESTION = "question_table";
    private final static String DATABASE_NAME = "gms_db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_QUESTION +
                "( _id integer primary key autoincrement, questionNum text, isEnd boolean)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
