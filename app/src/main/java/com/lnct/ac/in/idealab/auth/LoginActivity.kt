package com.lnct.ac.`in`.idealab.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.lnct.ac.`in`.idealab.Constants
import com.lnct.ac.`in`.idealab.R
import com.lnct.ac.`in`.idealab.VolleyRequest
import com.lnct.ac.`in`.idealab.`interfaces`.login_finish
import com.lnct.ac.`in`.idealab.activity.HomeActivity
import com.lnct.ac.`in`.idealab.interfaces.CallBack
import org.json.JSONObject

class LoginActivity : AppCompatActivity() , login_finish{
    lateinit var userEmail : TextInputEditText
    val genOTP = generateOTP()
    val TAG = "LoginActivity"
    lateinit var otpVerificationDialog : OTPVerificationDialog

    override fun finishLogin() {
        otpVerificationDialog.dismiss()
        finish()
        finishAffinity()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userEmail = findViewById(R.id.evEmail)

        findViewById<ImageView>(R.id.back_btn).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                finish()
                startActivity(Intent(this@LoginActivity,StartActivity::class.java))
            }
        })

        findViewById<Button>(R.id.btnLogin).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {

                val email = userEmail.text.toString().trim()

                if(email.length != 0) {

                   val request = VolleyRequest(this@LoginActivity, object : CallBack{
                       override fun responseCallback(response: JSONObject?) {
                           Log.d(TAG,response.toString())
                       }

                       override fun errorCallback(error_message: VolleyError?) {
                           Log.d(TAG,"ERROR : "+error_message.toString())
                       }

                       override fun responseStatus(response_code: NetworkResponse?) {
                           Log.d(TAG,"RESPONCE_CODE : "+response_code?.statusCode.toString())
                       }
                   })
                   val bodyData = JSONObject()
                    bodyData.put("email",email)
                    bodyData.put("otp",genOTP)

                    Log.d(TAG,Constants.URL_SEND_OTP)
                    request.postWithBody(Constants.URL_SEND_OTP,bodyData)


                     otpVerificationDialog = OTPVerificationDialog(
                        this@LoginActivity,
                        email,
                        genOTP,
                        this@LoginActivity
                    )
                    otpVerificationDialog.setCancelable(false)
                    otpVerificationDialog.show()

                }else
                    Toast.makeText(this@LoginActivity, "Invaild Credentials", Toast.LENGTH_SHORT).show()


            }
        })

        findViewById<Button>(R.id.btnRegister).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))

            }
        })

    }

    fun generateOTP(): String {
        val randomPin = (Math.random() * 9000).toInt() + 1000
        return randomPin.toString()
    }


}