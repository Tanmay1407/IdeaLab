package com.lnct.ac.`in`.idealab.auth

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.lnct.ac.`in`.idealab.R
import com.lnct.ac.`in`.idealab.`interface`.login_finish
import com.lnct.ac.`in`.idealab.activity.HomeActivity

class OTPVerificationDialog(context : Context,var userEmail : String, val loginFinish : login_finish ) : Dialog(context) {

    lateinit var otpET1 : EditText
    lateinit var otpET2 : EditText
    lateinit var otpET3 : EditText
    lateinit var otpET4 : EditText
    lateinit var resendBtn : TextView
    lateinit var verifyBtn : Button
    lateinit var userEmailTV : TextView

    var resendTime : Long = 60 // Resend OTP time
    var resendEnabled = false
    var selectedETPosition = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        window?.setBackgroundDrawable(ColorDrawable(context.resources.getColor(android.R.color.transparent)))
        setContentView(R.layout.otp_verification_layout)

        otpET1 = findViewById(R.id.otpET1)
        otpET2 = findViewById(R.id.otpET2)
        otpET3 = findViewById(R.id.otpET3)
        otpET4 = findViewById(R.id.otpET4)

        resendBtn = findViewById(R.id.TVresend)
        verifyBtn = findViewById(R.id.verifyButton)
        userEmailTV = findViewById(R.id.tvEmail)

        otpET1.addTextChangedListener(textWatcher)
        otpET2.addTextChangedListener(textWatcher)
        otpET3.addTextChangedListener(textWatcher)
        otpET4.addTextChangedListener(textWatcher)

        //By default open keyboard on first EditText
        showKeyboard(otpET1)

        // start countDown timer
        startCountDownTimer()

        // set email to textView
        userEmailTV.setText(userEmail)



        resendBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {

                if(resendEnabled){

                    // Resend code here . . .

                    startCountDownTimer()
                }
            }
        })

        verifyBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                loginFinish.finishLogin()
            var getOTP = otpET1.text.toString()+otpET2.text.toString()+otpET3.text.toString()+otpET4.text.toString()

                if(getOTP.length == 4){
                    // Verification code here . . .

                    Toast.makeText(context,getOTP,Toast.LENGTH_SHORT).show()
                    context.startActivity(Intent(context, HomeActivity::class.java))

                }

            }
        })





//        findViewById<AppCompatButton>(R.id.verifyButton).setOnClickListener(object : View.OnClickListener{
//            override fun onClick(p0: View?) {
//                context.startActivity(Intent(context, HomeActivity::class.java))
//            }
//        })
    }

    private val  textWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
           if(p0?.length!! == 1){

               if (selectedETPosition == 0){
                   // Select next edit text
                   selectedETPosition = 1
                   showKeyboard(otpET2)
               }else if (selectedETPosition == 1){
                   selectedETPosition = 2
                   showKeyboard(otpET3)

               }else if (selectedETPosition == 2){
                   selectedETPosition = 3
                   showKeyboard(otpET4)

               }else {
                   verifyBtn.setBackgroundColor(R.drawable.round_back_red)
               }
           } else p0?.clear()
        }
    }

    private fun showKeyboard(optET : EditText){
        optET.requestFocus()
        val inputMethodManager : InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputMethodManager.showSoftInput(optET, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun startCountDownTimer(){
         resendEnabled = false
        resendBtn.setTextColor(Color.parseColor("#99000000"))

       object : CountDownTimer(resendTime * 1000,1000){
           override fun onTick(millisUntilFinished: Long) {
               resendBtn.setText("Resend Code ("+(millisUntilFinished / 1000)+")")
           }

           override fun onFinish() {
               resendEnabled = true
               resendBtn.setText("Resend Code")
               resendBtn.setTextColor(context.resources.getColor(android.R.color.holo_blue_dark))
           }

       }.start()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if(keyCode == KeyEvent.KEYCODE_DEL){
            if(selectedETPosition == 3){
                // move to previous edit text
                selectedETPosition = 2
                showKeyboard(otpET3)
            }
            else if (selectedETPosition == 2){
                selectedETPosition = 1
                showKeyboard(otpET2)

            }else if (selectedETPosition == 1){
                selectedETPosition = 0
                showKeyboard(otpET1)
            }

            verifyBtn.setBackgroundResource(R.drawable.round_back_brown)
            return true
        }
        else {
            return super.onKeyUp(keyCode, event)
        }

    }


}