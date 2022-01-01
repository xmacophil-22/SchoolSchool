package com.example.schooltest.PopUps;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.schooltest.PopUps.PopUp;
import com.example.schooltest.R;

public class PopUpError extends PopUp {

    ///////////////////Errorfenster mit Nachricht
    public PopUpError(Activity a, String message){
        super(a);
        this.message = message;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_error);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        messageTV = (TextView) findViewById(R.id.messageTV);
        messageTV.setText(message);
        acceptBtn = (Button) findViewById(R.id.okBtn);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
}
