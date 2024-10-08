package com.example.vendicore.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vendicore.Models.CartItem

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<MutableList<CartItem>>(mutableListOf())
    val cartItems: LiveData<MutableList<CartItem>> get() = _cartItems

    fun addItem(cartItem: CartItem) {
        _cartItems.value?.add(cartItem)
        _cartItems.value = _cartItems.value // Trigger LiveData update
    }
    fun removeItem(cartItem: CartItem) {
        _cartItems.value?.remove(cartItem)
        _cartItems.value = _cartItems.value // Trigger LiveData update
    }
}