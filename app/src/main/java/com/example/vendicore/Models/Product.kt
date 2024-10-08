package com.example.vendicore.Models

data class Product(
    val name: String,
    val rating: Double,
    val price: Double,
    var vendorName: String,
    val imageResId: Int
)