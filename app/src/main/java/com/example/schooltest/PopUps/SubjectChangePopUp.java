package com.example.schooltest.PopUps;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.schooltest.R;
import com.example.schooltest.Helpers.Subject;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SubjectChangePopUp extends SubjectBigPopUp {
    private Button okWBtn, okSBtn, deleteSBtn, deleteWBtn, percentBtn;
    private ImageButton colorPickerBtn;
    private EditText newGradeWET, newGradeSET;
    private ChangePercentPopUp changePercentPopUp;
    private Context context;

    ///////////////////////////////////////////////////Fenster zum Verändern von Fächerdaten
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

        colorPickerBtn = (ImageButton) findViewById(R.id.colorPickerBtn);
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

        /////////////////////////////////wenn Prozentknopf gedrückt wird, öffnet das Fenster zum verändern der Verteilung zwischen mündlichen und schriftlichen Noten
        percentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePercentPopUp.setWrite(percWrite);
                changePercentPopUp.setSpeak(percSpeak);
                changePercentPopUp.show();
            }
        });

        //////////////////////////////wenn Farbknopf gedrückt wird, öffnet das Fenster zum verändern der Farbe eines Fachs
        colorPickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });

        ////////////////////////////////beim Auswählen einer Note in der mündlichen Notenliste, wird diese in das untere Textfeld übernommen
        bSpeakSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("juhu", String.valueOf(i));
                newGradeSET.setText(String.valueOf(getGradesS(i)));
            }
        });

        //////////////////////////////beim Auswählen einer Note in der schriflichen Notenliste, wird diese in das untere Textfeld übernommen
        bWriteSV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("juhu", String.valueOf(i));
                newGradeWET.setText(String.valueOf(getGradesW(i)));
            }
        });

        ///////////////////////////////wenn der Knopf mit dem X unter der schriftlichen Notenliste gedrückt wird, wird ein Element mit dem im Textfeld stehenden
        ///////////////////////Wert aus der schriftlichen Notenliste enfernt
        deleteWBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeGradeW(Integer.parseInt((String.valueOf(newGradeWET.getText()))));
            }
        });

        ///////////////////////////////wenn der Knopf mit dem X unter der mündlichen Notenliste gedrückt wird, wird ein Element mit dem im Textfeld stehenden
        ///////////////////////Wert aus der mündlichen Notenliste enfernt
        deleteSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeGradeS(Integer.parseInt(String.valueOf(newGradeSET.getText())));
            }
        });

        ///////////////////////////////wenn der Knopf mit dem Haken unter der mündlichen Notenliste gedrückt wird, wird die im Textfeld stehende Note hinzugefügt
        okSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGradeS();
            }
        });

        ///////////////////////////////wenn der Knopf mit dem Haken unter der schriflichen Notenliste gedrückt wird, wird die im Textfeld stehende Note hinzugefügt
        okWBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGradeW();
            }
        });


        ////////////////////////////////////////////////////////wenn neben das PopUp geklickt wird schließt es, speichert die Änderung der Noten
        ////////////////////////////////////////////////////und updated den gesamt Notenschnitt
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

    ////////////////////////////////öffnet den ColorPicker
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