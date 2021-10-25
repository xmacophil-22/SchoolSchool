package com.example.schooltest;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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



        int color = Color.rgb(255,0,0);
        int color2 = Color.rgb(0,255,0);
        Subject subject2 = new Subject("Mathe", color2, "Malenica", 60,40, c1w,c2w,c3w,c4w,c1s,c2s,c3s,c4s,View.VISIBLE);
        Subject subject = new Subject("Deutsch", color ,"Hilgarth", 50,50, c1w,c2w,c3w,c4w,c1s,c2s,c3s,c4s,View.VISIBLE);
        Subject subject00 = new Subject("", color ,"", 1,1, c1w,c2w,c3w,c4w,c1s,c2s,c3s,c4s,View.INVISIBLE);
        mySubjects = new ArrayList<>();
        mySubjects.add(subject00);
        mySubjects.add(subject00);
        mySubjects.add(subject);
        mySubjects.add(subject2);
        mySubjects.add(subject2);
        mySubjects.add(subject);
        mySubjects.add(subject2);
        mySubjects.add(subject2);
        myGrid = (RecyclerView) findViewById(R.id.gridListRV);
        myGrid.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new RecyclerViewAdapter(this, mySubjects);
        myGrid.setAdapter(adapter);

        //myGrid = (GridView) findViewById(R.id.subjectsGV);
        //CustomGridAdapter customGridAdapter = new CustomGridAdapter(getApplicationContext(), mySubjects);
        //myGrid.setAdapter(customGridAdapter);
        /*myGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("clicked", mySubjects.get(i).getName());
            }
        });*/

    }
}
