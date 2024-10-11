package com.example.vendicore.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vendicore.Models.Product
import com.example.vendicore.ViewModels.ProductViewModel
import com.example.vendicore.databinding.ActivityProductDetailsBinding
import com.squareup.picasso.Picasso

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the action bar with back navigation
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Product Details"
        }

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        val productId = intent.getStringExtra("PRODUCT_ID")
        if (productId != null) {
            productViewModel.fetchProductById(productId)
        }

        productViewModel.product.observe(this, { product ->
            displayProductDetails(product)
        })
    }

    private fun displayProductDetails(product: Product) {
        binding.productName.text = product.name
        binding.productPrice.text = "Rs. ${product.price}"
        binding.productDescription.text = product.description
        Picasso.get().load(product.imageUrl).into(binding.productImage)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}