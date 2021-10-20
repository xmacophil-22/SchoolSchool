package com.example.schooltest;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Snippet {
    JSONObject mySnippet;
    JSONObject data;

    public Snippet(){
        mySnippet = new JSONObject();
        data = new JSONObject();

        try {
            data.put("Notenuebersicht", "lol");
            mySnippet.put("data", data);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
