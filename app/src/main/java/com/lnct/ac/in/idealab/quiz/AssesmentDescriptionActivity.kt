package com.lnct.ac.`in`.idealab.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.lnct.ac.`in`.idealab.R

class AssesmentDescriptionActivity : AppCompatActivity() {

    lateinit var btnStartQuiz : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assesment_description)

        btnStartQuiz = findViewById(R.id.btnStartQuiz)

        btnStartQuiz.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(this@AssesmentDescriptionActivity,AssesmentActivity::class.java))
            }
        })

    }
}