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
import com.example.schooltest.Database.MyContractClass;
import com.example.schooltest.Database.MySQLiteHelper;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    Login login;
    EditText password1ET;
    EditText password2ET;
    EditText userNameET;
    MySQLiteHelper db;

    RequestQueue requestQueue;
    String key;
    String id;
    Snippet snippet;
    PopUp popUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);
        id = "";
        key = "";
        db = new MySQLiteHelper(this);
        Log.d("myIDIs", db.getUserData(MyContractClass.UserdataTable.COL_KEY));

        snippet = new Snippet();
        //db.add_Snippet(Snippet.mySnippet);

        requestQueue = Volley.newRequestQueue(this);

        password1ET = (EditText) findViewById(R.id.password1ET);
        password2ET = (EditText) findViewById(R.id.password2ET);
        userNameET = (EditText) findViewById(R.id.userNameET);
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


        ///////////////////////////////////////////////////////////sendPostRequest to receive key

        if(login.password1.equals(login.password2)){
            login.refreshJSON();
            MyRequestHandler.volleyRequest(1, popUp,"https://schoolschooli.herokuapp.com/registration/", login.loginJSON, requestQueue, key,  new VolleyCallback() { ////neeeds change
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
                    MyRequestHandler.volleyRequest(1, popUp,"https://schoolschooli.herokuapp.com/snippets/",snippet.mySnippet, requestQueue, key, new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            try {
                                id = result.getString("id");
                                //Registration at the Server Successful. Now add the username and password to the local Database
                                db.add_User(login.userName, key, id);
                                db.add_Snippet(Snippet.mySnippet);
                                ActivityHandler.switchActivity(MainActivity.this,Main.class, id, key);


                            } catch (Exception e) {
                                e.printStackTrace();
                                popUp.changeMessage("Ich wandle auf dem ewigen Weg des Id Suchenden :(");
                                popUp.show();
                            }
                        }

                        @Override
                        public void onDefeat() {

                        }
                    });
                }

                @Override
                public void onDefeat() {

                }
            });


        }
        else{
            popUp.changeMessage("Bist du zu dumm zwei gleiche Passwörter einzugeben?");
            popUp.show();
        }

    }


}