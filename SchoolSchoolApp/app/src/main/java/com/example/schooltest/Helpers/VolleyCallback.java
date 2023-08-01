package com.example.schooltest.Helpers;

import org.json.JSONObject;

public interface VolleyCallback {

    /////////////////////////////interface um die Antworten des Servers abzufangen und individuell zu bearbeiten
    void onSuccess(JSONObject result);
    void onDefeat();
}
