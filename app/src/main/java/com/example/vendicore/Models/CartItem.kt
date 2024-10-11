package com.example.vendicore.Models

data class CartItem(
    val productName: String,
    val productPrice: Double,
    val quantity: Int,
    val imageUrl: String,
    val vendorName: String,
)