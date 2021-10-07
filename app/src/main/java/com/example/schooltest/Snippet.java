package com.example.schooltest;

import org.json.JSONException;
import org.json.JSONObject;

public class Snippet {
    JSONObject mySnippet;

    public Snippet(){
        mySnippet = new JSONObject();

        try {
            mySnippet.put("Notenübersicht", "eine Notenübersicht");




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
