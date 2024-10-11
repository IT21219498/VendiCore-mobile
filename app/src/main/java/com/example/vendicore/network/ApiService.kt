package com.example.vendicore.network

import com.example.vendicore.Models.CartItem
import com.example.vendicore.Models.Category
import com.example.vendicore.Models.Product
import com.example.vendicore.Models.Review
import com.example.vendicore.Models.VendorReview
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

//class for login request body
data class LoginRequest(
    val email: String,
    val password: String
)

//class for login response body
data class LoginResponse(
    val token: String,
    val customerId: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val address: String
)

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val password: String
)

data class RegisterResponse(
    val message: String
)
data class CustomerUpdateDTO(
    val fullName: String?,
    val phoneNumber: String?,
    val address: String?,
    val password: String?
)
data class UpdateApiResponse(
    val message: String
)

interface ApiService {


      @GET("api/Products/products")
    fun getProducts(): Call<List<Product>>

    @POST("api/Customer/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/Customer/register")
    fun registerCustomer(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

    @PUT("api/Customer/modify/{customerId}")
    fun modifyCustomer(
        @Path("customerId") customerId: String,
        @Body customerUpdateDTO: CustomerUpdateDTO
    ): Call<UpdateApiResponse>

    @POST("api/Customer/deactivate/{customerId}")
    fun deactivateCustomer(@Path("customerId") customerId: String): Call<UpdateApiResponse>

    @GET("api/Products/categories")
    fun getCategories(): Call<List<Category>>

    @GET("api/Products/{id}")
    fun getProductById(@Path("id") id: String): Call<Product>

    @GET("api/Products/category/{categoryId}")
    fun getProductsByCategory(@Path("categoryId") categoryId: String): Call<List<Product>>

    @GET("api/Products/search/{name}")
    fun searchProductsByName(@Path("name") name: String): Call<List<Product>>

    // POST method for submitting a rating
    @POST("api/Rating/newRating")
    fun submitRating(@Body rating: Review): Call<Void>

    @GET("api/Rating")  // Adjust the endpoint as necessary
    fun getReviews(): Call<List<VendorReview>>

    @PUT("api/Rating/{id}/comment")  // Assuming this endpoint updates a review by its ID
    fun updateReview(@Path("id") id: String, @Body newComment: String): Call<Void>
}