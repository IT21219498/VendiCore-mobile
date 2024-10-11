package com.example.vendicore.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vendicore.Models.Product
import com.example.vendicore.R
import com.example.vendicore.Utils.showAddToCartDialog

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var productImage: ImageView
    private lateinit var productName: TextView
    private lateinit var productRating: TextView
    private lateinit var productPrice: TextView
    private lateinit var buyButton: Button
    private lateinit var addToCartButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        // Initialize views
        productImage = findViewById(R.id.productImage)
        productName = findViewById(R.id.productName)
        productRating = findViewById(R.id.productRating)
        productPrice = findViewById(R.id.productPrice)
        buyButton = findViewById(R.id.buyButton)
        addToCartButton = findViewById(R.id.addToCartButton)

        // Get product details from intent
        val intent = intent
        val imageUrl = intent.getIntExtra("PRODUCT_IMAGE", R.drawable.diamond_ring) // Use a placeholder image if needed
        val name = intent.getStringExtra("PRODUCT_NAME") ?: ""
        val rating = intent.getStringExtra("PRODUCT_RATING") ?: "0.0 ★"
        val price = intent.getStringExtra("PRODUCT_PRICE") ?: "Rs. 0.00"

        // Log the received data
        Log.d("ProductDetailsActivity", "Received product details: name=$name, rating=$rating, price=$price, imageUrl=$imageUrl")

        // Create a Product object
        val product = Product(
            name = name,
            rating = rating.toDoubleOrNull() ?: 0.0,
            price = price.removePrefix("Rs. ").toDoubleOrNull() ?: 0.0,
            vendorName = "Vendor 1",
            imageResId = imageUrl
        )

        // Set product details
        productImage.setImageResource(imageUrl) // Load image
        productName.text = name
        productRating.text = rating
        productPrice.text = price

        // Set up button click listeners
        buyButton.setOnClickListener {
            // Implement buy logic here
            // For example, you might start a new activity to handle the purchase process
        }

        addToCartButton.setOnClickListener {
            showAddToCartDialog(this, this, product)
        }
    }
}