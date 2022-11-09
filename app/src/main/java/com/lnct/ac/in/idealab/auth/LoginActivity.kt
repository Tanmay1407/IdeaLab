package com.lnct.ac.`in`.idealab.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.lnct.ac.`in`.idealab.Constants
import com.lnct.ac.`in`.idealab.R
import com.lnct.ac.`in`.idealab.Utils
import com.lnct.ac.`in`.idealab.VolleyRequest
import com.lnct.ac.`in`.idealab.`interfaces`.login_finish
import com.lnct.ac.`in`.idealab.activity.HomeActivity
import com.lnct.ac.`in`.idealab.interfaces.CallBack
import org.json.JSONObject
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() , login_finish{
    lateinit var userEmail : TextInputEditText
    val genOTP = generateOTP()
    val TAG = "LoginActivity"
    lateinit var otpVerificationDialog : OTPVerificationDialog
    var RES_CODE = -1
    lateinit var user : JSONObject
    var isUser = false
    lateinit var loginButton: CardView
    lateinit var loading : LinearLayout

    override fun finishLogin() {
        otpVerificationDialog.dismiss()
        finish()
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        userEmail = findViewById(R.id.evEmail)
        loading  = findViewById(R.id.loading)
        loginButton = findViewById(R.id.btnLogin)
        
        findViewById<TextView>(R.id.btnSkip).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                Toast.makeText(this@LoginActivity, "Welcome! to AICTE Idea Lab", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                finish()
            }
        })


       loginButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                val email = userEmail.text.toString().trim()
                Log.d(TAG,email)
                if(isValidString(email)) {

                   val request = VolleyRequest(this@LoginActivity, object : CallBack{
                       override fun responseCallback(response: JSONObject) {

                            if(response.has("success")) {

                                if((response.get("success") as JSONObject).has("user")){
                                    isUser = true
                                    user = ((response.get("success") as JSONObject).get("user") ) as JSONObject
                                    Utils.saveUser(this@LoginActivity,Utils.convertToUserObj(user))

                                }


                                otpVerificationDialog = OTPVerificationDialog(
                                    this@LoginActivity,
                                    email,
                                    genOTP,
                                    isUser,
                                    this@LoginActivity
                                )
                                otpVerificationDialog.setCancelable(false)
                                otpVerificationDialog.show()

                            }




                       }

                       override fun errorCallback(error_message: VolleyError?) {
                           loading.visibility = View.GONE
                           loginButton.isEnabled = true

                           Toast.makeText(this@LoginActivity, "Try again after sometime!", Toast.LENGTH_LONG).show()
                       }

                       override fun responseStatus(response_code: NetworkResponse?) {
                           if (response_code != null) {
                               RES_CODE = response_code.statusCode
                           }
                           Log.d(TAG,"RESPONCE_CODE : "+response_code)
                       }
                   })
                   val bodyData = JSONObject()
                    bodyData.put("email",email)
                    bodyData.put("otp",genOTP)

                    Log.d(TAG,Constants.URL_SEND_OTP)
                    request.postWithBody(Constants.URL_SEND_OTP,bodyData)

                    if(Utils.isNetworkAvailable(this@LoginActivity)){
                        loading.visibility = View.VISIBLE
                        loginButton.isEnabled = false

                    }

                }else
                    Toast.makeText(this@LoginActivity, "Invaild Credentials", Toast.LENGTH_SHORT).show()


            }
        })


    }


    fun generateOTP(): String {
        val randomPin = (Math.random() * 9000).toInt() + 1000
        return randomPin.toString()
    }

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    fun isValidString(str: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }



}