package com.lnct.ac.`in`.idealab.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import com.lnct.ac.`in`.idealab.R
import com.lnct.ac.`in`.idealab.interfaces.login_finish

class RegisterActivity : AppCompatActivity() , login_finish {
    override fun finishLogin() {
        finish()
        finishAffinity()
    }

    lateinit var collegeDropDown : AutoCompleteTextView
    lateinit var branchDropDown : AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        collegeDropDown = findViewById(R.id.college_dropdown_menu)
        branchDropDown = findViewById(R.id.branch_dropdown_menu)

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
//                finish()
//                startActivity(Intent(this@RegisterActivity, StartActivity::class.java))
                val otpVerificationDialog = OTPVerificationDialog(this@RegisterActivity,"user@gmail.com",this@RegisterActivity)
                otpVerificationDialog.setCancelable(false)
                otpVerificationDialog.show()
            }
        })
    }
}