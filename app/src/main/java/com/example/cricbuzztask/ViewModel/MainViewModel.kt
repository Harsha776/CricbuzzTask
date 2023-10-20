package com.example.cricbuzztask.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricbuzztask.Model.Sneaker
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

    private lateinit var sneakerData:ArrayList<Sneaker>
    fun getSneakerData() : LiveData<List<Sneaker>> = sneakerDataVal
    private val sneakerDataVal: MutableLiveData<List<Sneaker>> = MutableLiveData()
    fun setData(data:ArrayList<Sneaker>){
        sneakerData=data
        sneakerDataVal.postValue(sneakerData)
    }

    fun getSearchSneakerData() : LiveData<ArrayList<Sneaker>> = searchSneakerData
    private val searchSneakerData: MutableLiveData<ArrayList<Sneaker>> = MutableLiveData()
    fun searchData(name:String){
        var searchSneaker:ArrayList<Sneaker> =ArrayList()
        if(name.trim().isNotEmpty()){
            for (i in sneakerData.indices){
                if(sneakerData.get(i).name.contains(name,true)){
                    searchSneaker.add(sneakerData.get(i))
                }
            }
        }else{
            searchSneaker.addAll(sneakerData)
        }
        searchSneakerData.postValue(searchSneaker)
    }


}