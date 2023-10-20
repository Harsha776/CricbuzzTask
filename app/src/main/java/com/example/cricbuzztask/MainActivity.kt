package com.example.cricbuzztask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cricbuzztask.Repository.GetRepository
import com.example.cricbuzztask.ViewModel.MainViewModel
import com.example.cricbuzztask.ViewModel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var getRepository: GetRepository
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init(){
        getRepository = GetRepository()
       mainViewModelFactory = MainViewModelFactory(getRepository)
        mainViewModel = ViewModelProvider(this,mainViewModelFactory).get(MainViewModel::class.java)

    }
}
