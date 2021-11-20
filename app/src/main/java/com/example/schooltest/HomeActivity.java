package com.example.schooltest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    String id, key;
    UntisLoginPopUp untisLoginPopUp;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        key = bundle.getString("key");
        requestQueue = Volley.newRequestQueue(this);
        untisLoginPopUp = new UntisLoginPopUp(this, requestQueue);
        //wenn keine subject daten in der Database zu finden sind
        //untisLoginPopUp.show();



    }


    public void switchToGrades(View view){
        ActivityHandler.switchActivity(this, GradesActivity.class, id, key);
        finish();
    }
}
