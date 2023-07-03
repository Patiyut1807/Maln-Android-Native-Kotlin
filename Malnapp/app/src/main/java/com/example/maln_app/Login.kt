package com.example.maln_app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var signUpBtn = findViewById<Button>(R.id.signupBtn)
        var loginBtn = findViewById<Button>(R.id.loginBtn)
        var usernameEdt = findViewById<EditText>(R.id.unameEdt)
        var passEdt = findViewById<EditText>(R.id.passEdt)

        loginBtn.setOnClickListener {
            val uname = usernameEdt.text.toString()
            val pass = passEdt.text.toString()
            authCheck(uname,pass)
//
        }
        signUpBtn.setOnClickListener {
            val signupScreen = Intent(this,Signup_screen::class.java)
            startActivity(signupScreen)
        }
    }
    data class Root(
        val status: String,
        val value: Value,
    )

    data class Value(
        val id: String,
        val name: String,
        val lastname: String,
        val uname: String,
    )

    fun authCheck(uname:String, pass:String){

        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://172.20.10.2:4000/signin?uname=$uname&pass=$pass")
            .build()
        try {
             client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    call.cancel()
                }

                @SuppressLint("SuspiciousIndentation")
                override fun onResponse(call: Call, response: Response) {
                    var gson = Gson()
                    val res = response.body!!.string()
                    var obj = gson.fromJson(res,Root::class.java)

                 if (obj.status == "success"){
                val mainActivity = Intent(this@Login,MainActivity::class.java)
                     mainActivity.putExtra("uid",obj.value.id)
                startActivity(mainActivity)
            }

                }

            })
        }catch (err:Error){
        }

    }
}