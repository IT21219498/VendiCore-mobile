package com.example.vendicore.Models

data class VendorReview(
    val id: String?,  // Use ObjectId directly for MongoDB ID
    val vendorId: String,
    val rating: Float,
    var comment: String,
    val createdAt: String? // Optional date field
)