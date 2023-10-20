package com.example.cricbuzztask.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricbuzztask.Repository.GetRepository
import kotlinx.coroutines.launch
class MainViewModel(private val getRepository: GetRepository) : ViewModel() {

    fun getData(): LiveData<List<Any>> = myResponse
    private val myResponse: MutableLiveData<List<Any>> = MutableLiveData()
    fun getPost()
    {
        viewModelScope.launch{
            try{
                val response = getRepository.getData()
                myResponse.value = response

            }catch (e:Exception){
                Log.d("main", "getData: ${e.message}")
            }
        }
    }

}