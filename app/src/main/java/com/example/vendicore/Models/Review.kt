package com.example.vendicore.Models

data class Review(
    val vendorName: String,
    val rating: Float,
    var comment: String,
    val date: String
)
