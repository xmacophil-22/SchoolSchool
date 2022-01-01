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
import com.example.schooltest.Database.MySQLiteHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {

    Login login;
    EditText password1ET;
    EditText userNameET;
    MySQLiteHelper db;
    RequestQueue requestQueue;
    String id;
    String key;
    Snippet snippet;

    PopUp popUp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new MySQLiteHelper(this);
        login = new Login();
        snippet = new Snippet();

        requestQueue = Volley.newRequestQueue(this);

        id = "";
        key = "";
        password1ET = (EditText) findViewById(R.id.password1ET);
        userNameET = (EditText) findViewById(R.id.userNameET);
        popUp = new PopUpError(this, "Passwort oder Benutzername ist Falsch!");

    }

    public void submit(View view){
        login.password1 = password1ET.getText().toString();
        login.userName = userNameET.getText().toString();
        login.refreshJSON();

        MyRequestHandler.volleyRequest(1, popUp,"https://schoolschooli.herokuapp.com/login/", login.loginJSON, requestQueue, key, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    key = result.getString("key");
                    id = result.getString("SnippetID");
                    Log.d("Login", result.toString());
                    db.add_User(login.userName, key, id);
                    MyRequestHandler.volleyRequest(0, popUp, "https://schoolschooli.herokuapp.com/snippets/" + id, null, requestQueue, key, new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            db.add_Snippet(result);
                        }

                        @Override
                        public void onDefeat() {

                        }
                    });
                    ActivityHandler.switchActivity(MainActivity2.this,Main.class, id, key);

                } catch (JSONException e) {
                    popUp.changeMessage("Ich wandle auf dem ewigen Weg des Suchenden :(");
                    popUp.show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onDefeat() {

            }
        });
    }

    public void switchToRegister(View view){
        ActivityHandler.switchActivity(this,MainActivity.class, id, key);
        finish();
    }
}
