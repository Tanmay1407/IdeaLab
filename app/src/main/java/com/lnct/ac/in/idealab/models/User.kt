package com.lnct.ac.`in`.idealab.Models

class User(val name : String, val email : String, val branch : String, val college : String, val phone : String, val _id : String
) {
    override fun toString(): String {
        return  """{
              "name":${name},
              "email":${email},
              "branch":${branch},
              "college":${college},
              "phone":${phone},
              "_id":${_id}}""".trimIndent()
    }



}