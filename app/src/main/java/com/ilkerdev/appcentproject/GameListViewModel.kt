package com.ilkerdev.appcentproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilkerdev.appcentproject.model.GameDetailItem
import com.ilkerdev.appcentproject.model.GameItem
import com.ilkerdev.appcentproject.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class GameListViewModel(private  val repository: Repository):ViewModel() {

    //Oyun listelerini gostermek icin olusturulan view model yapisi.

    val myResponse: MutableLiveData<Response<GameItem>> = MutableLiveData()
    val myDetailRespone: MutableLiveData<Response<GameDetailItem>> = MutableLiveData()
    fun getGames(){
        viewModelScope.launch {
            val response: Response<GameItem> = repository.getGames()
            myResponse.value = response
        }
    }


    fun getGameDetails(gameId: String){
        viewModelScope.launch {
            val response: Response<GameDetailItem> = repository.getGameDetails(gameId)
            myDetailRespone.value = response
        }

    }

}