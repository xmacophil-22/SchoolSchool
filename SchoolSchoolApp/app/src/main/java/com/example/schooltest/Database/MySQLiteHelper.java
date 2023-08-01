package com.example.schooltest.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONObject;

import java.sql.Timestamp;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "com.example.schooltest.finalData";

    //////////////////////////////////////////////////////////////Befehle für die Kreation der Datenbank (nur einmal benutzt)

    public static final String SQL_CREATE_USERDATA = "CREATE TABLE " + MyContractClass.UserdataTable.TABLE_NAME + " ( " +
            MyContractClass.UserdataTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
            " , " + MyContractClass.UserdataTable.COL_USERNAME + " TEXT " +
            " , " + MyContractClass.UserdataTable.COL_KEY + " TEXT " +
            " , " + MyContractClass.UserdataTable.COL_SNIPPETID + " TEXT )";

    public static final String SQL_CREATE_SNIPPET = "CREATE TABLE " + MyContractClass.SnippetTable.TABLE_NAME + " ( " +
            MyContractClass.SnippetTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
            " , " + MyContractClass.SnippetTable.COL_SIPPET + " TEXT " +
            " , " + MyContractClass.SnippetTable.COL_TIMESTAMP + " TEXT )";

    /////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////testet ob bereits eine Datenbank mit dem DATABASE_NAME existiert, wenn ja, dann keine neue
    //sonst neue Datenbank
    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERDATA);
        db.execSQL(SQL_CREATE_SNIPPET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /////////////////////////////////////////////////////////////////////////legt neuen Benutzer in der richtigen Zeile an

    public long add_User(String username, String key, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long newID;

        if(getTableLength(MyContractClass.UserdataTable.TABLE_NAME) == 0){ContentValues values = new ContentValues();
            values.put(MyContractClass.UserdataTable.COL_USERNAME, username);
            values.put(MyContractClass.UserdataTable.COL_KEY, key);
            values.put(MyContractClass.UserdataTable.COL_SNIPPETID, id);

            newID = db.insert(MyContractClass.UserdataTable.TABLE_NAME, null, values);}
        else {
            String sSQL = "UPDATE " + MyContractClass.UserdataTable.TABLE_NAME + " "
                    + "SET [" + MyContractClass.UserdataTable.COL_USERNAME + "] = '"
                    + username + "'"
                    + " WHERE " + MyContractClass.SnippetTable._ID + "=" + 1;
            db.execSQL(sSQL);
            Log.d("usernametest",username);
            db = this.getWritableDatabase();
            sSQL = "UPDATE " + MyContractClass.UserdataTable.TABLE_NAME + " "
                    + "SET [" + MyContractClass.UserdataTable.COL_KEY + "] = '"
                    + key + "'"
                    + " WHERE " + MyContractClass.SnippetTable._ID + "=" + 1;
            db.execSQL(sSQL);
            Log.d("keytest",key);
            db = this.getWritableDatabase();
            sSQL = "UPDATE " + MyContractClass.UserdataTable.TABLE_NAME + " "
                    + "SET [" + MyContractClass.UserdataTable.COL_SNIPPETID + "] = '"
                    + id + "'"
                    + " WHERE " + MyContractClass.SnippetTable._ID + "=" + 1;
            db.execSQL(sSQL);
            Log.d("idtest",id);
            newID = 1;
        }


        return newID;
    }
    //////////////////////////////////////////////////////ändert oder erstellt Snippet (Daten des Benutzers) je nachdem
    public long add_Snippet(JSONObject snippet){
        SQLiteDatabase db = this.getWritableDatabase();
        long newID;
        if(getTableLength(MyContractClass.SnippetTable.TABLE_NAME) == 0){
            ContentValues values = new ContentValues();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            values.put(MyContractClass.SnippetTable.COL_SIPPET, snippet.toString());
            values.put(MyContractClass.SnippetTable.COL_TIMESTAMP, timestamp.toString());

            newID = db.insert(MyContractClass.SnippetTable.TABLE_NAME, null, values);
        }
        else{
            change_Snippet(snippet, 1);
            newID = 1;
        }

        return newID;
    }

    /////////////////////////////////////////////////////////////löscht den Nutzer zum Ausloggen

    public void delete_User(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        /*String aSQL = "DELETE FROM " + MyContractClass.UserdataTable.TABLE_NAME + " "
                + " WHERE " +
                " " + MyContractClass.UserdataTable._ID + "=" + id;
        db.execSQL(aSQL);*/
        String sSQL = "UPDATE " + MyContractClass.UserdataTable.TABLE_NAME + " "
                + "SET [" + MyContractClass.UserdataTable.COL_USERNAME + "] = '"
                + "" + "'"
                + " WHERE " + MyContractClass.SnippetTable._ID + "=" + id;
        db.execSQL(sSQL);
    }

    ////////////////////////////////////////////////ändert das Snippet

    public void change_Snippet(JSONObject snippet, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sSQL = "UPDATE " + MyContractClass.SnippetTable.TABLE_NAME + " "
                + "SET [" + MyContractClass.SnippetTable.COL_SIPPET + "] = '"
                + snippet.toString() + "'"
                + " WHERE " + MyContractClass.SnippetTable._ID + "=" + id;
        db.execSQL(sSQL);
        db = this.getWritableDatabase();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        sSQL = "UPDATE " + MyContractClass.SnippetTable.TABLE_NAME + " "
                + "SET [" + MyContractClass.SnippetTable.COL_TIMESTAMP + "] = '"
                + timestamp.toString() + "'"
                + " WHERE " + MyContractClass.SnippetTable._ID + "=" + id;
        db.execSQL(sSQL);

    }
    /////////////////////////////////////////////////gibt ein JSONObject Array zurück, Stelle 0 ist das Snippet, 1 der Timestamp
    public JSONObject[] getSnippet(){
        Cursor cursor = get_Table(MyContractClass.SnippetTable.TABLE_NAME);
        String snippet = "";
        String timestamp = "";
        JSONObject[] objects = new JSONObject[2];
        int col_snippet_ID = cursor.getColumnIndex(MyContractClass.SnippetTable.COL_SIPPET);
        int col_timestamp_ID = cursor.getColumnIndex(MyContractClass.SnippetTable.COL_TIMESTAMP);
        while (cursor.moveToNext()){
            snippet = cursor.getString(col_snippet_ID);
            timestamp = cursor.getString(col_timestamp_ID);
        }
        cursor.close();
        try {
            objects[0] = new JSONObject(snippet);
            JSONObject test = new JSONObject();
            test.put("timestamp", timestamp);
            objects[1] = test;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return objects;
    }
    /////////////////////////////////////////gibt die Benutzerdaten zurück
    public String getUserData(String culumnName){
        Cursor cursor = get_Table(MyContractClass.UserdataTable.TABLE_NAME);
        String data = "";
        int col_data_ID = cursor.getColumnIndex(culumnName);
        while (cursor.moveToNext()){
            data = cursor.getString(col_data_ID);
        }
        cursor.close();
        Log.d("userdata", data);
        return data;
    }
    ///////////////////////////////////////////////////////gibt eine Tabelle der Datenbank zurück
    private Cursor get_Table(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        String sSQL = "SELECT * FROM " + tableName;
        Cursor data = db.rawQuery(sSQL, null);
        return data;
    }

    ///////////////////////////////////////////////////gibt die Länge einer Tabelle zurück, um zu Testen ob 0

    public int getTableLength(String tableName){
        Cursor cursor = get_Table(tableName);
        int i = 0;
        while (cursor.moveToNext()){
            i++;
        }
        return i;
    }


}
