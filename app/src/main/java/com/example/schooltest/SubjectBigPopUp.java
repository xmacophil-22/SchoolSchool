package com.example.schooltest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class SubjectBigPopUp extends Dialog {

    private ArrayList<Integer> c1W;
    private ArrayList<Integer> c2W;
    private ArrayList<Integer> c3W;
    private ArrayList<Integer> c4W;
    private ArrayList<Integer> c1S;
    private ArrayList<Integer> c2S;
    private ArrayList<Integer> c3S;
    private ArrayList<Integer> c4S;
    private String name, teacher;
    private TextView nameTV, teacherTV;
    private ListView bSpeakSV, bWriteSV;
    private CardView cardView;
    int color;

    public SubjectBigPopUp(Activity a){
        super(a);
        c1W = new ArrayList<>();
        c1W.add(0);
        c2W = new ArrayList<>();
        c2W.add(0);
        c3W = new ArrayList<>();
        c3W.add(0);
        c4W = new ArrayList<>();
        c4W.add(0);
        c1S = new ArrayList<>();
        c1S.add(0);
        c2S = new ArrayList<>();
        c2S.add(0);
        c3S = new ArrayList<>();
        c3S.add(0);
        c4S = new ArrayList<>();
        c4S.add(0);
        name = "";
        teacher = "";
        color = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.subject_big_pop_up);
        nameTV = (TextView) findViewById(R.id.bNameTV);
        teacherTV = (TextView) findViewById(R.id.bTeacherTV);
        bSpeakSV = (ListView) findViewById(R.id.bSpeakLV);
        bWriteSV = (ListView) findViewById(R.id.bWriteLV);
        cardView = (CardView) findViewById(R.id.bCardView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        cardView.setCardBackgroundColor(color);
        nameTV.setText(name);
        teacherTV.setText(teacher);
        bSpeakSV.setAdapter(new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_list_item_1, c1S));
        bWriteSV.setAdapter(new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_list_item_1, c1W));
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setSubjectName(String name) {
        this.name = name;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setC1W(ArrayList<Integer> c1W) {
        //this.c1W = c1W;
        this.c1W.clear();
        for (int i:c1W) {
            this.c1W.add(i);
        }
    }

    public void setC2W(ArrayList<Integer> c2W) {
        this.c2W = c2W;
    }

    public void setC3W(ArrayList<Integer> c3W) {
        this.c3W = c3W;
    }

    public void setC4W(ArrayList<Integer> c4W) {
        this.c4W = c4W;
    }

    public void setC1S(ArrayList<Integer> c1S) {
        //this.c1S = c1S;
        this.c1S.clear();
        for (int i:c1S) {
            this.c1S.add(i);
        }
    }

    public void setC2S(ArrayList<Integer> c2S) {
        this.c2S = c2S;
    }

    public void setC3S(ArrayList<Integer> c3S) {
        this.c3S = c3S;
    }

    public void setC4S(ArrayList<Integer> c4S) {
        this.c4S = c4S;
    }
}
