package com.lnct.ac.`in`.idealab.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.NetworkResponse
import com.android.volley.VolleyError
import com.google.android.material.textfield.TextInputEditText
import com.lnct.ac.`in`.idealab.Constants
import com.lnct.ac.`in`.idealab.Models.User
import com.lnct.ac.`in`.idealab.R
import com.lnct.ac.`in`.idealab.Utils
import com.lnct.ac.`in`.idealab.VolleyRequest
import com.lnct.ac.`in`.idealab.activity.HomeActivity
import com.lnct.ac.`in`.idealab.interfaces.CallBack
import com.lnct.ac.`in`.idealab.`interfaces`.login_finish
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class RegisterActivity : AppCompatActivity() , login_finish {
    lateinit var userName : TextInputEditText
    lateinit var userEmail : TextInputEditText
    lateinit var userPhone : TextInputEditText
    lateinit var collegeDropDown : AutoCompleteTextView
    lateinit var branchDropDown : AutoCompleteTextView
    lateinit var loading : LinearLayout
    var RES_CODE = -1

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
        loading = findViewById(R.id.loading)

        val user_email = intent.getStringExtra("EMAIL")
        userEmail.setText(user_email)
        userEmail.isEnabled = false



        val collegeList = listOf("LNCT", "LNCTE", "LNCTS", "LNCTU","Other")
        val collegeDropDownAdapter = ArrayAdapter(this, R.layout.drop_down_list_item, collegeList)
        collegeDropDown?.setAdapter(collegeDropDownAdapter)

        val branchList = listOf("CSE","CY","IOT","AI/ML","Block Chain","DS","IT","EC","EX","EE","ME","CE","CM","Other")
        val branchDropDownAdapter = ArrayAdapter(this,R.layout.drop_down_list_item, branchList)
        branchDropDown?.setAdapter(branchDropDownAdapter)


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

                if(enableBtn){

                    val request = VolleyRequest(this@RegisterActivity, object : CallBack {
                        override fun responseCallback(response: JSONObject) {
                            val success = response.get("sucess") as JSONObject
                            val userCreated = success.get("user") as JSONObject

                            Utils.saveUser(this@RegisterActivity,Utils.convertToUserObj(userCreated))

                            val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        override fun errorCallback(error_message: VolleyError?) {
                            loading.visibility = View.GONE
                            Toast.makeText(this@RegisterActivity, "Try again after sometime!", Toast.LENGTH_LONG).show()
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
                    bodyData.put("name",name)
                    bodyData.put("phone",phone)
                    bodyData.put("college",college)
                    bodyData.put("branch",branch)



                    Log.d(TAG, Constants.URL_CREATE_USER)
                    request.postWithBody(Constants.URL_CREATE_USER,bodyData)

                    if(Utils.isNetworkAvailable(this@RegisterActivity)){
                        loading.visibility = View.VISIBLE
                    }



                }else {
                    Toast.makeText(this@RegisterActivity, "Invaild Credentials",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun finishLogin() {
        finish()
        finishAffinity()
    }
}