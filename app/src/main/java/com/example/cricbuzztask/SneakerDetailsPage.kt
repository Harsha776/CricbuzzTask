package com.example.cricbuzztask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cricbuzztask.Repository.GetRepository
import com.example.cricbuzztask.ViewModel.MainViewModel
import com.example.cricbuzztask.ViewModel.MainViewModelFactory
import com.example.cricbuzztask.databinding.ActivityMainBinding
import com.example.cricbuzztask.databinding.ActivitySneakerDetailsPageBinding

class SneakerDetailsPage : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivitySneakerDetailsPageBinding
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var getRepository: GetRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySneakerDetailsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        getRepository = GetRepository()
        mainViewModelFactory = MainViewModelFactory(getRepository)
        mainViewModel = ViewModelProvider(this,mainViewModelFactory).get(MainViewModel::class.java)
        mainViewModel.getSneakerData().observe(this, Observer {
            Log.d("TAG", "SneakerDetailsPage: "+it.size)
        })

    }
}