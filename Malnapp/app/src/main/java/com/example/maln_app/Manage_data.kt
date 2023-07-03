package com.example.maln_app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Flow.Publisher

class Manage_data : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_data)
        val BName = findViewById<EditText>(R.id.bname)
        val Genre = findViewById<EditText>(R.id.genre)
        val Author = findViewById<EditText>(R.id.author)
        val Publisher = findViewById<EditText>(R.id.publisher)


        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val delBtn = findViewById<Button>(R.id.delBtn)
        val bundle = intent.extras
        val id = bundle?.getString("itemId").toString()
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://172.20.10.2:4000/getbook?id=$id")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
            }

            override fun onResponse(call: Call, response: Response) {

                val res = response.body!!.string()
//                  if (myResponse == "sign up successfully"){
//
//                  }
                var gson = Gson()
               val book = gson.fromJson(res,Book::class.java)

                BName.setText(book.bname)
                Genre.setText(book.genre)
                Author.setText(book.author)
                Publisher.setText(book.publisher)
            }

        })
        saveBtn.setOnClickListener {
            val bName = BName.text.toString()
            val genre = Genre.text.toString()
            val  author = Author.text.toString()
            val  publisher = Publisher.text.toString()
            editBook(id,bName, genre, author, publisher)
        }
        delBtn.setOnClickListener {
            delBook(id)
        }
    }











    fun delBook(id:String){
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://172.20.10.2:4000/delbook?id=$id")
            .build()
        try {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    call.cancel()
                }

                @SuppressLint("SuspiciousIndentation")
                override fun onResponse(call: Call, response: Response) {
                    val res = response.body!!.string()
                    if (res == "success"){
                        this@Manage_data.finish()
                    }
                }

            })
        }catch (err:Error){
        }
    }
    fun editBook(id:String,bName:String,genre:String,author:String,publisher: String){
        try {
            Log.d("test",id)
            val client = OkHttpClient()
            val request: Request = Request.Builder()
                .url("https://172.20.10.2:4000/editbook?id=$id&bname=$bName&author=$author&publisher=$publisher&genre=$genre")
                .build()
            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    call.cancel()
                }

                override fun onResponse(call: Call, response: Response) {

                    val myResponse = response.body!!.string()

                    Log.d("test", myResponse.toString())
                    this@Manage_data.finish()

                }

            })
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}