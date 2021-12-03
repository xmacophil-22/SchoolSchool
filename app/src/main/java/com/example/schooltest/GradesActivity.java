package com.example.schooltest;

import android.content.DialogInterface;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GradesActivity extends AppCompatActivity {

    String id;
    String key;
    ArrayList<Subject> mySubjects;
    RecyclerView myGrid;
    RecyclerViewAdapter adapter;
    TextView overAllTV, usernameTV;
    DatabaseHandler databaseHandler;
    UntisLoginPopUp untisLoginPopUp;
    PopUpError popUpError;
    RequestQueue requestQueue;
    Snippet snippet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        key = bundle.getString("key");
        snippet = new Snippet();
        requestQueue = Volley.newRequestQueue(this);
        popUpError = new PopUpError(this, "update failed");
        databaseHandler = new DatabaseHandler(this);
        usernameTV = (TextView) findViewById(R.id.usernameTV);
        usernameTV.setText(databaseHandler.getUserLoginData()[0]);
        untisLoginPopUp = new UntisLoginPopUp(this, requestQueue, key);

        /*ArrayList<Integer> c1w = new ArrayList<>();
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
        Subject subjec4 = new Subject("Deutsch", color ,"Hilgarth", 50,50, c1w,c2w,c3w,c4w,c1s,c2s,c3s,c4s,View.VISIBLE);*/
        Subject subject00 = new Subject("", 0 ,"", 1,1, new ArrayList<Integer>(), new ArrayList<Integer>(),
                new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>(),
                new ArrayList<Integer>(),new ArrayList<Integer>(),View.INVISIBLE);
        mySubjects = new ArrayList<>();
        mySubjects.add(subject00);
        mySubjects.add(subject00);
        /*mySubjects.add(subject);
        mySubjects.add(subject2);
        mySubjects.add(subjec1);
        mySubjects.add(subjec3);
        mySubjects.add(subjec4);*/
        myGrid = (RecyclerView) findViewById(R.id.gridListRV);
        myGrid.setLayoutManager(new GridLayoutManager(this, 2));

        MyRequestHandler.volleyRequest(0, popUpError, "https://schoolschooli.herokuapp.com/snippets/" + id, null, requestQueue, key, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                ArrayList<Subject> subjects = MyRequestHandler.getSubjects(result);
                Log.d("gradesActivity", subjects.toString());
                if(subjects.size() == 0){
                    untisLoginPopUp.show();
                    untisLoginPopUp.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            retry();

                        }
                    });
                }
                else {
                    for (Subject s:subjects) {
                        mySubjects.add(s);
                    }
                }
            }
        });
        adapter = new RecyclerViewAdapter(this, this, mySubjects);
        myGrid.setAdapter(adapter);

        overAllTV = (TextView) findViewById(R.id.overAllTV);
        overAllTV.setText(String.valueOf(Subject.overAll));


    }

    @Override
    protected void onStop() {
        super.onStop();
        MyRequestHandler.volleyRequest(2, popUpError, "https://schoolschooli.herokuapp.com/snippets/" + id + "/", Snippet.mySnippet, requestQueue, key, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.d("GradesActivity", "upload success");
            }
        });
    }

    private void retry(){
        MyRequestHandler.volleyRequest(0, popUpError, "https://schoolschooli.herokuapp.com/webuntis/?subjects", null, requestQueue, key, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.d("lol", "ja");
                try{
                    Log.d("state", String.valueOf(result.getInt("done")));
                    if(result.getInt("done") == 0){
                        JSONArray array = result.getJSONArray("Subjects");
                        for (int i =0; i < array.length(); i++) {
                            JSONArray hm = array.getJSONArray(i);
                            Subject subject = new Subject(hm.getString(0), 0, hm.getString(1), 50, 50, new ArrayList<Integer>(),
                                    new ArrayList<Integer>(),
                                    new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(),
                                    new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(), View.VISIBLE);
                            mySubjects.add(subject);
                            snippet.addSubject(i, subject);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        Thread.sleep(2000);
                        retry();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}
