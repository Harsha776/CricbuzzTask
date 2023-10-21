package com.example.cricbuzztask.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cricbuzztask.Repository.GetRepository

/**
 *This class  allow data survive in device configure saturation
 */
class MainViewModelFactory(private val getRepository: GetRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(getRepository) as T
}