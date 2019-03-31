package com.example.android.findmyphonePhoenix

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.android.findmyphone.Login


class UserData{
    var context: Context?=null
    var sharedPreferences: SharedPreferences?=null  // initially doesn't have a value
    constructor(context: Context){
        this.context = context
        this.sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE)
    }
    fun savePhoneNum(phoneNumber:String){
        val editor = sharedPreferences!!.edit()
        editor.putString("phoneNumber", phoneNumber)
        editor.apply()
    }
    fun isFirstTimeLoad(){
        val phoneNumber = sharedPreferences!!.getString("phoneNumber","empty")
        if(phoneNumber.equals("empty")){
            val intent = Intent(context, Login::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context!!.startActivity(intent)
        }
    }
    fun loadPhoneNum():String{
        val phoneNumber = sharedPreferences!!.getString("phoneNumber","empty")
        return phoneNumber
    }
}
