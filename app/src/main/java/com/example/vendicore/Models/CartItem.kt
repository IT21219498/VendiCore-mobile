package com.example.vendicore.Models

data class CartItem(
    val productName: String,
    val productPrice: Double,
    val productImageResId: Int,
    val quantity: Int,
    val imageResId: Int,
    val vendorName: String,


)