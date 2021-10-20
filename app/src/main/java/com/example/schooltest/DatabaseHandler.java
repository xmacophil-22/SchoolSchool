package com.example.schooltest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.Locale;

public class DatabaseHandler {
    private SQLiteDatabase database;
    private String DatabaseName;

    public void create(String DatabaseName){ //DataBaseName must contain .db at the end
        this.DatabaseName = DatabaseName;
        database = SQLiteDatabase.openOrCreateDatabase(DatabaseName,null);
        database.close();
    }

    private void openDatabase(){
        database = SQLiteDatabase.openOrCreateDatabase(DatabaseName, null);
    }

    public void createUserData(String username, String password){
        openDatabase();
        database.execSQL("CREATE TABLE " + "UserLoginData" + "(username TEXT, password TEXT)");
        database.execSQL("INSERT INTO " + "UserLoginData" + " VALUES(username,password)");
        database.close();
    }

    public String[] getLoginData(){
        String[] UserData = new String[2];
        Cursor cursor = database.rawQuery("SELECT * FROM " + "UserLoginData", null);
        cursor.moveToFirst();
        UserData[0] = cursor.getString(0);
        UserData[1] = cursor.getString(1);
        cursor.close();
        database.close();

        return UserData;
        //return
    }
}
