package com.lnct.ac.in.idealab.interfaces;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface CallBack {
    public abstract void responseCallback(JSONObject response);
    public abstract void errorCallback(VolleyError error_message);
    public abstract void responseStatus(NetworkResponse response_code);
}
