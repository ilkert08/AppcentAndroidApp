package com.ilkerdev.appcentproject.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    const val BASE_URL = "https://rawg-video-games-database.p.rapidapi.com/"


    //Retrofitin kuruldugu kisim.
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: GameApi by lazy {
        retrofit.create(GameApi::class.java)
    }
}