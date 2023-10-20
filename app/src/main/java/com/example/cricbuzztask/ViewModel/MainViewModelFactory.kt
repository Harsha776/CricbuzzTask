package com.example.cricbuzztask.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cricbuzztask.Repository.GetRepository
class MainViewModelFactory(private val getRepository: GetRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(getRepository) as T
}