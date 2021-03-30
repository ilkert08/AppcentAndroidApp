package com.ilkerdev.appcentproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ilkerdev.appcentproject.repository.Repository

class GameListViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GameListViewModel(repository) as T
    }
}