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
    Snippet snippet;
    PopUp popUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = "";
        key = "";
        snippet = new Snippet();

        requestQueue = Volley.newRequestQueue(this);



        password1ET = (EditText) findViewById(R.id.password1ET);
        password2ET = (EditText) findViewById(R.id.password2ET);
        userNameET = (EditText) findViewById(R.id.userNameET);
        emailET = (EditText) findViewById(R.id.emailET);
        login = new Login();
        popUp = null;



    }


    public void changeToLogin(View view){
        ActivityHandler.switchActivity(this,MainActivity2.class,id, key);
        finish();
    }


    public void submit(View view) {

        popUp = new PopUpError(this, "Die Post ist nicht angekomen!");


        ////////////////////////////////////////////////read all inputFields

        login.password1 = password1ET.getText().toString();
        login.password2 = password2ET.getText().toString();
        login.userName = userNameET.getText().toString();
        login.email = emailET.getText().toString();


        ///////////////////////////////////////////////////////////sendPostRequest to recieve key

        if(login.password1.equals(login.password2)){
            login.refreshJSON();
            MyRequestHandler.volleyPost(popUp,"https://schoolschooli.herokuapp.com/registration/", login.loginJSON, requestQueue, key,  new VolleyCallback() { ////neeeds change
                @Override
                public void onSuccess(JSONObject result) {
                    try {
                        key = result.getString("key");
                    } catch (Exception e) {
                        e.printStackTrace();
                        popUp.changeMessage("Ich wandle auf dem ewigen Weg des Key Suchenden :(");
                        popUp.show();
                    }
                    Snippet snippet = new Snippet();
                    MyRequestHandler.volleyPost( popUp,"https://schoolschooli.herokuapp.com/snippets/",snippet.mySnippet, requestQueue, key, new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            try {
                                id = result.getString("id");
                                ActivityHandler.switchActivity(MainActivity.this,HomeActivity.class, id, key);
                                finish();

                            } catch (Exception e) {
                                e.printStackTrace();
                                popUp.changeMessage("Ich wandle auf dem ewigen Weg des Id Suchenden :(");
                                popUp.show();
                            }
                        }
                    });

                }
            });


        }
        else{
            popUp.changeMessage("Bist du zu dumm zwei gleiche Passwörter einzugeben?");
            popUp.show();
        }

    }


}