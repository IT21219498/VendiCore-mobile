package com.example.vendicore.Models

data class Product(
    val id: String,
    val name: String,
    val code: String,
    val price: Double,
    val cost: Double,
    val rating: Double,
    var vendorName: String,
    val imageResId: Int,
    val reorderLevel: Int,
    val categoryId: String,
    val measurementUnitId: String?,
    val description: String,
    val itemPerCase: Int,
    val supplierId: String?,
    val supplierName: String?,
    val imageUrl: String,
    val categoryName: String,
    val measurementUnitName: String,
    val isActive: Boolean
)