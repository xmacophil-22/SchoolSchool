package com.example.schooltest.Helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schooltest.PopUps.SubjectBigPopUp;
import com.example.schooltest.PopUps.SubjectChangePopUp;
import com.example.schooltest.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Subject> mySubjects;
    private LayoutInflater mInflater;
    private SubjectBigPopUp mySubjectPopUp;
    private SubjectChangePopUp mySubjectChangePopUp;
    private Snippet snippet;

    ////////////////////////////////////Adapter für die gitterförmige Anordnung der unterschiedlichen Fächer in der GradesActivity
    public RecyclerViewAdapter(Activity activity, Context context, ArrayList<Subject> mySubjects) {
        this.mInflater = LayoutInflater.from(context);
        this.mySubjects = mySubjects;
        mySubjectPopUp = new SubjectBigPopUp(activity);
        mySubjectChangePopUp = new SubjectChangePopUp(activity, context);
        mySubjectPopUp.setAdapter(this);
        mySubjectChangePopUp.setAdapter(this);
        snippet = new Snippet();
    }

    ///////////////////////////////////erweitert das Gitter (Tabelle) um eine weitere Zelle, auf Basis des grid_fill_in.xml Layouts, wenn nötig, zum Beispiel beim
    ///////////////////////Runterscrollen oder beim Hinzufügen eines neuen Fachs
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_fill_in, parent, false);

        return new ViewHolder(view);
    }

    ///////////////////////////////setzt die Daten einer einzelnen Zelle
    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Subject mySubject = mySubjects.get(position);
        int color = mySubject.getColor();
        holder.cardView.setVisibility(mySubject.getIsVisible());
        holder.teacherTV.setText(mySubject.getTeacher());
        holder.nameTV.setText(mySubject.getName());
        holder.averageC1TV.setText(String.valueOf(mySubject.getAverageC1()));
        holder.averageC2TV.setText(String.valueOf(mySubject.getAverageC2()));
        holder.averageC3TV.setText(String.valueOf(mySubject.getAverageC3()));
        holder.averageC4TV.setText(String.valueOf(mySubject.getAverageC4()));
        holder.averageTotalTV.setText(String.valueOf(mySubject.getAverageTotal()));
        holder.cardView.setCardBackgroundColor(color);
    }

    ////////////////////////////gibt die Anzahl an Fächern im Grid zurück
    @Override
    public int getItemCount() {
        return mySubjects.size();
    }


    //////////////////////////Schaltet die Funktionalität von Zellen frei, wenn sie auf dem Bildschirm zu sehen sind
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView teacherTV, averageC1TV, averageC2TV, averageC3TV,averageC4TV, averageTotalTV, nameTV;

        ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.subjectCV);
            teacherTV = (TextView) view.findViewById(R.id.teacherTV);
            nameTV = (TextView) view.findViewById(R.id.nameTV);
            averageC1TV = (TextView) view.findViewById(R.id.averageC1TV);
            averageC2TV = (TextView) view.findViewById(R.id.averageC2TV);
            averageC3TV = (TextView) view.findViewById(R.id.averageC3TV);
            averageC4TV = (TextView) view.findViewById(R.id.averageC4TV);
            averageTotalTV = (TextView) view.findViewById(R.id.averageTotalTV);

            ////////////////////////////////////////////////////////////bei einem normalen Klick auf eine Zelle öffnet sich das PopUp mit detaillierteren Infos
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setViewData(mySubjectPopUp);
                }
            });

            /////////////////////////////////////////////////////////////bei einem langen Klick öffnet sich der Bearbeitungsmodus des Fachs
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    setViewData(mySubjectChangePopUp);
                    return true;
                }
            });
        }

        @Override
        public void onClick(View view) {
            //do nothing
        }

        //////////////////////////////////////////////////setzt die Daten im Detailles- oder Bearbeitungspopup eines Fachs
        public void setViewData(SubjectBigPopUp mySubjectPopUp){
            int i = getAdapterPosition();
            if(i != 0 && i != 1) {
                Subject subject = getItem(i);
                mySubjectPopUp.setMyCurrent(i);
                mySubjectPopUp.setSubjectName(subject.getName());
                mySubjectPopUp.setTeacher(subject.getTeacher());
                mySubjectPopUp.setColor(subject.getColor());
                mySubjectPopUp.setC1S(subject.getC1S());
                mySubjectPopUp.setC1W(subject.getC1W());
                mySubjectPopUp.setC2S(subject.getC2S());
                mySubjectPopUp.setC2W(subject.getC2W());
                mySubjectPopUp.setC3S(subject.getC3S());
                mySubjectPopUp.setC3W(subject.getC3W());
                mySubjectPopUp.setC4S(subject.getC4S());
                mySubjectPopUp.setC4W(subject.getC4W());
                mySubjectPopUp.setPercWrite(subject.getPercentWrite());
                mySubjectPopUp.setPercSpeak(subject.getPercentSpeak());
                mySubjectPopUp.show();
            }
        }
    }

    //////////////////////////////////gibt das Fach an mitgegebener Stelle zurück
    Subject getItem(int id) {
        return mySubjects.get(id);
    }

    //////////////////////////////////////ändert die Daten des alten Fachs zu denen des neuen Fachs
    public void equalizeAll(int i, ArrayList<Integer> c1s, ArrayList<Integer> c1w, ArrayList<Integer> c2s,
                               ArrayList<Integer> c2w, ArrayList<Integer> c3s, ArrayList<Integer> c3w,
                               ArrayList<Integer> c4s, ArrayList<Integer> c4w, int col, int percWrite, int percSpeak){

        Subject subject = getItem(i);
        subject.setC1S(c1s);
        subject.setC1W(c1w);
        subject.setC2S(c2s);
        subject.setC2W(c2w);
        subject.setC3S(c3s);
        subject.setC3W(c3w);
        subject.setC4S(c4s);
        subject.setC4W(c4w);
        subject.setPercentWrite(percWrite);
        subject.setPercentSpeak(percSpeak);
        subject.updateAverages();
        subject.setColor(col);
        notifyItemChanged(i);
    }

    ////////////////////////////////ändert die Daten des Fachs im Snippet, um sie später zu speichern
    public void equalizeSnippet(int position){
        Log.d("addSubject", position+ ", " + mySubjects.get(position).getName());
        snippet.addSubject(position - 2, mySubjects.get(position));

    }

    /////////////////////////////gibt die Liste an Fächern zurück, die in dem Grid aufgelistet werden
    public ArrayList<Subject> getMySubjects() {
        return mySubjects;
    }
}