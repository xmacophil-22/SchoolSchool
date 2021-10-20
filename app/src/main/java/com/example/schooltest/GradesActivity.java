package com.example.schooltest;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GradesActivity extends AppCompatActivity {

    String id;
    String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        key = bundle.getString("key");

    }
}
