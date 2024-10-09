package com.example.vendicore.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vendicore.Models.Product
import com.example.vendicore.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun fetchProducts() {
        RetrofitClient.instance.getProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    _products.value = response.body()
                    Log.d("ProductViewModel", "Products fetched successfully: ${response.body()}")
                } else {
                    Log.e("ProductViewModel", "Failed to fetch products: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("ProductViewModel", "API call failed: ${t.message}")
            }
        })
    }
}