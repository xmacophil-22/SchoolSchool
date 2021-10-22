package com.example.schooltest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;

public class DatabaseHandler extends SQLiteOpenHelper {
    //Attributes
    private static final String TAG = "DbHandler";

    private static final String TABLE_NAME = "UserLogin";
    private static final String col1 = "username";
    private static final String col2 = "password";
    public DatabaseHandler(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE "+TABLE_NAME+
                " ("+col1+" VARCHAR(255) ,"+ col2+" VARCHAR(255));";

        try {
            db.execSQL(createTable);
        } catch (Exception e) {
            Log.e("createTableError", String.valueOf(e));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addUserLoginData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, username);
        contentValues.put(col2, password);

        Log.e(TAG, "addData: Adding "+ username + " and " + password + " to" + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        //when insertion is incorrect it will return -1 -> false
        return result != -1;
    }

    public String[] getUserLoginData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT username,password FROM " + TABLE_NAME;
        Cursor Raw_data = db.rawQuery(query, null);
        String[] data = new String[2];
        Raw_data.moveToFirst();
        data[0] = Raw_data.getString(0);    
        data[1] = Raw_data.getString(1);
        db.close();
        Raw_data.close();
        return data; //Returns Login Data Array -> [0] is username [1] is password
    }
}
