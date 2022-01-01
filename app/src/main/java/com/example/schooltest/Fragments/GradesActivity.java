package com.example.schooltest.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.schooltest.Database.MyContractClass;
import com.example.schooltest.Database.MySQLiteHelper;
import com.example.schooltest.Helpers.MyRequestHandler;
import com.example.schooltest.PopUps.PopUpError;
import com.example.schooltest.PopUps.UntisLoginPopUp;
import com.example.schooltest.R;
import com.example.schooltest.Helpers.RecyclerViewAdapter;
import com.example.schooltest.Helpers.Snippet;
import com.example.schooltest.Helpers.Subject;
import com.example.schooltest.Helpers.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

public class GradesActivity extends Fragment{

    private String id;
    private String key;
    private ArrayList<Subject> mySubjects;
    private RecyclerView myGrid;
    private RecyclerViewAdapter adapter;
    private TextView overAllTV, usernameTV;
    private MySQLiteHelper db;
    private UntisLoginPopUp untisLoginPopUp;
    private PopUpError popUpError;
    private RequestQueue requestQueue;
    private Snippet snippet;

    //////////////////////////////////////////////////////Fragment um die Fächer des Nutzers und die Noten anzuzeigen und zu verändern
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_grades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new MySQLiteHelper(getContext());
        snippet = new Snippet();
        usernameTV = (TextView) view.findViewById(R.id.usernameTV);
        usernameTV.setText(db.getUserData(MyContractClass.UserdataTable.COL_USERNAME));
        myGrid = (RecyclerView) view.findViewById(R.id.gridListRV);
        myGrid.setLayoutManager(new GridLayoutManager(getContext(), 2));
        overAllTV = (TextView) view.findViewById(R.id.overAllTV);
        overAllTV.setText(String.valueOf(Subject.overAll));
        requestQueue = Volley.newRequestQueue(getActivity());
        popUpError = new PopUpError(getActivity(), "update failed");
        id = db.getUserData(MyContractClass.UserdataTable.COL_SNIPPETID);
        key = db.getUserData(MyContractClass.UserdataTable.COL_KEY);
        untisLoginPopUp = new UntisLoginPopUp(getActivity(), requestQueue, key);

        Subject subject00 = new Subject("", 0 ,"", 1,1, new ArrayList<Integer>(), new ArrayList<Integer>(),
                new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>(),new ArrayList<Integer>(),
                new ArrayList<Integer>(),new ArrayList<Integer>(),View.INVISIBLE);
        mySubjects = new ArrayList<>();
        mySubjects.add(subject00);
        mySubjects.add(subject00);
        adapter = new RecyclerViewAdapter(getActivity(), getContext(), mySubjects);
        myGrid.setAdapter(adapter);


        /////////////////////////////////////////////////checken ob Internetverbindung
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNI = connectivityManager.getActiveNetworkInfo();
        if(activeNI != null && activeNI.isConnected()) {
            /////////////////////////////////////////////////wenn verbunden dann holen der Daten von der Website und der Datenbank und die Timstamps vergleichen,
            /////je nachdem eins von beiden Laden
            MyRequestHandler.volleyRequest(0, null, "https://schoolschooli.herokuapp.com/snippets/" + id, null, requestQueue, key, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {

                    try {
                        Timestamp timestamp2 = Timestamp.valueOf(db.getSnippet()[1].getString("timestamp"));
                        Timestamp timestamp = Timestamp.valueOf(result.getJSONObject("data").getString("timestamp"));
                        Log.d("time", timestamp.toString());
                        Log.d("time", timestamp2.toString());
                        if (timestamp2.before(timestamp)) {
                            load(result, view);
                        } else {
                            load(db.getSnippet()[0], view);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        load(db.getSnippet()[0], view);
                    }
                }

                @Override
                public void onDefeat() {
                    load(db.getSnippet()[0], view);
                }
            });
        }
        ////////////////////////wenn nicht verbunden dann Datenbank laden
        else{
            load(db.getSnippet()[0], view);
        }

    }

    //////////////////////////////////////////Funktion zum Laden von Daten aus einem JSONObject, wenn keine Daten vorhanden, dann Untis Login Fenster öffnen
    public void load(JSONObject result, View view){
        ArrayList<Subject> subjects = MyRequestHandler.getSubjects(result);

        Log.d("gradesActivity", subjects.toString());
        if(subjects.size() == 0){
            view.findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
            untisLoginPopUp.show();
            untisLoginPopUp.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                    retry(view);

                }
            });
        }
        else {
            int x = 2;
            for (Subject s:subjects) {
                mySubjects.add(s);
                snippet.addSubject(x-2, mySubjects.get(x));
                x++;
            }
            adapter.notifyDataSetChanged();
            ArrayList<Subject> a = new ArrayList<Subject>();
            for(int b = 2; b < mySubjects.size(); b++){a.add(mySubjects.get(b));}
            Subject.recountOverAll(a);
            overAllTV.setText(String.valueOf(Subject.overAll));
            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        }
    }

    //////////////////////////////////speichert beim schließen der App
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        db.change_Snippet(Snippet.mySnippet, 1);
        Log.d("snippet", db.getSnippet()[0].toString());
        MyRequestHandler.saveSnippet(getActivity());
    }

    ////////////////////////////////////////////////////holt die Fächer des Benutzers aus WebUntis
    private void retry(View view){
        MyRequestHandler.volleyRequest(0, popUpError, "https://schoolschooli.herokuapp.com/webuntis/?subjects", null, requestQueue, key, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                Log.d("lol", "ja");
                try{
                    Log.d("state", String.valueOf(result.getInt("done")));
                    if(result.getInt("done") == 0){
                        JSONArray array = result.getJSONArray("Subjects");
                        for (int i =0; i < array.length(); i++) {
                            JSONArray hm = array.getJSONArray(i);
                            Subject subject = new Subject(hm.getString(0), 0, hm.getString(1), 50, 50, new ArrayList<Integer>(),
                                    new ArrayList<Integer>(),
                                    new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(),
                                    new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Integer>(), View.VISIBLE);
                            mySubjects.add(subject);
                            snippet.addSubject(i, subject);
                        }
                        Log.d("subs", mySubjects.toString());
                        adapter.notifyDataSetChanged();
                        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                    }
                    else{
                        Thread.sleep(2000);
                        retry(view);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onDefeat() {

            }
        });
    }

}
