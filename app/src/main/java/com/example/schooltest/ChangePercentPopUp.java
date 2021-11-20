package com.example.schooltest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class ChangePercentPopUp extends PopUp{

    EditText writeET, speakET;
    int write,speak;
    SubjectChangePopUp subjectChangePopUp;

    ChangePercentPopUp(Activity a, SubjectChangePopUp subjectChangePopUp){
        super(a);
        write = 0;
        speak = 0;
        this.subjectChangePopUp = subjectChangePopUp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.change_percent_pop_up);

        writeET = (EditText) findViewById(R.id.writeET);
        speakET = (EditText) findViewById(R.id.speakET);
        acceptBtn = (Button) findViewById(R.id.acceptBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        writeET.setText(String.valueOf(write));
        speakET.setText(String.valueOf(speak));

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak = Integer.parseInt(String.valueOf(speakET.getText()));
                write = Integer.parseInt(String.valueOf(writeET.getText()));
                subjectChangePopUp.setPercSpeak(speak);
                subjectChangePopUp.setPercWrite(write);
                //subjectChangePopUp.onStart();
                dismiss();
            }
        });


    }

    public void setWrite(int percent){
        write = percent;
    }
    public void setSpeak(int percent){
        speak = percent;
    }
}
