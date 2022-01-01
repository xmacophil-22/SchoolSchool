package com.example.schooltest.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.schooltest.Helpers.ActivityHandler;
import com.example.schooltest.Database.MySQLiteHelper;
import com.example.schooltest.Helpers.Login;
import com.example.schooltest.Helpers.MyRequestHandler;
import com.example.schooltest.PopUps.PopUp;
import com.example.schooltest.PopUps.PopUpError;
import com.example.schooltest.R;
import com.example.schooltest.Helpers.Snippet;
import com.example.schooltest.Helpers.VolleyCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {

    private Login login;
    private EditText password1ET;
    private EditText userNameET;
    private MySQLiteHelper db;
    private RequestQueue requestQueue;
    private String id;
    private String key;
    private Snippet snippet;
    private PopUp popUp;

    ///////////////////////////////////////////////////////////////////////Aktivität zum einloggen, um key und id zu erhalten
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

    //////////////////////////////////////////////////////////////lädt Daten zum einloggen hoch und speichert key und id
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
                    ActivityHandler.switchActivity(MainActivity2.this, Main.class, id, key);

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

    ////////////////////////////////////////////////////wechselt zur Registrationsoberfläche
    public void switchToRegister(View view){
        ActivityHandler.switchActivity(this, MainActivity.class, id, key);
        finish();
    }
}
