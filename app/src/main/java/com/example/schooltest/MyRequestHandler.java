package com.example.schooltest;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyRequestHandler extends AppCompatActivity {





    public static void volleyPost(String postUrl, JSONObject postData, RequestQueue requestQueue, final VolleyCallback callback){



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);

            }
        }, new Response.ErrorListener(){
            @Override
            public  void onErrorResponse(VolleyError error){
                error.printStackTrace();

            }
        });


        requestQueue.add(jsonObjectRequest);


    }




}
