package com.example.schooltest;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schooltest.Database.MyContractClass;
import com.example.schooltest.Database.MySQLiteHelper;

public class StartActivity extends AppCompatActivity {

    String id;
    String key;
    MySQLiteHelper db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new MySQLiteHelper(this);
        id = db.getUserData(MyContractClass.UserdataTable.COL_SNIPPETID);
        key = db.getUserData(MyContractClass.UserdataTable.COL_KEY);
        ChangeActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //ChangeActivity();
    }

    private void ChangeActivity(){
        /////////////////////////////////////////////////////////////////////change to different Activity weather locked in or not

        if(db.getUserData(MyContractClass.UserdataTable.COL_USERNAME).equals("")){
                ActivityHandler.switchActivity(this, MainActivity.class, id, key);
            }
            else{

            ActivityHandler.switchActivity(this, Main.class, id, key);
            }
        }
    }
