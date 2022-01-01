package com.example.schooltest.Helpers;



import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class Login extends AppCompatActivity {
    ////////////////////////////ja, sind public, aber nur, weil die Klasse immer nur kurzzeitig benutzt wird und keine Sicherheitslücken entstehen
    public JSONObject loginJSON;

    public String password1;
    public String userName;
    public String password2;
    public String email;
    public String key;
    public String id;


    //////////////////////////////////////JSONObject welches der Request bei Logins oder Registrierungen mitgegeben wird
    public Login(){

        password1 = "";
        password2 = "";
        email = "";
        userName = "";
        key = "";
        id = "";


        loginJSON = new JSONObject();




    }
    ///////////////////////////////////////ändert das JSONObject
    public void refreshJSON(){
        try {
            if(!userName.equals("")){
                loginJSON.put("username", userName);

            }


            if (!email.equals("")){
                loginJSON.put("email", email);
            }
            if(!password1.equals("")){
                if(password2.equals("")){
                    loginJSON.put("password",  password1);
                }
                else {
                    loginJSON.put("password1", password1);
                    loginJSON.put("password2", password2);
                }
            }



        }
        catch (Exception e){
            e.printStackTrace();
        }

    }



}
