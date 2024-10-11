package com.example.vendicore.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vendicore.Models.CartItem
import com.example.vendicore.network.ApiService
import com.example.vendicore.network.CartRequest
import com.example.vendicore.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<MutableList<CartItem>>(mutableListOf())
    val cartItems: LiveData<MutableList<CartItem>> get() = _cartItems

    fun addItem(cartItem: CartItem) {
//send order to api
        sendCartItem()
        Log.d("CartViewModel", "Adding item to cart: $cartItem")
        _cartItems.value?.add(cartItem)
        _cartItems.value = _cartItems.value // Trigger LiveData update
        Log.d("CartViewModel", "Cart items: ${_cartItems.value}")
    }

    fun removeItem(cartItem: CartItem) {
        _cartItems.value?.remove(cartItem)
        _cartItems.value = _cartItems.value // Trigger LiveData update
    }

    private fun sendCartItem() {
        val orderId = ""
        val customerId = "670181d28c096894a6fbf5b5"  // Replace with actual customer ID
        val items = mutableListOf<Any>()
        for (cartItem in _cartItems.value!!) {
            items.add(mapOf("productId" to cartItem.productId, "quantity" to cartItem.quantity))
        }



        val cartRequest = CartRequest(orderId , customerId, items)
        val apiService = RetrofitClient.instance
        apiService.sendCartItems(cartRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("CartViewModel", "Cart items sent successfully")
                } else {
                    Log.e("CartViewModel", "Failed to send cart items: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("CartViewModel", "API call failed: ${t.message}")
            }
        })
    }
}