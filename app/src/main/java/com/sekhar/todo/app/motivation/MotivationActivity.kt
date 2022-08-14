package com.sekhar.todo.app.motivation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sekhar.todo.app.databinding.ActivityMotivationBinding
import com.sekhar.todo.app.repo.network.Motivation
import com.sekhar.todo.app.repo.network.MotivationApi
import retrofit2.Call
import retrofit2.Response

class MotivationActivity : AppCompatActivity() {

    private  lateinit var bind : ActivityMotivationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMotivationBinding.inflate(layoutInflater)
        setContentView(bind.root)
    }

    init{
        getMotivationQuotes()
    }

    private fun getMotivationQuotes(){
        MotivationApi.retrofitService.getMotivation().enqueue(object:retrofit2.Callback<List<Motivation>> {

            // HTTP response
            override fun onResponse(
                call: Call<List<Motivation>>,
                response: Response<List<Motivation>>
            ) {

                bind.quote.text = response.body()?.get(0)?.author ?: "LOL"
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected exception
             * occurred creating the request or processing the response.
             */
            override fun onFailure(
                call: Call<List<Motivation>>,
                t: Throwable
            ) {
                bind.quote.text = t.message
            }

        })
    }

}