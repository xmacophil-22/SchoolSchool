package com.example.schooltest;

import android.content.Context;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;

public class ActivityHandler {

    public static void switchActivity(Context context, Class myClass, String id, String key){
        Intent switchActivityIntent = new Intent(context,myClass);
        Bundle extras = new Bundle();
        extras.putString("id", id);
        extras.putString("key", key);
        switchActivityIntent.putExtras(extras);
        context.startActivity(switchActivityIntent);

    }

}
