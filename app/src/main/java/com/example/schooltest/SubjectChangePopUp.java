package com.example.schooltest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SubjectChangePopUp extends SubjectBigPopUp{
    private Button okWBtn, okSBtn, deleteSBtn, deleteWBtn, colorPickerBtn, percentBtn;
    private EditText newGradeWET, newGradeSET;
    private ChangePercentPopUp changePercentPopUp;
    Context context;


    public SubjectChangePopUp(Activity a, Context c){
        super(a);
        context = c;
        percWrite = 0;
        percSpeak = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_change_pop_up);

        changePercentPopUp = new ChangePercentPopUp(activity, this);

        colorPickerBtn = (Button) findViewById(R.id.colorPickerBtn);
        nameTV = (TextView) findViewById(R.id.bNameTV);
        teacherTV = (TextView) findViewById(R.id.bTeacherTV);
        bSpeakSV = (ListView) findViewById(R.id.bSpeakLV);
        bWriteSV = (ListView) findViewById(R.id.bWriteLV);
        cardView = (CardView) findViewById(R.id.bCardView);
        dropDownS = (Spinner) findViewById(R.id.dropDownS);
        percentBtn = (Button) findViewById(R.id.percentBtn);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, courses);
        dropDownS.setAdapter(adapter);

        deleteSBtn = (Button) findViewById(R.id.deleteSBtn);
        deleteWBtn = (Button) findViewById(R.id.deleteWBtn);
        okSBtn = (Button) findViewById(R.id.okSBtn);
        okWBtn = (Button) findViewById(R.id.okWBtn);
        newGradeSET = (EditText) findViewById(R.id.newGradeSET);
        newGradeWET = (EditText) findViewById(R.id.newGradeWET);
        sHM.put(0, c1S);
        sHM.put(1, c2S);
        sHM.put(2, c3S);
        sHM.put(3, c4S);
        wHM.put(0, c1W);
        wHM.put(1, c2W);
        wHM.put(2, c3W);
        wHM.put(3, c4W);

    }

    @Override
    protected void onStart() {
        super.onStart();

        percentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePercentPopUp.setWrite(percWrite);
                changePercentPopUp.setSpeak(percSpeak);
                changePercentPopUp.show();
            }
        });

        colorPickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });


        bSpeakSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("juhu", String.valueOf(i));
                newGradeSET.setText(String.valueOf(getGradesS(i)));
            }
        });

        bWriteSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("juhu", String.valueOf(i));
                newGradeWET.setText(String.valueOf(getGradesW(i)));
            }
        });

        deleteWBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeGradeW(Integer.parseInt((String.valueOf(newGradeWET.getText()))));
            }
        });

        deleteSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeGradeS(Integer.parseInt(String.valueOf(newGradeSET.getText())));
            }
        });


        okSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGradeS();
            }
        });

        okWBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGradeW();
            }
        });


        ////////////////////////////////////////////////////////wenn neben das PopUp geklickt wird schließt es, speichert die Änderung der Noten und updated den gesamt Schnitt
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.d("SubjectChangePopUp", "dismiss triggered");
                adapter.equalizeAll(myCurrent, c1S, c1W, c2S, c2W, c3S, c3W ,c4S,c4W, color, percWrite, percSpeak);
                Subject.recountOverAll(adapter.getMySubjects());
                adapter.equalizeSnippet(myCurrent);
            }
        });
    }

    public int getGradesS(int position){

        return sHM.get(dropDownS.getSelectedItemPosition()).get(position);
    }

    public int getGradesW(int position){

        return wHM.get(dropDownS.getSelectedItemPosition()).get(position);
    }

    public void removeGradeS(int grade){
        ArrayAdapter<Integer> myAdapter = (ArrayAdapter<Integer>) bSpeakSV.getAdapter();
        ArrayList<Integer> mArray = sHM.get(dropDownS.getSelectedItemPosition());
        int x = 0;
        for (int i:mArray) {
            if(i == grade){
                mArray.remove(x);
                break;
            }
            x++;
        }

        myAdapter.notifyDataSetChanged();
    }



    public void removeGradeW(int grade){
        ArrayAdapter<Integer> myAdapter = (ArrayAdapter<Integer>) bWriteSV.getAdapter();
        int x = 0;
        ArrayList<Integer> mArray = wHM.get(dropDownS.getSelectedItemPosition());
        for (int i:mArray) {
            if(i == grade){
                mArray.remove(x);
                break;
            }
            x++;
        }

        myAdapter.notifyDataSetChanged();
    }


    public void addGradeW(){
        int grade = Integer.parseInt(String.valueOf(newGradeWET.getText()));
        ArrayAdapter<Integer> myAdapter = (ArrayAdapter<Integer>) bWriteSV.getAdapter();
        wHM.get(dropDownS.getSelectedItemPosition()).add(grade);

        myAdapter.notifyDataSetChanged();
    }

    public void addGradeS(){
        int grade = Integer.parseInt(newGradeSET.getText().toString());
        ArrayAdapter<Integer> myAdapter = (ArrayAdapter<Integer>) bSpeakSV.getAdapter();
        sHM.get(dropDownS.getSelectedItemPosition()).add(grade);

        myAdapter.notifyDataSetChanged();
    }

    public void openColorPicker(){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(context, color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color2) {
                color = color2;
                cardView.setCardBackgroundColor(color);
            }
        });

        colorPicker.show();
    }

}