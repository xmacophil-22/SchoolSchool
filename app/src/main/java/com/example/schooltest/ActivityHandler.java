package com.example.schooltest;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;

public class ActivityHandler {

    public static void switchActivity(Context context, Class myClass, String id){
        Intent switchActivityIntent = new Intent(context,myClass);
        switchActivityIntent.putExtra("id", id);
        context.startActivity(switchActivityIntent);

    }
}
