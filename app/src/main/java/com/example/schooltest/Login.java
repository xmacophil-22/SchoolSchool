package com.example.schooltest;



import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class Login extends AppCompatActivity {

    JSONObject loginJSON;
    //MyRequestHandler myRequestHandler;

    String password1;
    String userName;
    String password2;
    String email;



    public Login(){

        password1 = "";
        password2 = "";
        email = "";
        userName = "";

        loginJSON = new JSONObject();




    }

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



            Log.d("myJSON", loginJSON.toString());


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }



}
