package com.example.maln_app

import CustomAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.*
import okhttp3.internal.wait
import java.io.IOException
import java.util.concurrent.CountDownLatch

data class Book(
    val id: String,
    val owner: String,
    val bname: String,
    val author: String,
    val publisher: String,
    val genre: String,
)


class MainActivity : AppCompatActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bundle = intent.extras
        val uid = bundle?.getString("uid").toString()
        val addBtn = findViewById<Button>(R.id.addBtn)

            addBtn.setOnClickListener {
                val addData = Intent(this,Add_data::class.java)
                addData.putExtra("uid",uid)
                startActivity(addData)
            }



    }

    override fun onResume() {
        super.onResume()
        val bundle = intent.extras
        val uid = bundle?.getString("uid").toString()
        val books = getBooks(uid)
        val recyclerview = findViewById<RecyclerView>(R.id.book_listview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ItemsViewModel>()
        for (book in books) {
            data.add(ItemsViewModel( book.id,book.owner,book.bname,book.author,book.publisher,book.genre))
        }

        val adapter = CustomAdapter(data,this)

        recyclerview.adapter = adapter
    }

    fun getBooks(uid:String): List<Book> {
        var booksArray:List<Book> = emptyArray<Book>().toList()
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://172.20.10.2:4000/getbooks?uid=$uid")
            .build()
        val countDownLatch = CountDownLatch(1)
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
                booksArray = gson.fromJson(res,Array<Book>::class.java).toList()

                Log.d("test",booksArray.toString())
                countDownLatch.countDown();
            }

        })
        countDownLatch.await();
        return booksArray;
    }
    }