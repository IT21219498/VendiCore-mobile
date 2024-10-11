package com.example.vendicore.network

import com.example.vendicore.Models.Category
import com.example.vendicore.Models.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/Products/products")
    fun getProducts(): Call<List<Product>>

    @GET("api/Products/categories")
    fun getCategories(): Call<List<Category>>

    @GET("api/Products/{id}")
    fun getProductById(@Path("id") id: String): Call<Product>

    @GET("api/Products/category/{categoryId}")
    fun getProductsByCategory(@Path("categoryId") categoryId: String): Call<List<Product>>

    @GET("api/Products/search/{name}")
    fun searchProductsByName(@Path("name") name: String): Call<List<Product>>
}