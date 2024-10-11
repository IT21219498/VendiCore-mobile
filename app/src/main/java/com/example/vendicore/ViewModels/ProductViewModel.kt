package com.example.vendicore.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vendicore.Models.Category
import com.example.vendicore.Models.Product
import com.example.vendicore.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    private val _categories = MutableLiveData<List<Category>>()
    private val _product = MutableLiveData<Product>()
    val categories: LiveData<List<Category>> get() = _categories
    val products: LiveData<List<Product>> get() = _products
    val product: LiveData<Product> get() = _product

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

    fun fetchProductById(id: String) {
        RetrofitClient.instance.getProductById(id).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    _product.value = response.body()
                    Log.d("ProductViewModel", "Product fetched successfully: ${response.body()}")
                } else {
                    Log.e("ProductViewModel", "Failed to fetch product: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.e("ProductViewModel", "API call failed: ${t.message}")
            }
        })
    }

    fun fetchProductsByCategory(categoryId: String) {
        RetrofitClient.instance.getProductsByCategory(categoryId).enqueue(object : Callback<List<Product>> {
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

    fun searchProductsByName(name: String) {
        RetrofitClient.instance.searchProductsByName(name).enqueue(object : Callback<List<Product>> {
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