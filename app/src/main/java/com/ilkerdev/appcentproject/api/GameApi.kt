package com.ilkerdev.appcentproject.api

import com.ilkerdev.appcentproject.model.GameDetailItem
import com.ilkerdev.appcentproject.model.GameItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GameApi {
    //Retrofit yardimiyla api isteklerinin yapildigi alan.


    @Headers("x-rapidapi-key: 7cefe39915msh558bf017dbca089p107fecjsnf6e04e3831f3",
        "x-rapidapi-host: rawg-video-games-database.p.rapidapi.com")
    @GET("games")
    suspend fun getGames(): Response<GameItem>

    @Headers("x-rapidapi-key: 7cefe39915msh558bf017dbca089p107fecjsnf6e04e3831f3",
        "x-rapidapi-host: rawg-video-games-database.p.rapidapi.com")
    @GET("games/{gameId}")
    suspend fun getGameDetails(@Path("gameId") gameId:String ): Response<GameDetailItem>

}