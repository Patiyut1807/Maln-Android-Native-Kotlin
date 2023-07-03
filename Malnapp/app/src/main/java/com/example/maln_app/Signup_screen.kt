package com.example.maln_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.maln_app.databinding.ActivitySignupScreenBinding
import okhttp3.*
import java.io.IOException


class Signup_screen : AppCompatActivity() {
    private lateinit var binding: ActivitySignupScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.confirmBtn.setOnClickListener {
            val uname = binding.unameEdt.text.toString()
            val name = binding.nameEdt.text.toString()
            val lname = binding.lnameEdt.text.toString()
            val pass = binding.passEdt.text.toString()

            binding.confirmBtn.setOnClickListener {
//                if (uname.isEmpty() || pass.isEmpty()){
//                    Log.e("test","invalid username or password")
//                }else{



                try {
                    createUser(uname,pass,name,lname)


                } catch (e: IOException) {
                    e.printStackTrace()
                }



            }
        }


        }
    fun createUser(uname:String, pass:String, name:String, lname:String) {
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://172.20.10.2:4000/signup?uname=$uname&pass=$pass&name=$name&lastname=$lname")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
            }

            override fun onResponse(call: Call, response: Response) {

                val myResponse = response.body!!.string()
//                  if (myResponse == "sign up successfully"){
//
//                  }
                Log.d("test",myResponse.toString())
                if (myResponse.toString() == "sign up successfully"){
                    this@Signup_screen.finish()
                }
            }

        })

    }
    }

