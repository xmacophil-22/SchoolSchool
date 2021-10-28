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

    private static final String User_Login_Data = "User_Loging_Table";
    //private static final String school_Data = "UserLoginDatabase";
    private static final String[] col = {"username", "password", "authKey", "snippetId"};

    public DatabaseHandler(Context context){
        super(context, User_Login_Data, null, 12);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE "+User_Login_Data+
                " ("
                + col[0] + " VARCHAR(255) ,"
                + col[1] + " VARCHAR(255) ,"
                + col[2] + " VARCHAR(255) ,"
                + col[3] + " VARCHAR(255)"
                + ");";

        try {
            db.execSQL(createTable);
        } catch (Exception e) {
            Log.e("createTableError", String.valueOf(e));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS " + User_Login_Data);
        onCreate(db);
    }

    public boolean addUserLoginData(String username, String password, String authKey, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col[0],username);
        contentValues.put(col[1], password);
        contentValues.put(col[2], authKey);
        contentValues.put(col[3], id);

        long result = db.insert(User_Login_Data, null, contentValues);

        db.close();
        //when insertion is incorrect it will return -1 -> false
        return result != -1;
    }

    public String getKey(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+col[2]+" FROM " + User_Login_Data;

        Cursor Raw_data = db.rawQuery(query, null);

        Raw_data.moveToFirst();
        String data = Raw_data.getString(0);
        db.close();
        Raw_data.close();
        return data;
    }

    public String getId(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+col[3]+" FROM " + User_Login_Data;

        Cursor Raw_data = db.rawQuery(query, null);

        Raw_data.moveToFirst();
        String data = Raw_data.getString(0);
        Log.e("UserKey: ", data);
        db.close();
        Raw_data.close();
        return data;
    }

    public String[] getUserLoginData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "
            +col[0]+
            ","
            +col[1]+
        " FROM " + User_Login_Data;
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
