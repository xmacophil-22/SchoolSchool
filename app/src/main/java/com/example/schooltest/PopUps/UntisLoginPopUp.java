package com.example.schooltest.PopUps;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.schooltest.Helpers.Login;
import com.example.schooltest.Helpers.MyRequestHandler;
import com.example.schooltest.R;
import com.example.schooltest.Helpers.VolleyCallback;

import org.json.JSONObject;

public class UntisLoginPopUp extends PopUp {

    EditText passwordET, usernameET;
    Login untisLoginData;
    PopUp errorPopup;
    RequestQueue requestQueue;
    String key;

    /////////////////////////////////Fenster zum einloggen in WebUntis
    public UntisLoginPopUp(Activity a, RequestQueue requestQueue, String key) {
        super(a);
        message = "Du hast dich noch nicht in moodle angemeldet. \n Ohne die Anmeldung kannst du diese Funktion leider nicht nutzen.";
        untisLoginData = new Login();
        errorPopup = new PopUpError(a, "Da ist wohl etwas schief gelaufen");
        this.requestQueue = requestQueue;
        this.key = key;
        setCanceledOnTouchOutside(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.untis_login_pop_up);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        passwordET = (EditText) findViewById(R.id.passwordET);
        messageTV = (TextView) findViewById(R.id.uMessageTV);
        usernameET = (EditText) findViewById(R.id.userNameET);
        acceptBtn = (Button) findViewById(R.id.loginBtn);

        messageTV.setText(message);

        //////////////////////////////////////////////wenn der okay Knopf gedrückt wird, werden die Daten an den Server gesendet, bei erfolg schließt sich das Fenster
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                untisLoginData.password1 = String.valueOf(passwordET.getText());
                untisLoginData.userName = String.valueOf(usernameET.getText());
                untisLoginData.refreshJSON();
                Log.d("LoginData", untisLoginData.password1);


                MyRequestHandler.volleyRequest(1, errorPopup, "https://schoolschooli.herokuapp.com/webuntis/login/", untisLoginData.loginJSON, requestQueue, key, new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        dismiss();
                    }

                    @Override
                    public void onDefeat() {

                    }
                });
            }
        });
    }
}


