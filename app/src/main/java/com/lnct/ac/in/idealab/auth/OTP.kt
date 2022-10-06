package com.lnct.ac.`in`.idealab.auth

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class OTP(val context : Context) {
    val queue = Volley.newRequestQueue(context)
    val url = "http://192.168.137.225:5000/api/auth/sendOTP"
    val TAG = "OTP"


     fun sendOTP(email : String, otp : String){
         val jsonObject = JSONObject()
         jsonObject.put("email",email)
         jsonObject.put("otp",otp)

         Log.d(TAG,  "Volly Running !!")



         val jsonObjectRequest = JsonObjectRequest(
             Request.Method.POST, url, jsonObject,
             { response ->
                 try {
                     Log.d(TAG,  "Volly SUCCESS!!")
                     Log.d(TAG,  "Volly SUCCESS!!")
                     Log.d(TAG,response.toString())
                 } catch (e: Exception) {
                     //Log.d(TAG,  "Volly ERRROR1!!"+error)
                     e.printStackTrace()
                 }

             }
         ) { error ->
             Log.d(TAG,  "Volly ERRROR1!!"+error)
             error.printStackTrace()
         }
         jsonObjectRequest.setRetryPolicy(
             DefaultRetryPolicy(
                 0,
                 DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                 DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
             )
         )
         queue.add(jsonObjectRequest)
    }
}