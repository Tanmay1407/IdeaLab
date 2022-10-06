package com.lnct.ac.`in`.idealab.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.lnct.ac.`in`.idealab.R
import com.lnct.ac.`in`.idealab.`interfaces`.login_finish
import java.util.*
import kotlin.collections.ArrayList

class RegisterActivity : AppCompatActivity() , login_finish {
    lateinit var userName : TextInputEditText
    lateinit var userEmail : TextInputEditText
    lateinit var userPhone : TextInputEditText
    lateinit var collegeDropDown : AutoCompleteTextView
    lateinit var branchDropDown : AutoCompleteTextView


    val TAG = "RegisterActivity"
    var enableBtn = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        collegeDropDown = findViewById(R.id.college_dropdown_menu)
        branchDropDown = findViewById(R.id.branch_dropdown_menu)
        userName = findViewById(R.id.evName)
        userEmail = findViewById(R.id.evEmail)
        userPhone = findViewById(R.id.evPhNo)

        val collegeList = listOf("LNCT", "LNCTE", "LNCTS", "LNCTU","Other")
        val collegeDropDownAdapter = ArrayAdapter(this, R.layout.drop_down_list_item, collegeList)
        collegeDropDown?.setAdapter(collegeDropDownAdapter)

        val branchList = listOf("CSE","CY","IOT","AI/ML","Block Chain","DS","IT","EC","EX","EE","ME","CE","CM","Other")
        val branchDropDownAdapter = ArrayAdapter(this,R.layout.drop_down_list_item, branchList)
        branchDropDown?.setAdapter(branchDropDownAdapter)


        findViewById<ImageView>(R.id.back_arrow).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                finish()
                startActivity(Intent(this@RegisterActivity, StartActivity::class.java))
            }
        })

        findViewById<Button>(R.id.btnRegister).setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                enableBtn = false;
                val name = userName.text.toString().trim()
                val email = userEmail.text.toString().trim()
                val branch = branchDropDown.text.toString().trim()
                val college = collegeDropDown.text.toString().trim()
                val phone = userPhone.text.toString().trim()

                if(name.length != 0 && email.length != 0 && branch.length != 0 && college.length != 0 && phone.length == 10)
                    enableBtn = true


                Log.d(TAG,name + " "+email + " "+branch + " "+college + " "+phone + " ")
                Log.d(TAG,generateOTP())

                if(enableBtn){
                val otpVerificationDialog = OTPVerificationDialog(this@RegisterActivity,email,generateOTP(),this@RegisterActivity)
                otpVerificationDialog.setCancelable(false)
                otpVerificationDialog.show()
                }else {
                    Toast.makeText(this@RegisterActivity, "Invaild Credentials",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun generateOTP(): String {
        val randomPin = (Math.random() * 9000).toInt() + 1000
        return randomPin.toString()
    }


    override fun finishLogin() {
        finish()
        finishAffinity()
    }
}