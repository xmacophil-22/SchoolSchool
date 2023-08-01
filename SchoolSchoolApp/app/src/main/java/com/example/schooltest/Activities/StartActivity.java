package com.example.schooltest.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schooltest.Helpers.ActivityHandler;
import com.example.schooltest.Database.MyContractClass;
import com.example.schooltest.Database.MySQLiteHelper;
import com.example.schooltest.R;

public class StartActivity extends AppCompatActivity {

    private String id;
    private String key;
    private MySQLiteHelper db;

    ///////////////////////////////////////Starterklasse, die entscheidet, ob sich erst eingeloggt bzw. registriert werden muss, oder direkt die Anwendung geöffnet wird
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new MySQLiteHelper(this);
        id = db.getUserData(MyContractClass.UserdataTable.COL_SNIPPETID);
        key = db.getUserData(MyContractClass.UserdataTable.COL_KEY);
        ChangeActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //ChangeActivity();
    }

    ////////////////////////////////wechselt zu einer Aktivität je nachdem, ob schon Benutzerdaten in der Datenbank existieren oder nicht
    private void ChangeActivity(){

        if(db.getUserData(MyContractClass.UserdataTable.COL_USERNAME).equals("")){
                ActivityHandler.switchActivity(this, MainActivity.class, id, key);
            }
            else{

            ActivityHandler.switchActivity(this, Main.class, id, key);
            }
        }
    }
