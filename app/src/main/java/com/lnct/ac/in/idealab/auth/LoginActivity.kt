package com.lnct.ac.`in`.idealab.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.lnct.ac.`in`.idealab.R
import com.lnct.ac.`in`.idealab.`interface`.login_finish
import com.lnct.ac.`in`.idealab.activity.HomeActivity

class LoginActivity : AppCompatActivity() , login_finish {
    override fun finishLogin() {
        finish()
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<ImageView>(R.id.back_btn).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                finish()
                startActivity(Intent(this@LoginActivity,StartActivity::class.java))
            }
        })

        findViewById<Button>(R.id.btnLogin).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {

            val otpVerificationDialog = OTPVerificationDialog(this@LoginActivity,"user@gmail.com",this@LoginActivity)
                otpVerificationDialog.setCancelable(false)
                otpVerificationDialog.show()

            }
        })

        findViewById<Button>(R.id.btnRegister).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))

            }
        })

    }


}