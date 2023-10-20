package com.example.cricbuzztask

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cricbuzztask.Model.Sneaker
import com.example.cricbuzztask.Model.SneakerDetails
import com.example.cricbuzztask.Repository.GetRepository
import com.example.cricbuzztask.ViewModel.MainViewModel
import com.example.cricbuzztask.ViewModel.MainViewModelFactory
import com.example.cricbuzztask.ViewModel.MainViewModelUseCase
import com.example.cricbuzztask.adapter.SneakerAdapter
import com.example.cricbuzztask.databinding.ActivityMainBinding
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private lateinit var getRepository: GetRepository
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sneakerAdapter:SneakerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        clickListner()
    }

    fun init(){
        getRepository = GetRepository()
       mainViewModelFactory = MainViewModelFactory(getRepository)
        mainViewModel = ViewModelProvider(this,mainViewModelFactory).get(MainViewModel::class.java)
        sneakerAdapter = SneakerAdapter()
        binding.rvPopular.apply {
            setHasFixedSize(true)
            layoutManager=GridLayoutManager(this@MainActivity, 2)
            adapter=sneakerAdapter
        }
        readJson()
        mainViewModel.getSneakerData().observe(this, Observer {
            sneakerAdapter.setData(it as ArrayList<Sneaker>)
        })
        mainViewModel.getSearchSneakerData().observe(this, Observer {
            sneakerAdapter.setData(it as ArrayList<Sneaker>)
        })
    }

    fun clickListner(){
        binding.etSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
               mainViewModel.searchData(p0.toString())
            }

        })

        binding.tvPopular.setOnClickListener {
            val intent=Intent(this,SneakerDetailsPage::class.java)
            startActivity(intent)
        }
    }

    fun readJson(){
        try {
            val jsonData=Gson().fromJson(loadJSONFromAsset(), SneakerDetails::class.java)
            Log.d("TAG", "readJson: "+jsonData.sneakerDetails.size)
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
