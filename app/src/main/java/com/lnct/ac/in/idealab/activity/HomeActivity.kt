package com.lnct.ac.`in`.idealab.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.lnct.ac.`in`.idealab.R
import com.lnct.ac.`in`.idealab.frgments.AboutUs

class HomeActivity : AppCompatActivity() {

    lateinit var frame_layout : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        frame_layout = findViewById(R.id.frame_layout)

        var transaction = getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.frame_layout, AboutUs())
        transaction.commit()

    }
}