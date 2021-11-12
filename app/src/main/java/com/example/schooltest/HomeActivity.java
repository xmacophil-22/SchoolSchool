package com.example.schooltest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    String id;
    String key;
    HashMap<Integer, ArrayList<Integer>> myHM;
    ArrayList<Integer> lol;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        key = bundle.getString("key");
        lol = new ArrayList<>();

    }


    public void switchToGrades(View view){
        ActivityHandler.switchActivity(this, GradesActivity.class, id, key);
        finish();
    }
}
