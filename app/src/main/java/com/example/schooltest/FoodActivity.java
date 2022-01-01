package com.example.schooltest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.schooltest.Database.MyContractClass;
import com.example.schooltest.Database.MySQLiteHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodActivity extends Fragment {

    private String key;
    private PopUpError popUpError;
    private RequestQueue requestQueue;
    private Integer[] myViews;
    private String[] keys;
    private MySQLiteHelper db;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        keys = new String[]{"name","main_dish","supplement","garnish","cost_students"};
        db = new MySQLiteHelper(getContext());
        key = db.getUserData(MyContractClass.UserdataTable.COL_KEY);
        popUpError = new PopUpError(getActivity(), "sorry, hat nicht geklappt");
        requestQueue = Volley.newRequestQueue(getContext());
        myViews = new Integer[]{R.id.food1, R.id.food2, R.id.food3, R.id.food4};
        MyRequestHandler.volleyRequest(0, popUpError, "https://schoolschooli.herokuapp.com/getDayMeal/", null, requestQueue, key, new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try{
                    Log.d("result", result.toString());
                    JSONArray jsonArray = result.getJSONArray("Meals");
                    JSONArray meal = jsonArray.getJSONObject(1).getJSONArray("meal");
                    for(int a = 0; a < 4; a++){
                        JSONArray meals = meal.getJSONArray(a);
                        String[] supplements = new String[5];
                        supplements[0] = meals.getJSONObject(0).getString(keys[0]);
                        supplements[1] = meals.getJSONObject(1).getString(keys[1]);
                        supplements[4] = meals.getJSONObject(3).getString(keys[4]);
                        JSONArray info = meals.getJSONObject(7).getJSONArray("remaining_info");
                        supplements[2] = info.getString(0);
                        supplements[3] = info.getString(1);
                        FoodFragment foodFragment = new FoodFragment();
                        foodFragment.changeFoodFragment(supplements);

                        getFragmentManager().beginTransaction().replace(myViews[a], foodFragment).commit();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

            @Override
            public void onDefeat() {

            }
        });
    }
}
