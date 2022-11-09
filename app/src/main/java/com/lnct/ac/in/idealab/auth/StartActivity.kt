package com.lnct.ac.`in`.idealab.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.android.volley.NetworkResponse
import com.android.volley.VolleyError
import com.google.android.material.textfield.TextInputEditText
import com.lnct.ac.`in`.idealab.Constants
import com.lnct.ac.`in`.idealab.R




// THIS IS DUMMY ACTIVITY WHICH NOT IN USE //


class StartActivity : AppCompatActivity() {
    lateinit var loginButton: CardView
    lateinit var userEmail : TextInputEditText

    val TAG = "StartActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_start)

    }
}