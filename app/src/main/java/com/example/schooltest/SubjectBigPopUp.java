package com.example.schooltest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectBigPopUp extends Dialog {

    protected RecyclerViewAdapter adapter;
    protected int myCurrent;
    protected Activity activity;
    protected int percWrite, percSpeak;

    protected ArrayList<Integer> c1W;
    protected ArrayList<Integer> c2W;
    protected ArrayList<Integer> c3W;
    protected ArrayList<Integer> c4W;
    protected ArrayList<Integer> c1S;
    protected ArrayList<Integer> c2S;
    protected ArrayList<Integer> c3S;
    protected ArrayList<Integer> c4S;
    protected String[] courses;
    protected String name, teacher;
    protected TextView nameTV, teacherTV;
    protected ListView bSpeakSV, bWriteSV;
    protected CardView cardView;
    protected Spinner dropDownS;
    int color;
    HashMap<Integer, ArrayList<Integer>> sHM;
    HashMap<Integer, ArrayList<Integer>> wHM;

    public SubjectBigPopUp(Activity a){
        super(a);
        sHM = new HashMap<>();
        wHM = new HashMap<>();
        adapter = null;
        activity = a;
        courses = new String[]{"1", "2", "3", "4"};
        c1W = new ArrayList<>();
        c2W = new ArrayList<>();
        c3W = new ArrayList<>();
        c4W = new ArrayList<>();
        c1S = new ArrayList<>();
        c2S = new ArrayList<>();
        c3S = new ArrayList<>();
        c4S = new ArrayList<>();
        c1S.add(0);
        c2S.add(0);
        c3S.add(0);
        c4S.add(0);
        c1W.add(0);
        c2W.add(0);
        c3W.add(0);
        c4W.add(0);

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
        dropDownS = (Spinner) findViewById(R.id.dropDownS);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, courses);
        dropDownS.setAdapter(adapter);
        sHM.put(0, c1S);
        sHM.put(1, c2S);
        sHM.put(2, c3S);
        sHM.put(3, c4S);
        wHM.put(0, c1W);
        wHM.put(1, c2W);
        wHM.put(2, c3W);
        wHM.put(3, c4W);
        Log.d("SBPU", sHM.get(0).toString());

    }

    @Override
    protected void onStart() {
        super.onStart();
        cardView.setCardBackgroundColor(color);
        nameTV.setText(name);
        teacherTV.setText(teacher);
        int adapterPos = dropDownS.getSelectedItemPosition();
        setArrayAdapter(sHM.get(adapterPos), wHM.get(adapterPos));

        /////////////////////////////////////////////////////////////////////////////////////////////wenn ein anderes Halbjahr ausgewählt wird ändern sich die Notenlisten
        dropDownS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setArrayAdapter(sHM.get(i), wHM.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////setzt die anzuzeigenden Notenlisten
    public void setArrayAdapter(ArrayList<Integer> mySGrades, ArrayList<Integer> myWGrades){
        bSpeakSV.setAdapter(new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_list_item_1, mySGrades));
        bWriteSV.setAdapter(new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_list_item_1, myWGrades));
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
        this.c1W = new ArrayList<>(c1W);
        wHM.put(0, this.c1W);
    }

    public void setC2W(ArrayList<Integer> c2W) {
        this.c2W = new ArrayList<>(c2W);
        wHM.put(1, this.c2W);
    }

    public void setC3W(ArrayList<Integer> c3W) {
        this.c3W = new ArrayList<>(c3W);
        wHM.put(2, this.c3W);
    }

    public void setC4W(ArrayList<Integer> c4W) {
        this.c4W = new ArrayList<>(c4W);
        wHM.put(3, this.c4W);
    }

    public void setC1S(ArrayList<Integer> c1S) {
        this.c1S = new ArrayList<>(c1S);
        sHM.put(0, this.c1S);
    }

    public void setC2S(ArrayList<Integer> c2S) {
        this.c2S = new ArrayList<>(c2S);
        sHM.put(1, this.c2S);
    }

    public void setC3S(ArrayList<Integer> c3S) {
        this.c3S = new ArrayList<>(c3S);
        sHM.put(2, this.c3S);
    }

    public void setC4S(ArrayList<Integer> c4S) {
        this.c4S = new ArrayList<>(c4S);
        sHM.put(3, this.c4S);
    }

    public int getMyCurrent() {
        return myCurrent;
    }

    public void setMyCurrent(int myCurrent) {
        this.myCurrent = myCurrent;
    }

    public void setAdapter(RecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    public void setPercWrite(int percWrite) {
        this.percWrite = percWrite;
    }

    public void setPercSpeak(int percSpeak) {
        this.percSpeak = percSpeak;
    }
}
