package com.lnct.ac.in.idealab;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;

import com.android.volley.DefaultRetryPolicy;

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

import org.json.JSONObject;

import java.util.Map;

public class VolleyRequest {

    Context c;
    JsonObjectRequest request;
    CallBack mCallback;
    RequestQueue q;
    public VolleyRequest(Context c, CallBack mCallback) {
        this.c = c;
        this.mCallback = mCallback;
        q = Volley.newRequestQueue(c);
    }


    public void getRequest(String url) {
        if(Utils.isNetworkAvailable(c)) {
            request = new JsonObjectRequest(url,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            mCallback.responseCallback(response);
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mCallback.errorCallback(error);
                        }
                    }

            ) {
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    mCallback.responseStatus(response);
                    return super.parseNetworkResponse(response);
                }
            };
            request.setRetryPolicy(
                    new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    ));


            q.add(request);
        }else{
            Toast.makeText(c,"No Internet Connection",Toast.LENGTH_LONG).show();
        }
    }

    public void postWithBody(String url, JSONObject body) {
        if(Utils.isNetworkAvailable(c)) {
           request = new JsonObjectRequest(
                   Request.Method.POST, url, body,
                   new Response.Listener<JSONObject>() {
                       @Override
                       public void onResponse(JSONObject response) {
                           mCallback.responseCallback(response);

                       }
                   },

                   new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                           mCallback.errorCallback(error);

                       }
                   }

           ) {
               @Override
               protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                   mCallback.responseStatus(response);
                   return super.parseNetworkResponse(response);
               }
           };

           request.setRetryPolicy(
                   new DefaultRetryPolicy(
                           0,
                           DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                           DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                   ));
           q.add(request);
       }else{
           Toast.makeText(c,"No Internet Connection",Toast.LENGTH_LONG).show();
       }

    }

}
