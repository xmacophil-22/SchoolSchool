package com.example.schooltest;


import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    Login login;
    EditText password1ET;
    EditText password2ET;
    EditText emailET;
    EditText userNameET;

    RequestQueue requestQueue;
    String key;
    String id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = "";

        requestQueue = Volley.newRequestQueue(this);



        password1ET = (EditText) findViewById(R.id.password1ET);
        password2ET = (EditText) findViewById(R.id.password2ET);
        userNameET = (EditText) findViewById(R.id.userNameET);
        emailET = (EditText) findViewById(R.id.emailET);
        login = new Login();


    }


    public void changeToLogin(View view){
        ActivityHandler.switchActivity(this,MainActivity2.class,id);
        finish();
    }


    public void submit(View view) {

        ////////////////////////////////////////////////read all inputFields

        login.password1 = password1ET.getText().toString();
        login.password2 = password2ET.getText().toString();
        login.userName = userNameET.getText().toString();
        login.email = emailET.getText().toString();

        ///////////////////////////////////////////put in JSON
        login.refreshJSON();


        ///////////////////////////////////////////////////////////sendPostRequest to recieve key

        if(login.password1.equals(login.password2)){
            MyRequestHandler.volleyPost("https://schoolschooli.herokuapp.com/rest-auth/registration/", login.loginJSON, requestQueue,  new VolleyCallback() { ////neeeds change
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        key = result.getString("key");
                        Log.d("result", key);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Snippet snippet = new Snippet();
                    MyRequestHandler.volleyPost("https://schoolschooli.herokuapp.com/snippets/",snippet.mySnippet, requestQueue, new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            try {
                                id = result.getString("id");
                                Log.d("myId", id);
                                ActivityHandler.switchActivity(MainActivity.this,HomeActivity.class, id);
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            });


        }

    }


}