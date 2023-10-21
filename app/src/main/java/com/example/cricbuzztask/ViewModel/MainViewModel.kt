package com.example.cricbuzztask.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cricbuzztask.Model.Sneaker
import com.example.cricbuzztask.Repository.GetRepository
import com.example.cricbuzztask.utils.SingleLiveEvent
import kotlinx.coroutines.launch

/**
 * This class is used for calling the API and handling the business logic
 */
class MainViewModel(private val getRepository: GetRepository) : ViewModel() {

    fun getData(): LiveData<List<Any>> = myResponse
    private val myResponse: MutableLiveData<List<Any>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            try {
                val response = getRepository.getData()
                myResponse.value = response

            } catch (e: Exception) {
                Log.d("main", "getData: ${e.message}")
            }
        }
    }

    //Popular data list handled
    private lateinit var sneakerData: ArrayList<Sneaker>
    fun getSneakerData(): LiveData<List<Sneaker>> = sneakerDataVal
    private val sneakerDataVal: MutableLiveData<List<Sneaker>> = MutableLiveData()
    fun setData(data: ArrayList<Sneaker>) {
        sneakerData = data
        sneakerDataVal.postValue(sneakerData)
    }

    //handled the search sneaker data

    fun getSearchSneakerData(): LiveData<ArrayList<Sneaker>> = searchSneakerData
    private val searchSneakerData: MutableLiveData<ArrayList<Sneaker>> = MutableLiveData()
    var searchProduct = ""
    fun searchData(name: String) {
        searchProduct = name
        val searchSneaker: ArrayList<Sneaker> = ArrayList()
        if (name.trim().isNotEmpty()) {
            for (i in sneakerData.indices) {
                if (sneakerData[i].name.contains(name, true)) {
                    searchSneaker.add(sneakerData[i])
                }
            }
        } else {
            searchSneaker.addAll(sneakerData)
        }
        searchSneakerData.postValue(searchSneaker)
    }

    //handle the popular screen data  to pass it in sneaker details screen
    private var productNameID = ""
    private lateinit var sneakerProduct: Sneaker
    fun getSneakerDetailsPageData(): LiveData<Sneaker> = sneakerDetailsPageData
    private val sneakerDetailsPageData: MutableLiveData<Sneaker> = MutableLiveData()

    fun setSneakerDetails(id: String) {
        productNameID = id
        if (productNameID.trim().isNotEmpty()) {
            for (i in sneakerData.indices) {
                if (sneakerData[i].id.equals(productNameID, true)) {
                    sneakerProduct = sneakerData[i]
                    sneakerDetailsPageData.postValue(sneakerProduct)
                    productNameID = ""
                }
            }
        }
    }


    //handle the cart list product
    private var cartList: ArrayList<Sneaker> = ArrayList()
    private val cartListObserver: MutableLiveData<ArrayList<Sneaker>> = MutableLiveData()
    fun getCartList(): LiveData<ArrayList<Sneaker>> = cartListObserver

    private val addCartMessgae: SingleLiveEvent<String> = SingleLiveEvent()
    fun getCartMessgae(): SingleLiveEvent<String> = addCartMessgae


    fun addToCart(id: String) {
        for (i in cartList.indices) {
            if (cartList[i].id.equals(id, true)) {
                addCartMessgae.postValue("This Product Already in Cart")
                return
            }
        }
        if (id.isNotEmpty()) {
            for (i in sneakerData.indices) {
                if (sneakerData[i].id.equals(id, true)) {
                    cartList.add(sneakerData[i])
                    addCartMessgae.postValue("Successfully added this product to cart")
                }
            }
        }
        cartListObserver.postValue(cartList)
    }


    //handled the removing products from cart
    fun removeFromCart(id: String) {
        for (i in cartList.indices) {
            if (cartList[i].id.equals(id, true)) {
                cartList.removeAt(i)
                cartListObserver.postValue(cartList)
                return
            }
        }
    }
}