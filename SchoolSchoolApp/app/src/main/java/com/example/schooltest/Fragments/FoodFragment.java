package com.example.schooltest.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.schooltest.R;

public class FoodFragment extends Fragment {
    ////////////////////////////////////////////ein Teil der 4 Essen, die angezeigt werden
    private String[] myAdditions = new String[5];
    private TextView[] myTV;
    public void changeFoodFragment(String[] additions){
        for(int i = 0; i<5;i++){
            myAdditions[i] = additions[i];
        }
    }
    ////////////////////////////////////////////////////setzt die TextViews
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_fragment, container, false);
        myTV = new TextView[]{view.findViewById(R.id.mealTV), view.findViewById(R.id.main_dish), view.findViewById(R.id.supplement), view.findViewById(R.id.garnish), view.findViewById(R.id.price)};
        for(int x = 0; x<5;x++){
            myTV[x].setText(myAdditions[x]);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
