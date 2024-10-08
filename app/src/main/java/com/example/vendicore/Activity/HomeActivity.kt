package com.example.vendicore.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vendicore.Adapters.CategoriesAdapter
import com.example.vendicore.Adapters.ProductAdapter
import com.example.vendicore.Models.Category
import com.example.vendicore.Models.Product
import com.example.vendicore.R

class HomeActivity : AppCompatActivity() {

    private lateinit var categoriesRecyclerView: RecyclerView
    private lateinit var productsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize RecyclerViews
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView)
        productsRecyclerView = findViewById(R.id.productsRecyclerView)

        // Set layout managers
        categoriesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        productsRecyclerView.layoutManager =
            LinearLayoutManager(this)

        // Set adapters
        val categoriesAdapter = CategoriesAdapter(getCategories())
        categoriesRecyclerView.adapter = categoriesAdapter

        val productsAdapter = ProductAdapter(getProducts(), this) // Pass context here
        productsRecyclerView.adapter = productsAdapter
    }

    // Mock data for categories
    private fun getCategories(): List<Category> {
        return listOf(
            Category("Sale"),
            Category("Celebration"),
            Category("Arrivals"),
            Category("Bestsellers")
        )
    }

    // Mock data for products
    private fun getProducts(): List<Product> {
        return listOf(
            Product("Diamond Ring", 4.3, 2500.0, R.drawable.diamond_ring),
            Product("Gold Bracelet", 5.0, 3000.0, R.drawable.gold_bracelet)
        )
    }
}
