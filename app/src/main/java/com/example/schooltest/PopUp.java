package com.example.schooltest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public abstract class PopUp extends Dialog {
    protected Button acceptBtn;
    protected TextView messageTV;
    protected String message;
    protected Activity activity;

    public PopUp(Activity a){
       super(a);
       activity = a;
    }

    public void changeMessage(String m){
        message = m;
    }
}
