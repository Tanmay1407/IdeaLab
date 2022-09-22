package com.lnct.ac.`in`.idealab.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.lnct.ac.`in`.idealab.R

class StartActivity : AppCompatActivity() {
    lateinit var loginButton: Button
    lateinit var registerButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        loginButton = findViewById(R.id.btnLogin)

        loginButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                finish()
                startActivity(Intent(this@StartActivity,LoginActivity::class.java))
            }
        })
    }
}