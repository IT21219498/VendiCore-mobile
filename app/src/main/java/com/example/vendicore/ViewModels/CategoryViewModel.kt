package com.example.vendicore.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vendicore.Models.Category
import com.example.vendicore.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class CategoryViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    fun fetchCategories() {
        RetrofitClient.instance.getCategories().enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {
                    _categories.value = response.body()
                    Log.d("CategoryViewModel", "Categories fetched successfully: ${response.body()}")

                }else {
                    Log.e("CategoryViewModel", "Failed to fetch categories: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                // Handle failure
                Log.e("CategoryViewModel", "API call failed: ${t.message}")
            }
        })
    }
}