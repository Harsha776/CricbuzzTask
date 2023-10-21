package com.example.cricbuzztask

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cricbuzztask.Model.SneakerDetails
import com.example.cricbuzztask.Repository.GetRepository
import com.example.cricbuzztask.ViewModel.MainViewModel
import com.example.cricbuzztask.ViewModel.MainViewModelFactory
import com.example.cricbuzztask.databinding.ActivityMainBinding
import com.google.gson.Gson
import org.json.JSONException
import java.io.IOException
import java.nio.charset.Charset

/**
 * This is the Main Activity, reading the json file and handling the fragments
 */
class MainActivity : AppCompatActivity() {

    private lateinit var getRepository: GetRepository
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init() {
        getRepository = GetRepository()
        mainViewModelFactory = MainViewModelFactory(getRepository)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
        readJson()
    }

    fun readJson() {
        try {
            val jsonData = Gson().fromJson(loadJSONFromAsset(), SneakerDetails::class.java)
            Log.d("TAG", "readJson: " + jsonData.sneakerDetails.size)
            mainViewModel.setData(jsonData.sneakerDetails)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJSONFromAsset(): String? {
        //function to load the JSON from the Asset and return the object
        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val `is` = assets.open("sneaker.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}
