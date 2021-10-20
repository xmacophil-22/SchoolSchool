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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyRequestHandler extends AppCompatActivity {





    public static void volleyPost(PopUp myPopUp, String postUrl, JSONObject postData, RequestQueue requestQueue, String key, final VolleyCallback callback) {
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                myPopUp.show();
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


        requestQueue.add(jsonObjectRequest);


        }


    }
