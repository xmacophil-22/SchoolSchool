package com.example.schooltest;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GradesActivity extends AppCompatActivity {

    String id;
    String key;
    ArrayList<Subject> mySubjects;
    RecyclerView myGrid;
    RecyclerViewAdapter adapter;
    TextView overAllTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        key = bundle.getString("key");

        ArrayList<Integer> c1w = new ArrayList<>();
        c1w.add(10);
        c1w.add(12);
        ArrayList<Integer> c2w = new ArrayList<>();
        c2w.add(15);
        ArrayList<Integer> c3w = new ArrayList<>();
        c3w.add(15);
        ArrayList<Integer> c4w = new ArrayList<>();
        c4w.add(15);
        ArrayList<Integer> c1s = new ArrayList<>();
        c1s.add(15);
        ArrayList<Integer> c2s = new ArrayList<>();
        c2s.add(15);
        ArrayList<Integer> c3s = new ArrayList<>();
        c3s.add(15);
        ArrayList<Integer> c4s = new ArrayList<>();
        c4s.add(15);



        int color = Color.rgb(161,84,84);
        int color2 = Color.rgb(0,255,0);
        Subject subject2 = new Subject("Mathe", color2, "Malenica", 60,40, c1w,c2w,c3w,c4w,c1s,c2s,c3s,c4s,View.VISIBLE);
        Subject subject = new Subject("Deutsch", color ,"Hilgarth", 50,50, c1w,c2w,c3w,c4w,c1s,c2s,c3s,c4s,View.VISIBLE);
        Subject subjec1 = new Subject("Deutsch", color ,"Hilgarth", 50,50, c1w,c2w,c3w,c4w,c1s,c2s,c3s,c4s,View.VISIBLE);
        Subject subjec3 = new Subject("Deutsch", color ,"Hilgarth", 50,50, c1w,c2w,c3w,c4w,c1s,c2s,c3s,c4s,View.VISIBLE);
        Subject subjec4 = new Subject("Deutsch", color ,"Hilgarth", 50,50, c1w,c2w,c3w,c4w,c1s,c2s,c3s,c4s,View.VISIBLE);
        Subject subject00 = new Subject("", color ,"", 1,1, c1w,c2w,c3w,c4w,c1s,c2s,c3s,c4s,View.INVISIBLE);
        mySubjects = new ArrayList<>();
        mySubjects.add(subject00);
        mySubjects.add(subject00);
        mySubjects.add(subject);
        mySubjects.add(subject2);
        mySubjects.add(subjec1);
        mySubjects.add(subjec3);
        mySubjects.add(subjec4);
        myGrid = (RecyclerView) findViewById(R.id.gridListRV);
        myGrid.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new RecyclerViewAdapter(this, this, mySubjects);
        myGrid.setAdapter(adapter);

        overAllTV = (TextView) findViewById(R.id.overAllTV);
        overAllTV.setText(String.valueOf(Subject.overAll));

    }

    public RecyclerViewAdapter getAdapter() {
        return adapter;
    }
}
