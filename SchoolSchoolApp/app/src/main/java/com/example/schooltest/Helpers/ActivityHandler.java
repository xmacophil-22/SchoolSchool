package com.example.schooltest.Helpers;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;

public class ActivityHandler {


    ////////////////////////////////////////////ja, ist static, aber sehr praktisch (7 mal benutzt), wechselt zwischen Aktivitäten
    public static void switchActivity(Context context, Class myClass, String id, String key){
        Intent switchActivityIntent = new Intent(context,myClass);
        Bundle extras = new Bundle();
        extras.putString("id", id);
        extras.putString("key", key);
        switchActivityIntent.putExtras(extras);
        context.startActivity(switchActivityIntent);

    }

}
