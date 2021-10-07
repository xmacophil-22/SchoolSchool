package com.example.schooltest;

import android.os.Bundle;
import android.text.NoCopySpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {

    Login login;
    EditText password1ET;
    EditText userNameET;

    RequestQueue requestQueue;
    String id;
    String key;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        login = new Login();

        requestQueue = Volley.newRequestQueue(this);

        id = "";
        key = "";
        password1ET = (EditText) findViewById(R.id.password1ET);
        userNameET = (EditText) findViewById(R.id.userNameET);


    }

    public void submit(View view){
        login.password1 = password1ET.getText().toString();
        login.userName = userNameET.getText().toString();
        login.refreshJSON();


        MyRequestHandler.volleyPost("https://schoolschooli.herokuapp.com/rest-auth/login/", login.loginJSON, requestQueue, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    key = result.getString("key");
                    Log.d("mykey", key);

                    MyRequestHandler.volleyPost("https://schoolschooli.herokuapp.com/snippets/?key=" + key, null, requestQueue, new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            try {
                                id = result.getString("id");
                                Log.d("myId", id);
                                ActivityHandler.switchActivity(MainActivity2.this,HomeActivity.class, id);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void switchToRegister(View view){
        ActivityHandler.switchActivity(this,MainActivity.class, id);
        finish();
    }
}
