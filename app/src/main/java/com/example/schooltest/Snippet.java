package com.example.schooltest;

import android.app.Activity;
import android.util.Log;

import com.example.schooltest.databinding.ActivityRegisterBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class Snippet {
    public static JSONObject mySnippet = new JSONObject();
    private JSONObject data;
    private JSONArray subjects;

    public Snippet(){
        data = new JSONObject();
        subjects = new JSONArray();
        try {
            data.put("subjects", subjects);
            data.put("timestamp", 0);
            mySnippet.get("data");
        }
        catch (Exception u){
            try{
            mySnippet.put("data", data);}
            catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    public void addSubject(int position, Subject theSubject){

        HashMap<Integer, ArrayList<Integer>> myHM = new HashMap<>();
        myHM.put(0, theSubject.getC1S());
        myHM.put(1, theSubject.getC2S());
        myHM.put(2, theSubject.getC3S());
        myHM.put(3, theSubject.getC4S());
        myHM.put(4, theSubject.getC1W());
        myHM.put(5, theSubject.getC2W());
        myHM.put(6, theSubject.getC3W());
        myHM.put(7, theSubject.getC4W());



        JSONObject subject = new JSONObject();
        try {
            subject.put("name", theSubject.getName());
            subject.put("teacher", theSubject.getTeacher());
            subject.put("color", theSubject.getColor());
            subject.put("percentWrite", theSubject.getPercentWrite());
            subject.put("percentSpeak", theSubject.getPercentSpeak());

            for (int i = 0; i < 8; i++){
                JSONArray classGrades = new JSONArray();
                for (int x:myHM.get(i)) {
                    classGrades.put(x);

                }
                subject.put("c" + i, classGrades);
            }
            data = mySnippet.getJSONObject("data");
            subjects = data.getJSONArray("subjects");
            subjects.put(position, subject);
            data.put("subjects", subjects);
            data.put("timestamp", new Timestamp(System.currentTimeMillis()));
            mySnippet.put("data", data);
            Log.d("snippet", mySnippet.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void clearSnippet(){
        data = new JSONObject();
        subjects = new JSONArray();
        try {
            data.put("subjects", subjects);
            data.put("timestamp", 0);
            mySnippet.put("data", data);
            Log.d("Snippet", mySnippet.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}


/*
data:
    subjects: [
        0: {
            name:
            teacher:
            color:
            percentWrite:
            percentSpeak:
            c0: [0: ](S)
            c1: [0: ](S)
            c2: [0: ](S)
            c3: [0: ](S)
            c4: [0: ](W)
            c5: [0: ](W)
            c6: [0: ](W)
            c7: [0: ](W)
            }

    ]
 */
