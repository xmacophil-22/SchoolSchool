package com.example.schooltest;

import android.app.Activity;
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

public class SubjectChangePopUp extends SubjectBigPopUp{
    private Button okWBtn, okSBtn, deleteSBtn, deleteWBtn;
    private EditText newGradeWET, newGradeSET;

    public SubjectChangePopUp(Activity a){
        super(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_change_pop_up);

        nameTV = (TextView) findViewById(R.id.bNameTV);
        teacherTV = (TextView) findViewById(R.id.bTeacherTV);
        bSpeakSV = (ListView) findViewById(R.id.bSpeakLV);
        bWriteSV = (ListView) findViewById(R.id.bWriteLV);
        cardView = (CardView) findViewById(R.id.bCardView);
        dropDownS = (Spinner) findViewById(R.id.dropDownS);
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

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Log.d("SubjectChangePopUp", "dismiss triggered");
                adapter.equalizeArrays(myCurrent, c1S, c1W, c2S, c2W, c3S, c3W ,c4S,c4W);
                Subject.recountOverAll(adapter.getMySubjects());
            }
        });
    }

    public int getGradesS(int position){

        return sHM.get(dropDownS.getSelectedItemPosition()).get(position);
        /*switch (dropDownS.getSelectedItemPosition()) {
            case 1:
                return c2S.get(position);

            case 2:
                return c3S.get(position);

            case 3:
                return c4S.get(position);

            default:
                return c1S.get(position);
        }*/
    }

    public int getGradesW(int position){

        return wHM.get(dropDownS.getSelectedItemPosition()).get(position);
        /*switch (dropDownS.getSelectedItemPosition()) {
            case 1:
                return c2W.get(position);

            case 2:
                return c3W.get(position);

            case 3:
                return c4W.get(position);

            default:
                return c1W.get(position);
        }*/
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
        /*switch (dropDownS.getSelectedItemPosition()) {
            case 0:
                for (int i:c1S) {
                    if(i == grade){
                        c1S.remove(x);
                        break;
                    }
                    x++;
                }
                break;
            case 1:
                for (int i:c2S) {
                    if(i == grade){
                        c2S.remove(x);
                        break;
                    }
                    x++;
                }
                break;
            case 2:
                for (int i:c3S) {
                    if(i == grade){
                        c3S.remove(x);
                        break;
                    }
                    x++;
                }
                break;
            case 3:
                for (int i:c4S) {
                    if(i == grade){
                        c4S.remove(x);
                        break;
                    }
                    x++;
                }
                break;
        }*/

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
        /*switch (dropDownS.getSelectedItemPosition()) {
            case 0:
                for (int i:c1W) {
                    if(i == grade){
                        c1W.remove(x);
                        break;
                    }
                    x++;
                }
                break;
            case 1:
                for (int i:c2W) {
                    if(i == grade){
                        c2W.remove(x);
                        break;
                    }
                    x++;
                }
                break;
            case 2:
                for (int i:c3W) {
                    if(i == grade){
                        c3W.remove(x);
                        break;
                    }
                    x++;
                }
                break;
            case 3:
                for (int i:c4W) {
                    if(i == grade){
                        c4W.remove(x);
                        break;
                    }
                    x++;
                }
                break;
        }*/

        myAdapter.notifyDataSetChanged();
    }


    public void addGradeW(){
        int grade = Integer.parseInt(String.valueOf(newGradeWET.getText()));
        ArrayAdapter<Integer> myAdapter = (ArrayAdapter<Integer>) bWriteSV.getAdapter();
        wHM.get(dropDownS.getSelectedItemPosition()).add(grade);
        /*switch (dropDownS.getSelectedItemPosition()) {
            case 0:
                c1W.add(grade);
                break;
            case 1:
                c2W.add(grade);
                break;
            case 2:
                c3W.add(grade);
                break;
            case 3:
                c4W.add(grade);
                break;
        }*/

        myAdapter.notifyDataSetChanged();
    }

    public void addGradeS(){
        int grade = Integer.parseInt(newGradeSET.getText().toString());
        ArrayAdapter<Integer> myAdapter = (ArrayAdapter<Integer>) bSpeakSV.getAdapter();
        sHM.get(dropDownS.getSelectedItemPosition()).add(grade);
        /*switch (dropDownS.getSelectedItemPosition()) {
            case 0:
                c1S.add(grade);
                break;
            case 1:
                c2S.add(grade);
                break;
            case 2:
                c3S.add(grade);
                break;
            case 3:
                c4S.add(grade);
                break;
        }*/

        myAdapter.notifyDataSetChanged();
    }


}