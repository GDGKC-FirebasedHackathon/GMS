package com.hackathon.teamgms.gms.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hackathon.teamgms.gms.utils.DBHelper;

/**
 * Created by hello_DE on 2017-02-18.
 * 내장디비 컨트롤러
 */

public class DBController {

    private Context context = null;
    private DBHelper dbHelper = null;

    public DBController(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        dbHelper = new DBHelper(this.context);
    }

    public void closeDB() {
        dbHelper.close();
    }

    public void createQuestionNum(String questionNum) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("insert into " + DBHelper.TABLE_QUESTION + " VALUES (null, '" + questionNum + "', 0" + ");");

        dbHelper.close();
    }

    public String readQuestionNum() {
        String getQuestionNum = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
/*
        Cursor cursor = db.rawQuery("select * from " + DBHelper.TABLE_QUESTION + " where =" + , null);

        cursor.moveToNext();
        getQuestionNum = cursor.getString(1);
        cursor.close();
*/
        dbHelper.close();

        return getQuestionNum;
    }

    public void updateIsEnd(String questionNum) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues raw = new ContentValues();
        raw.put("isEnd", 1);

        String whereClause = "questionNum=?";
        String whereArgs[] = new String[] { questionNum };

        db.update(DBHelper.TABLE_QUESTION, raw, whereClause, whereArgs);

        dbHelper.close();
    }

}
