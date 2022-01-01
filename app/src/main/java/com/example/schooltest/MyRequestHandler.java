package com.example.schooltest;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schooltest.Database.MyContractClass;
import com.example.schooltest.Database.MySQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyRequestHandler extends AppCompatActivity {

    public static void saveSnippet(Activity a){
        PopUpError popUpError = new PopUpError(a, "mist");
        MySQLiteHelper db = new MySQLiteHelper(a);
        RequestQueue requestQueue = Volley.newRequestQueue(a);
        Log.d("Snippet before posting", Snippet.mySnippet.toString());
        MyRequestHandler.volleyRequest(2, popUpError, "https://schoolschooli.herokuapp.com/snippets/" + db.getUserData(MyContractClass.UserdataTable.COL_SNIPPETID) + "/", Snippet.mySnippet, requestQueue, db.getUserData(MyContractClass.UserdataTable.COL_KEY), new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {

            }

            @Override
            public void onDefeat() {

            }
        });
    }


    public static void volleyRequest(int method,PopUp myPopUp, String postUrl, JSONObject postData, RequestQueue requestQueue, String key, final VolleyCallback callback) {
        String headerKey;
        String headerValue;

        if(!key.equals("")){
            headerKey = "Authorization";
            headerValue = "Token " + key;
        }
        else {
            headerKey = "Content-Type";
            headerValue = "application/json";
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(myPopUp!= null){myPopUp.show();}
                else {
                    callback.onDefeat();
                }
                error.printStackTrace();
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();


                headers.put(headerKey, headerValue);
                return headers;
            }

        };

        Log.d("Request", method + " " + postUrl + " " + headerKey + ":" + headerValue);
        if(postData != null) {
            Log.d("Request", postData.toString());
        }
        requestQueue.add(jsonObjectRequest);


        }

        public static ArrayList<Subject> getSubjects(JSONObject result){
        ArrayList<Subject> subjects = new ArrayList<>();
            ArrayList<Integer> c2W = new ArrayList<>();
            ArrayList<Integer> c3W = new ArrayList<>();
            ArrayList<Integer> c4W = new ArrayList<>();
            ArrayList<Integer> c1S = new ArrayList<>();
            ArrayList<Integer> c2S = new ArrayList<>();
            ArrayList<Integer> c1W = new ArrayList<>();
            ArrayList<Integer> c3S = new ArrayList<>();
            ArrayList<Integer> c4S = new ArrayList<>();
            HashMap<Integer, ArrayList<Integer>> myHM = new HashMap<>();
            myHM.put(0, c1S);
            myHM.put(1, c2S);
            myHM.put(2, c3S);
            myHM.put(3, c4S);
            myHM.put(4, c1W);
            myHM.put(5, c2W);
            myHM.put(6, c3W);
            myHM.put(7, c4W);
            for (int x = 0; x > myHM.size(); x++){
                myHM.get(x).add(15);
            }
        try {
            JSONObject data = result.getJSONObject("data");
            JSONArray array = data.getJSONArray("subjects");
            Log.d("result", array.toString());
            for(int i = 0; i < array.length(); i++){
                JSONObject subject = array.getJSONObject(i);

                String name = subject.getString("name");
                int color = subject.getInt("color");
                String teacher = subject.getString("teacher");
                int percentWrite = subject.getInt("percentWrite");
                int percentSpeak = subject.getInt("percentSpeak");

                for(int z = 0; z < myHM.size(); z++){
                    JSONArray theArray = subject.getJSONArray("c" + z);
                    ArrayList<Integer> aarray = myHM.get(z);
                    aarray.clear();
                    for(int u = 0; u < theArray.length(); u++){
                        aarray.add(theArray.getInt(u));
                    }
                }
                subjects.add(new Subject(name, color, teacher, percentWrite, percentSpeak, c1W,c2W,c3W,c4W,c1S,c2S,c3S,c4S, View.VISIBLE));
                Log.d("request", subjects.toString());
            }
            return subjects;
        }
        catch (Exception e){
            e.printStackTrace();
            return subjects;
        }
        }
    }
