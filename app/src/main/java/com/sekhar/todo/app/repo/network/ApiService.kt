package com.sekhar.todo.app.repo.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// base url for api
private const val BASE_URL = "https://type.fit/api/"

// Build the Moshi object that Retrofit will be using
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//  Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("quotes")
    fun getMotivation() : Call<List<Motivation>>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MotivationApi{
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}