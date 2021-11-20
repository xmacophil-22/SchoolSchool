package com.example.schooltest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    String id;
    String key;
    DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id = "";
        key = "";
        ChangeActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //ChangeActivity();
    }

    private void ChangeActivity(){
        /////////////////////////////////////////////////////////////////////change to different Activity weather locked in or not
        try{
            databaseHandler = new DatabaseHandler(this);
            id = databaseHandler.getId();
            key = databaseHandler.getKey();
            ActivityHandler.switchActivity(this, MainActivity.class, id, key);
        }
        catch (Exception e){
            ActivityHandler.switchActivity(this, MainActivity.class, id, key);
        }
    }
}
