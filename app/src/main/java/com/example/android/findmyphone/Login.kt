package com.example.android.findmyphone

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.android.findmyphonePhoenix.UserData
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*


class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    fun btnRegister(view: View){    //btnRegister is the name of onClick for the button
        val userData = UserData(this)
        val phoneNumber = et_phone_number.text.toString()
        userData.savePhoneNum(phoneNumber)// save to preferences
        finish() //goes to launcher activity defined in manifest
    }
}
