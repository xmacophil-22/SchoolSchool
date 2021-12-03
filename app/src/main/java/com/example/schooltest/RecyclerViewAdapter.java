package com.example.schooltest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Subject> mySubjects;
    private LayoutInflater mInflater;
    private SubjectBigPopUp mySubjectPopUp;
    private SubjectChangePopUp mySubjectChangePopUp;
    private Snippet snippet;

    public RecyclerViewAdapter(Activity activity, Context context, ArrayList<Subject> mySubjects) {
        this.mInflater = LayoutInflater.from(context);
        this.mySubjects = mySubjects;
        mySubjectPopUp = new SubjectBigPopUp(activity);
        mySubjectChangePopUp = new SubjectChangePopUp(activity, context);
        mySubjectPopUp.setAdapter(this);
        mySubjectChangePopUp.setAdapter(this);
        snippet = new Snippet();
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_fill_in, parent, false);

        return new ViewHolder(view);
    }

    // sets Data of each Item
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

    // total number of cells
    @Override
    public int getItemCount() {
        return mySubjects.size();
    }


    // connects programm to Itemlayout if seen on screen
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

            ////////////////////////////////////////////////////////////on normal click on item spreads
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setViewData(mySubjectPopUp);
                }
            });

            ///////////////////////////////////////////////////////////// on long click on item edit
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

    // convenience method for getting data at click position
    Subject getItem(int id) {
        return mySubjects.get(id);
    }

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


    public void equalizeSnippet(int position){
        snippet.addSubject(position, mySubjects.get(position));
    }

    public ArrayList<Subject> getMySubjects() {
        return mySubjects;
    }
}