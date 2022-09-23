package com.lnct.ac.`in`.idealab.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.lnct.ac.`in`.idealab.R

class SplashScreen : AppCompatActivity() {
    lateinit var logoImg : ImageView
    lateinit var tv2: TextView
    lateinit var  icon_holder: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        logoImg = findViewById(R.id.launch_image)
        tv2 = findViewById(R.id.tv2)
        icon_holder = findViewById(R.id.icon_holder)

        Handler().postDelayed({
            finish()
            startActivity(Intent(this@SplashScreen, HomeActivity::class.java))

        },2500

        )

        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_scale)
        icon_holder.startAnimation(anim)
        logoImg.startAnimation(anim)
        tv2.startAnimation(anim)

    }
}