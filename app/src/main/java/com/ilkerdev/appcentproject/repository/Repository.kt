package com.ilkerdev.appcentproject.repository

import com.ilkerdev.appcentproject.api.RetrofitInstance
import com.ilkerdev.appcentproject.model.GameDetailItem
import com.ilkerdev.appcentproject.model.GameItem
import retrofit2.Response

class Repository {
    suspend fun getGames(): Response<GameItem> { //Oyun listesini ceken fonksiyon.
        return  RetrofitInstance.api.getGames()
    }

    suspend fun getGameDetails(gameId: String): Response<GameDetailItem> {  //Oyun detaylarini ceken fonksiyon.
        return  RetrofitInstance.api.getGameDetails(gameId = gameId)
    }
}