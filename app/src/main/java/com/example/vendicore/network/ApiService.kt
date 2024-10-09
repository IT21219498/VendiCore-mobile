package com.example.vendicore.network

import com.example.vendicore.Models.Product
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/Products/products")
    fun getProducts(): Call<List<Product>>
}