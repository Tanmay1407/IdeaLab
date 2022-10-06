package com.lnct.ac.in.idealab;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lnct.ac.in.idealab.interfaces.CallBack;

import java.util.Map;

public class VolleyRequest {

    Context c;
    StringRequest request;
    CallBack mCallback;

    VolleyRequest(Context c, CallBack mCallback) {
        this.c = c;
        this.mCallback = mCallback;
    }


    public boolean postWithBody(String url, Map<String, String> map_body) {

        if(!Utils.getInstance().isNetworkAvailable(c)) {
            Log.i("===volley request---", "no network available");
            Toast.makeText(c, "No network available", Toast.LENGTH_SHORT).show();
            return false;
        }

        request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mCallback.responseCallback(response);
                Log.i("-----response----", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mCallback.errorCallback(error.getMessage());
                Log.i("-----error----", error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map_body;
            }
        };

        Toast.makeText(c, "volley started", Toast.LENGTH_SHORT).show();
        
        RequestQueue q = Volley.newRequestQueue(c);
        q.add(request);

        return true;

    }

}
