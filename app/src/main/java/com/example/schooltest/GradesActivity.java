package com.example.schooltest;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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

        Subject subject00 = new Subject("", 0 ,"", 1,1, new ArrayList<Integer>(), new ArrayList<Integer>(),
                new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>(),
                new ArrayList<Integer>(),new ArrayList<Integer>(),View.INVISIBLE);
        mySubjects = new ArrayList<>();
        mySubjects.add(subject00);
        mySubjects.add(subject00);
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
                    int x = 2;
                    for (Subject s:subjects) {
                        mySubjects.add(s);
                        snippet.addSubject(x-2, mySubjects.get(x));
                        x++;
                    }
                    adapter.notifyDataSetChanged();
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
                Log.d("GradesActivity", Snippet.mySnippet.toString());
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
