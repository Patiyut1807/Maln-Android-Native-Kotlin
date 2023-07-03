package com.example.maln_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import okhttp3.*
import okhttp3.internal.wait
import java.io.IOException

class Add_data : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)
        val bundle = intent.extras
        val uid = bundle?.getString("uid").toString()

        val confirmBtn = findViewById<Button>(R.id.addDataBtn)
        val bname = findViewById<EditText>(R.id.bname)
        val genre = findViewById<EditText>(R.id.genre)
        val author = findViewById<EditText>(R.id.author)
        val publisher = findViewById<EditText>(R.id.publisher)

        confirmBtn.setOnClickListener {
            val bnameStr = bname.text.toString()
            val genreStr = genre.text.toString()
            val authorStr = author.text.toString()
            val publisherStr = publisher.text.toString()


            try {
                val client = OkHttpClient()
                val request: Request = Request.Builder()
                    .url("https://172.20.10.2:4000/insertbook?uid=$uid&bname=$bnameStr&author=$authorStr&publisher=$publisherStr&genre=$genreStr")
                    .build()
                client.newCall(request).enqueue(object : Callback {

                    override fun onFailure(call: Call, e: IOException) {
                        call.cancel()
                    }

                    override fun onResponse(call: Call, response: Response) {

                        val myResponse = response.body!!.string()

                        Log.d("test", myResponse.toString())
                        this@Add_data.finish()

                    }

                })
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}
